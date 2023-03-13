package clone_project.stagram.controller;

import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.FollowService;
import clone_project.stagram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @PostMapping("/follow/request")
    @ResponseBody
    public boolean followRequest(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                @RequestParam Long user_no) {
        System.out.println("팔로우 하려는 회원의 회원 번호 : " + user_no);

        UserDTO followFrom = userService.findUserByUserNo(loginMember.getUser_no());
        UserDTO followTo = userService.findUserByUserNo(user_no);

        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollow_from(loginMember.getUser_no());
        followDTO.setFollow_to(user_no);

        followService.follow(followDTO, followFrom, followTo);

        return true;
    }

    @PostMapping("/follow/unfollow")
    @ResponseBody
    public boolean unfollowRequest(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                  @RequestParam Long user_no) {
        System.out.println("언팔로우 하려는 회원의 회원 번호 : " + user_no);

        UserDTO unfollowFrom = userService.findUserByUserNo(loginMember.getUser_no());
        UserDTO unfollowTo = userService.findUserByUserNo(user_no);

        FollowDTO followDTO = followService.isFollow(unfollowFrom.getUser_no(), unfollowTo.getUser_no());

        followService.unfollow(followDTO, unfollowFrom, unfollowTo);

        return true;
    }
}
