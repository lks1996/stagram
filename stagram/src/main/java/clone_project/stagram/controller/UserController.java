package clone_project.stagram.controller;

import clone_project.stagram.DTO.*;
import clone_project.stagram.SavePath;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.CommentsService;
import clone_project.stagram.service.FollowService;
import clone_project.stagram.service.PostService;
import clone_project.stagram.service.UserService;
import org.apache.commons.io.IOUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static clone_project.stagram.WhatTime.whatTimeIsItNow;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final CommentsService commentsService;
    private final FollowService followService;


    public UserController(UserService userService, PostService postService, CommentsService commentsService, FollowService followService) {
        this.userService = userService;
        this.postService = postService;
        this.commentsService = commentsService;
        this.followService = followService;
    }

    @GetMapping("/list")
    public String welcome(Model model) throws Exception{
        List<UserDTO> users = userService.findAllMembers();

        model.addAttribute("users", users);

        return "home";
    }

/** 회원 가입 **/
    @GetMapping("/user/signup")
    public String createUser() throws Exception{
        return "signup";
    }

    @PostMapping("/user/signup")
    public String register(UserDTO userDTO) throws Exception{
        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getPassword());
        userDTO.setRegDate(whatTimeIsItNow());
        userService.register(userDTO);
        return "redirect:/";
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public boolean emailCheck(@RequestParam String email) {
        if (email.contains("@") && email.contains(".")) {
            UserDTO emailCheck = userService.isDuplicateEmail(email);
            //이메일이 이미 없다면
            if (emailCheck == null) {
                System.out.println(email);
                return true;
            }
        }
        System.out.println(email);
        return false;
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam String id) {
        UserDTO idCheck = userService.isDuplicateId(id);
        //id(사용자 이름)가 없다면
        if (idCheck == null) {
            System.out.println(id);
            return true;
        }
        System.out.println(id);
        return false;
    }




/** 로그인 **/
    @GetMapping("/user/signin")
    public String home() throws Exception{
        return "signin";
    }
    @PostMapping(value = "/user/signin")
    @ResponseBody
    public String signin(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult, HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result;

        if (bindingResult.hasErrors()) {
            return "passwordFail";
        }

        if (session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            session.removeAttribute(SessionConst.LOGIN_MEMBER);
        }

        System.out.println("로그인 시도 : " + loginDTO.getIdOrEmail());

        UserDTO loginUser = userService.login(loginDTO.getIdOrEmail());

        System.out.println(encoder.matches(loginDTO.getPw(), loginUser.getPassword()));

        //입력한 email 이나 id 가 존재하지 않는다면,
        if (loginUser != null) {
            if (encoder.matches(loginDTO.getPw(), loginUser.getPassword())) {
                session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
                session.setMaxInactiveInterval(3000);
                System.out.println("request.getSession()" + session.getAttribute(SessionConst.LOGIN_MEMBER));

                result = "loginSuccess";
            } else {
                bindingResult.reject("passwordFail", "비밀번호가 맞지 않습니다.");
                result = "passwordFail";

            }
        } else {
            bindingResult.reject("idOrEmailFail", "없는 아이디입니다.");
            result = "idOrEmailFail";

        }

        return result;
    }



/** 로그아웃 **/
    @GetMapping("/logout")
        public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();

        session.invalidate();
        return "redirect:/";
    }



