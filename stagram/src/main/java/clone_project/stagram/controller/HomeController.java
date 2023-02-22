package clone_project.stagram.controller;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.PostService;
import clone_project.stagram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class HomeController {

    private final UserService userService;
    private final PostService postService;

    public HomeController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(@SessionAttribute(name =SessionConst.LOGIN_MEMBER, required = false) UserDTO loginMember,
                       Model model) throws IOException {


        if (loginMember == null) {
            return "signin";
        }

        System.out.println("세션 아이디 : " + loginMember.getId());

        // 세션에 대응되는 멤버 있는지 확인
        UserDTO validUser = userService.isDuplicateId(loginMember.getId());

        if (validUser == null) {
            return "signin";
        }

        List<PostDTO> posts = postService.selectPost();
        //정상 로그인
//        UserDTO loginUser = (UserDTO) session.getAttribute("user");
        //model.addAttribute("postName", "97b89484-c51e-4f4c-9350-dcd425ac5af9.jpg");
        model.addAttribute("posts", posts);
        model.addAttribute("loginUser", loginMember);
        return "timeline2";
    }
}



