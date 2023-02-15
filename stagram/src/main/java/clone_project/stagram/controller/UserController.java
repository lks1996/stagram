package clone_project.stagram.controller;

import clone_project.stagram.DTO.LoginDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.UserService;
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

    @GetMapping("/profile")//프로필페이지 만들기!!!!!!!!!
    public String profile(@SessionAttribute(name =SessionConst.LOGIN_MEMBER, required = true) UserDTO loginMember,
                          Model model, String id) throws Exception{
        model.addAttribute("loginUser", loginMember);
        return "profile";
    }

/** view 단에 있는 file name 과 @RequestParam의 file name이 일치해야 작동. **/
    @PostMapping("/upload/profile")
    public String upload_profile_pic(@SessionAttribute(name =SessionConst.LOGIN_MEMBER, required = true) UserDTO loginMember, @RequestParam MultipartFile profileImg) throws Exception{


        String savePath = "C:\\Users\\user\\Desktop";

        if( !profileImg.isEmpty() ) {   //---파일이 없으면 true를 리턴. false일 경우에만 처리함.
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

            userService.saveProfileImg(userProfileImgDTO);

            return "redirect:/";
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
