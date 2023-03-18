package clone_project.stagram.controller;

import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.FollowService;
import clone_project.stagram.service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

/** 팔로우 **/
    @PostMapping("/follow/request")
    @ResponseBody
    public boolean followRequest(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                @RequestParam Long user_no) {
        System.out.println("팔로우 하려는 회원의 회원 번호 : " + user_no);

        UserDTO followFrom = userService.findUserByUserNo(loginMember.getUser_no());
        UserDTO followTo = userService.findUserByUserNo(user_no);

        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollow_from_user_no(loginMember.getUser_no());
        followDTO.setFollow_from_user_no(user_no);

        followService.follow(followDTO, followFrom, followTo);

        return true;
    }

/** 팔로우 취소(본인이 본인의 팔로잉 취소, 프로필 화면에서) **/
    @PostMapping("/follow/unfollow")
    @ResponseBody
    public boolean unfollowRequest(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                  @RequestParam Long user_no) {

        UserDTO unfollowFrom = userService.findUserByUserNo(loginMember.getUser_no());
        UserDTO unfollowTo = userService.findUserByUserNo(user_no);

        FollowDTO followDTO = followService.isFollow(unfollowFrom.getUser_no(), unfollowTo.getUser_no());

        followService.unfollow(followDTO, unfollowFrom, unfollowTo);

        return true;
    }

/** 팔로우 취소(본인이 팔로워가 본인을 팔로우하는 것을 취소, 팔로워 리스트에서) **/
    @PostMapping("/follow/deleteFollower")
    @ResponseBody
    public boolean deleteFollower(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                   @RequestParam Long user_no) {
        System.out.println("언팔로우 하려는 회원의 회원 번호 : " + user_no);
        System.out.println("언팔로우 하려는 회원의 회원 : " + loginMember.getUser_no());

        UserDTO unfollowFrom = userService.findUserByUserNo(user_no);
        UserDTO unfollowTo = userService.findUserByUserNo(loginMember.getUser_no());

        FollowDTO followDTO = followService.isFollow(unfollowFrom.getUser_no(), unfollowTo.getUser_no());

        System.out.println("unfollowFrom -- "+ unfollowFrom);
        System.out.println("unfollowTo -- "+ unfollowTo);
        System.out.println("followDTO -- "+ followDTO);

        followService.unfollow(followDTO, unfollowFrom, unfollowTo);

        return true;
    }

/** 팔로워 리스트 반환 **/
    @GetMapping("/follow/followerList")
    public String followerList(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                               Model model, Long user_no) {

        System.out.println("누구의 팔로우 리스트? == " + user_no);

        List<FollowDTO> followDTO = followService.followerList(user_no);
        System.out.println(followDTO.size());


        List<UserDTO> followers = new ArrayList<>();


        for (int i = 0; i < followDTO.size(); i++) {
            System.out.println("followDTO.get(i).getFollow_from_user_no()" + followDTO.get(i).getFollow_from_user_no());

            UserDTO follower = userService.findUserByUserNo(followDTO.get(i).getFollow_from_user_no());
            followers.add(follower);
        }

//팔로잉 리스트도 작성하기, 타임라인에서 사용자 팔로잉에 따라 동적으로 타임라인 구성하기.
        model.addAttribute("followerLists", followers);

        System.out.println("ㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂ:: "+user_no);

        UserDTO nowUser = userService.findUserByUserNo(user_no);

        // 본인 프로필로 들어가려는것이라면.
        if (Objects.equals(loginMember.getUser_no(), nowUser.getUser_no())) {
            System.out.println("본인 프로필들어와서 팔로워 리스트 열었음.");
            model.addAttribute("hiddenFollowerDeleteBtn", false);//팔로워 리스트 팔로워 삭제 버튼

            return "profile :: #followerListBody";
        }

        model.addAttribute("hiddenFollowerDeleteBtn", true);//팔로워 리스트 팔로워 삭제 버튼

        return "profile :: #followerListBody";
    }


/** 팔로잉 리스트 반환 **/
    @GetMapping("/follow/followingList")
    public String followingList(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                               Model model, Long user_no) {

        System.out.println("누구의 팔로잉 리스트? == " + user_no);

        List<FollowDTO> followingDTO = followService.followingList(user_no);
        System.out.println(followingDTO.size());


        List<UserDTO> followings = new ArrayList<>();


        for (int i = 0; i < followingDTO.size(); i++) {
            System.out.println("followDTO.get(i).getFollow_from_user_no()" + followingDTO.get(i).getFollow_to_user_no());

            UserDTO follower = userService.findUserByUserNo(followingDTO.get(i).getFollow_to_user_no());
            followings.add(follower);
        }

        model.addAttribute("followingLists", followings);

        System.out.println("ㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐ:: "+user_no);
        UserDTO nowUser = userService.findUserByUserNo(user_no);

        // 본인 프로필로 들어가려는것이라면.
        if (Objects.equals(loginMember.getUser_no(), nowUser.getUser_no())) {
            System.out.println("본인 프로필들어와서 팔로워 리스트 열었음.");
            model.addAttribute("hiddenFollowingDeleteBtn", false);//팔로워 리스트 팔로워 삭제 버튼

            return "profile :: #followingListBody";
        }

        model.addAttribute("hiddenFollowingDeleteBtn", true);//팔로워 리스트 팔로워 삭제 버튼

        return "profile :: #followingListBody";
    }

}
