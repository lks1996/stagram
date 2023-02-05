package clone_project.stagram.controller;

import clone_project.stagram.DTO.LoginDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcome(Model model) {
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
        System.out.println(userDTO.getPw());
        userService.register(userDTO);
        return "redirect:/";
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
}
