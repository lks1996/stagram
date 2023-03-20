package clone_project.stagram.controller;

import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.FollowService;
import clone_project.stagram.service.PostService;
import clone_project.stagram.service.UserService;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


import java.io.IOException;
import java.util.Collections;
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

        model.addAttribute("loginUser", loginMember);

        //로그인한 회원이 팔로우하는(팔로잉) 회원들의 회원번호 가져오기.
        List<FollowDTO> followingList = followService.followingList(loginMember.getUser_no());
        //팔로잉하는 회원들의 게시글과 본인의 게시글을 뿌려주자.
        List<PostDTO> postDTO = postService.selectPost(followingList, loginMember);
        System.out.println("홈컨트롤러에서 뿌려줄 게시물들은 ? " + postDTO);


        System.out.println("postDTO.get(0).getLikeDTOS().get(0) ==== " + postDTO.get(0).getLikeDTOS().isEmpty());


        //팔로우하는 회원이나 본인의 게시글에서 더 이상 보여줄게 없다면, 아무 사용자의 게시글을 랜덤으로 보여준다.
        if (postDTO.isEmpty()) {

            System.out.println("이 회원은 팔로우 하는 사람도 없고, 본인 게시물도 없음.");

            List<PostDTO> allPostDTO = postService.findAllPost();

            //리스크 인덱스를 랜덤으로 셔플.
            Collections.shuffle(allPostDTO);

            //랜덤으로 섞은 리스트를 페이징.(이때 게시글의 순서가 랜덤이므로, 첫 인덱스와 마지막 인덱스는 고정 값으로 함.)
            List<PostDTO> paginatedAllPostList = postService.pagination(allPostDTO, 0);

            model.addAttribute("posts", paginatedAllPostList);

            return "timeline2";
        }

        List<PostDTO> paginatedPostList = postService.pagination(postDTO, pageCount);

        model.addAttribute("posts", paginatedPostList);
        return "timeline2";
    }
}

