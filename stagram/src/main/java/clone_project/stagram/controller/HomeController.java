package clone_project.stagram.controller;

import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.FollowService;
import clone_project.stagram.service.PostService;
import clone_project.stagram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;

    public HomeController(UserService userService, PostService postService, FollowService followService) {
        this.userService = userService;
        this.postService = postService;
        this.followService = followService;
    }

    @GetMapping("/")
    public String home(@SessionAttribute(name =SessionConst.LOGIN_MEMBER, required = false) UserDTO loginMember,
                       Model model) throws IOException {

        int pageCount = 0;

        if (loginMember == null) {
            return "signin";
        }

        System.out.println("세션 아이디 : " + loginMember.getId());

        // 세션에 대응되는 멤버 있는지 확인
        UserDTO validUser = userService.isDuplicateId(loginMember.getId());

        if (validUser == null) {
            return "signin";
        }

        //로그인한 회원이 팔로우하는(팔로잉) 회원들의 회원번호 가져오기.
        List<FollowDTO> followingList = followService.followingList(loginMember.getUser_no());
        //팔로잉하는 회원들의 게시글과 본인의 게시글을 뿌려주자.
        List<PostDTO> postDTO = postService.selectPost(followingList, loginMember);
        List<PostDTO> paginatedPostList = postService.pagination(postDTO, pageCount);

        model.addAttribute("posts", paginatedPostList);
        model.addAttribute("loginUser", loginMember);
        return "timeline2";
    }
}




