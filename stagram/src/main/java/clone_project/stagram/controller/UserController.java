package clone_project.stagram.controller;

import clone_project.stagram.DTO.LoginDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.UserService;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.jni.FileInfo;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/profile")
    public String profile(@SessionAttribute(name =SessionConst.LOGIN_MEMBER, required = true) UserDTO loginMember,
                          Model model) throws Exception{

        System.out.println("loginMember.getId()" + loginMember.getId());

        model.addAttribute("loginUser", loginMember);
        return "profile";
    }


    @GetMapping(value = "/display", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember) throws IOException {
        String profileImg;

        UserProfileImgDTO userProfileImgDTO = userService.hasProfileImg(loginMember.getUser_no());

        //등록된 프로필 사진이 없다면, default 이미지 경로 설정.
        if (userProfileImgDTO == null) {
            profileImg = "c:\\Users\\user\\Desktop\\study_spring\\stagram\\stagram\\src\\main\\resources\\static\\images\\profile_frame.jpg";

        } else {
            String savedPath = "c:\\Users\\user\\Desktop\\study_spring\\stagram\\stagram\\src\\main\\resources\\userProfileImg";
            profileImg = savedPath + "\\" + userProfileImgDTO.getProfileImgName();
        }

        InputStream imageStream = new FileInputStream(profileImg);

        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }



/** view 단에 있는 file name 과 @RequestParam의 file name이 일치해야 작동. **/
    @PostMapping("/upload/profile")
    public String upload_profile_pic(@SessionAttribute(name =SessionConst.LOGIN_MEMBER, required = true) UserDTO loginMember, @RequestParam MultipartFile profileImg,
                                     Model model) throws Exception{


        String savePath = "C:\\Users\\user\\Desktop\\study_spring\\stagram\\stagram\\src\\main\\resources\\userProfileImg";

        if( !profileImg.isEmpty() ) {   //파일이 비어있지 않다면.
            String uuidForProfilePicName = UUID.randomUUID().toString()+".jpg";
            File converFile = new File(savePath, uuidForProfilePicName);
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

            //사용자가 프로필 사진이 있는지 조회 후 업다면, 업로드한 사진을 그냥 sava.
            if (checkProfileImg == null) {
                userService.saveProfileImg(userProfileImgDTO);
            } else { //그렇지 않다면 즉, 이미 DB에 저장된 프로필 사진이 있다면 update.

            }


//            model.addAttribute("loginUser", loginMember);
            return "redirect:/profile";
        }


        return "redirect:/";
    }




    public String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
