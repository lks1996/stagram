package clone_project.stagram.controller;

import clone_project.stagram.DTO.LoginDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcome(Model model) throws Exception{
        List<UserDTO> users = userService.findMembers();

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
        UserDTO emailCheck = userService.findByEmail(email);
        //이메일이 이미 없다면
        if (emailCheck == null) {
            System.out.println(email);
            return true;
        }
        System.out.println(email);
        return false;
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam String id) {
        UserDTO idCheck = userService.findById(id);
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
    @PostMapping("/user/signin")
    public String signin(LoginDTO loginDTO, HttpSession session) throws Exception {
        System.out.println("로그인 시도 : " + loginDTO.getIdOrEmail());

        return "signin";
    }

    public String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