/** 개인 프로필 페이지 **/
    @GetMapping("/profile")
    public String profile(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                          String id, Model model) throws Exception{

        System.out.println("넘어온 id = " + id);
        System.out.println("세션id = " + loginMember.getId());
        System.out.println("loginMember.getId().equals(id)"+loginMember.getId().equals(id));

        UserDTO nowUser = userService.isDuplicateId(id);

        if (nowUser == null) {
            return "/logout";
        }

        long followingCount = followService.followingList(nowUser.getUser_no()).stream().count();
        long followerCount = followService.followerList(nowUser.getUser_no()).stream().count();

        model.addAttribute("followingCount", followingCount);
        model.addAttribute("followerCount", followerCount);

        List<UserDTO> follows = new ArrayList<>();

        model.addAttribute("followerLists", follows);
        model.addAttribute("followingLists", follows);


        // 본인 프로필로 들어가려는것이라면.
        if (Objects.equals(loginMember.getUser_no(), nowUser.getUser_no())) {
            System.out.println("@@@@@@@@@loginMember.getId()" + loginMember.getUser_no());

            List<PostDTO> userPosts = postService.getOwnPost(loginMember.getUser_no());

            model.addAttribute("profileChangeBtn_disabled", false);//프로필사진 업로드 버튼
            model.addAttribute("hiddenProfileEditBtn", false);//프로필 수정 버튼

            model.addAttribute("user", loginMember);//프로필 페이지의 주인 유저
            model.addAttribute("postCount", userPosts.stream().count());//프로필 페이지 주인의 게시글 수
            model.addAttribute("userPosts", userPosts);//프로필 페이지 주인이 작성한 게시글들
            model.addAttribute("loginUser", loginMember);//현재 로그인한 유저(세선에 등록된 유저)

            model.addAttribute("hiddenFollowBtn", true);//팔로우버튼
            model.addAttribute("hiddenFollowingBtn", true);//언팔로우 버튼

            return "profile";
        }

        // 다른 유저의 프로필로 들어가려는것이라면.
        List<PostDTO> userPosts = postService.getOwnPost(nowUser.getUser_no());

        model.addAttribute("profileChangeBtn_disabled", true);//프로필사진 업로드 버튼
        model.addAttribute("hiddenProfileEditBtn", true);//프로필 수정 버튼

        model.addAttribute("user", nowUser);//프로필 페이지의 주인 유저
        model.addAttribute("postCount", userPosts.stream().count());//프로필 페이지 주인의 게시글 수
        model.addAttribute("userPosts", userPosts);//프로필 페이지 주인이 작성한 게시글들
        model.addAttribute("loginUser", loginMember);//현재 로그인한 유저(세선에 등록된 유저)


        //로그인한 사용자와 다른 유저 간의 팔로우 관계가 있는지 확인.

        //팔로우 관계가 있다면, 언팔로우 버튼 보이게.
        model.addAttribute("hiddenFollowBtn", true);//팔로우 버튼
        model.addAttribute("hiddenFollowingBtn", false);//언팔로우 버튼

        //팔로우 관계가 없다면, 팔로우 버튼 보이게.
        FollowDTO isFollowDTO = followService.isFollow(loginMember.getUser_no(), nowUser.getUser_no());
        if (isFollowDTO == null) {
            model.addAttribute("hiddenFollowBtn", false);//팔로우 버튼
            model.addAttribute("hiddenFollowingBtn", true);//언팔로우 버튼
        }

        return "profile";
    }





/** 회원 정보 수정 **/
    @GetMapping("/user/edit")
    public String profileUpdate(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, Model model) {
        model.addAttribute("loginUser", loginMember);
        return "profileEdit";
    }

    @PostMapping("/user/update")
    @ResponseBody
    public String updateProfile(HttpServletRequest request, @SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                @RequestBody UserDTO updatedUserDTO) {

        HttpSession session = request.getSession();

        System.out.println(updatedUserDTO.getEmail());
        System.out.println(updatedUserDTO.getBio());

        updatedUserDTO.setUser_no(loginMember.getUser_no());
        updatedUserDTO.setPassword(loginMember.getPassword());
        updatedUserDTO.setRegDate(loginMember.getRegDate());

        //사용자가 id와 email을 변경하지 않았을 경우.
        if ((loginMember.getId().equals(updatedUserDTO.getId())) && (loginMember.getEmail().equals(updatedUserDTO.getEmail()))) {


            //사용자가 id만 변경한 경우,
        } else if (!(loginMember.getId().equals(updatedUserDTO.getId())) && (loginMember.getEmail().equals(updatedUserDTO.getEmail()))) {
            UserDTO isDuplicatedId = userService.isDuplicateId(updatedUserDTO.getId());

            if (isDuplicatedId == null) {

            } else {
                return "idFail";
            }

            //사용자가 email만 변경한 경우,
        } else if ((loginMember.getId().equals(updatedUserDTO.getId())) && !(loginMember.getEmail().equals(updatedUserDTO.getEmail()))) {
            UserDTO isDuplicatedEmail = userService.isDuplicateEmail(updatedUserDTO.getEmail());

            if (isDuplicatedEmail == null) {

            } else {
                return "emailFail";
            }

            //사용자가 id와 email 모두 변경한 경우.
        } else {
            UserDTO isDuplicatedEmail = userService.isDuplicateEmail(updatedUserDTO.getEmail());
            UserDTO isDuplicatedId = userService.isDuplicateId(updatedUserDTO.getId());

            //변경한 id와 email이 모두 사용 가능할 경우,
            if ((isDuplicatedId == null) && (isDuplicatedEmail == null)) {


                //변경한 id와 email 중 id가 중복일 경우,
            } else if (!(isDuplicatedId == null) && (isDuplicatedEmail == null)) {
                return "idFail";

                //변경한 id와 email 중 email이 중복일 경우,
            } else if ((isDuplicatedId == null) && !(isDuplicatedEmail == null)) {
                return "emailFail";

                ////변경한 id와 email 모두 중복일 경우,
            } else {
                return "fail";
            }
        }

        //user 테이블과 post 테이블에 업데이트 정보 반영.
        userService.updateProfile(updatedUserDTO);
        postService.updatePostUserId(updatedUserDTO.getUser_no(), updatedUserDTO.getId());
        commentsService.updateCommentsUserId(updatedUserDTO.getUser_no(), updatedUserDTO.getId());

        //업데이트된 session으로 재부여.
        session.setAttribute(SessionConst.LOGIN_MEMBER, updatedUserDTO);
        session.setMaxInactiveInterval(3000);

        return "updateSuccessful";
    }




