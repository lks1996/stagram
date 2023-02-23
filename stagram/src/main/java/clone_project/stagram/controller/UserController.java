package clone_project.stagram.controller;

import clone_project.stagram.DTO.LoginDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.SavePath;
import clone_project.stagram.SessionConst;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/list")
    public String welcome(Model model) throws Exception{
        List<UserDTO> users = userService.findAllMembers();

        model.addAttribute("users", users);

        return "home";
    }

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

    @GetMapping("/logout")
        public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();

        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                          String id, Model model) throws Exception{

        System.out.println("넘어온 id = " + id);
        System.out.println("세션id = " + loginMember.getId());
        System.out.println("loginMember.getId().equals(id)"+loginMember.getId().equals(id));

        /** 본인 프로필로 들어가려는것이라면. **/
        if (loginMember.getId().equals(id)) {
            System.out.println("loginMember.getId()" + loginMember.getId());

            List<PostDTO> userPosts = postService.getOwnPost(loginMember.getId());

            model.addAttribute("profileChangeBtn_disabled", false);
            model.addAttribute("hiddenProfileEditBtn", false);
            model.addAttribute("hiddenFollowBtn", true);
            model.addAttribute("user", loginMember);
            model.addAttribute("postCount", userPosts.stream().count());
            model.addAttribute("userPosts", userPosts);
            model.addAttribute("loginUser", loginMember);
            return "profile";
        }

        /** 다른 유저의 프로필로 들어가려는것이라면. **/
        UserDTO nowUser = userService.isDuplicateId(id);
        if (!(nowUser == null)) {
            List<PostDTO> userPosts = postService.getOwnPost(id);

            model.addAttribute("profileChangeBtn_disabled", true);
            model.addAttribute("hiddenProfileEditBtn", true);
            model.addAttribute("hiddenFollowBtn", false);
            model.addAttribute("user", nowUser);
            model.addAttribute("postCount", userPosts.stream().count());
            model.addAttribute("userPosts", userPosts);
            model.addAttribute("loginUser", loginMember);
            return "profile";
        }

        return "/logout";
    }


    @GetMapping(value = "/user/display", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userProfileDisplay(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                                     String id) throws IOException {
        String profileImg;
        UserProfileImgDTO userProfileImgDTO;
        UserDTO nowUser = userService.isDuplicateId(id);

        if (loginMember.getId() == id) {
            userProfileImgDTO = userService.hasProfileImg(loginMember.getUser_no());
        } else {
            userProfileImgDTO = userService.hasProfileImg(nowUser.getUser_no());
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

            UserProfileImgDTO checkProfileImg = userService.hasProfileImg(loginMember.getUser_no());

            //사용자가 프로필 사진이 있는지 조회 후 없다면, 업로드한 사진을 그냥 save를 통한 insert.
            if (checkProfileImg == null) {
                userService.saveProfileImg(userProfileImgDTO);
            } else { //그렇지 않다면 즉, 이미 DB에 저장된 프로필 사진이 있다면 save를 통한 update.

                userProfileImgDTO.setUserImgNo(checkProfileImg.getUserImgNo());
                userProfileImgDTO.setRegDate(whatTimeIsItNow());
                userService.saveProfileImg(userProfileImgDTO);
            }

            return "redirect:/profile?id=" + loginMember.getId();
        }


        return "redirect:/";
    }

    @GetMapping("/delete/profileImg")
    public String deleteProfileImg(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember) {
        UserProfileImgDTO checkProfileImg = userService.hasProfileImg(loginMember.getUser_no());

        //프로필 사진이 없는 사용자가 사진 삭제를 누를 경우,
        if (checkProfileImg == null) {
            return "redirect:/profile?id=" + loginMember.getId();
        }

        userService.deleteProfileImg(checkProfileImg);

        return "redirect:/profile?id=" + loginMember.getId();

    }


    @GetMapping("/user/edit")
    public String profileUpdate(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, Model model) {
        model.addAttribute("loginUser", loginMember);
        return "profileEdit";
    }


    public String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
