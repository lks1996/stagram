package clone_project.stagram.controller;

import clone_project.stagram.DTO.LoginDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
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
        if (bindingResult.hasErrors()) {
            return "passwordFail";
        }
        System.out.println("로그인 시도 : " + loginDTO.getIdOrEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result;

        UserDTO loginUser = userService.login(loginDTO.getIdOrEmail());

        System.out.println(encoder.matches(loginDTO.getPw(), loginUser.getPassword()));
        //입력한 email 이나 id 가 존재하지 않는다면,
        if (loginUser != null) {
            if (encoder.matches(loginDTO.getPw(), loginUser.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

                result = "loginSuccess";


//                session.setAttribute("logined_user", loginUser);
//                System.out.println("logined_user 세션 확인 : " + loginUser);
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

    @GetMapping("/timeline")
    public String timeline() throws Exception{
        return "timeline";
    }

    public String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