/** 회원 탈퇴 **/
    @GetMapping("/user/secession")
    public String userSecession(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, Model model) {

        model.addAttribute("loginUser", loginMember);
        return "userSecession";
    }

    @PostMapping("/user/secession")
    @ResponseBody
    public String deleteUser(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                             @RequestParam String password, HttpServletRequest request) {

        System.out.println("INPUT_PASSWORD : "+ password);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches(password, loginMember.getPassword()));

        if (encoder.matches(password, loginMember.getPassword())) {
            postService.deletePost(loginMember.getUser_no());
            userService.deleteProfileImg(loginMember.getUser_no());
            userService.deleteUser(loginMember.getUser_no());

            HttpSession session = request.getSession();

            session.invalidate();

            return "/";
        }

        System.out.println("비밀번호 틀림!!!!");
        return "userSecession";
    }






/** 회원 프로필 사진 등록 **/
    /** view 단에 있는 file name 과 @RequestParam의 file name이 일치해야 작동. **/
    @PostMapping("/upload/profile")
    public String upload_profile_pic(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam MultipartFile profileImg) throws Exception{



        if( !profileImg.isEmpty() ) {   //파일이 비어있지 않다면.
            String uuidForProfilePicName = UUID.randomUUID().toString()+".jpg";
            File converFile = new File(SavePath.USER_PROFILE_IMG_SAVE_PATH, uuidForProfilePicName);
            profileImg.transferTo(converFile);  //--- 저장할 경로를 설정 해당 경로는 각자 원하는 위치로 설정하면 됩니다. 다만, 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생

            UserProfileImgDTO userProfileImgDTO = new UserProfileImgDTO();

            userProfileImgDTO.setProfileImgOriginName(profileImg.getOriginalFilename());
            userProfileImgDTO.setProfileImgName(uuidForProfilePicName);
            userProfileImgDTO.setProfileImgSize(profileImg.getSize());
            userProfileImgDTO.setRegDate(whatTimeIsItNow());
            userProfileImgDTO.setUserNo(loginMember.getUser_no());

            System.out.println(userProfileImgDTO.getProfileImgOriginName());
            System.out.println(userProfileImgDTO.getProfileImgName());
            System.out.println(userProfileImgDTO.getProfileImgSize());
            System.out.println(userProfileImgDTO.getRegDate());
            System.out.println(userProfileImgDTO.getUserNo());

            UserProfileImgDTO checkProfileImg = userService.hasProfileImg(loginMember);

            //사용자가 프로필 사진이 있는지 조회 후 없다면, 업로드한 사진을 그냥 save를 통한 insert.
            if (checkProfileImg == null) {
                userService.saveProfileImg(userProfileImgDTO, loginMember);
            } else { //그렇지 않다면 즉, 이미 DB에 저장된 프로필 사진이 있다면 save를 통한 update.

                userProfileImgDTO.setUserImgNo(checkProfileImg.getUserImgNo());
                userProfileImgDTO.setRegDate(whatTimeIsItNow());
                userService.saveProfileImg(userProfileImgDTO, loginMember);
            }

            return "redirect:/profile?id=" + loginMember.getId();
        }


        return "redirect:/";
    }

/** 회원 프로필 사진 삭제 **/
    @GetMapping("/delete/profileImg")
    public String deleteProfileImg(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember) {
        UserProfileImgDTO checkProfileImg = userService.hasProfileImg(loginMember);

        //프로필 사진이 없는 사용자가 사진 삭제를 누를 경우,
        if (checkProfileImg == null) {
            return "redirect:/profile?id=" + loginMember.getId();
        }

        userService.deleteProfileImg(loginMember.getUser_no());

        return "redirect:/profile?id=" + loginMember.getId();

    }



/** 로컬에 있는 프로필 사진 가져오기 **/
    @GetMapping(value = "/user/display", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userProfileDisplay(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                                     String id) throws IOException {
        String profileImg;
        UserProfileImgDTO userProfileImgDTO;
        UserDTO nowUser = userService.isDuplicateId(id);

        if (loginMember.getId() == id) {
            userProfileImgDTO = userService.hasProfileImg(loginMember);
        } else {
            userProfileImgDTO = userService.hasProfileImg(nowUser);
        }


        //등록된 프로필 사진이 없다면, default 이미지 경로 설정.
        if (userProfileImgDTO == null) {
            profileImg = SavePath.USER_PROFILE_IMG_DEFAULT;

        } else {
            profileImg = SavePath.USER_PROFILE_IMG_SAVE_PATH + "\\" + userProfileImgDTO.getProfileImgName();
        }

        InputStream imageStream = new FileInputStream(profileImg);

        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }

/** 유저 정보 가져오기 **/
    @GetMapping("/user/info")
    @ResponseBody
    public UserDTO userInfo(Long userNo) {
        System.out.println("넘어온 userNo : " + userNo);

        UserDTO userDTO = userService.findUserByUserNo(userNo);

        //필요없는 정보는 빈 문자열로 바꿔서 뷰 단으로 전송.
        userDTO.setPassword("");
        userDTO.setEmail("");

        return userDTO;
    }

}
