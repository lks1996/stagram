package clone_project.stagram.controller;

import clone_project.stagram.DTO.LikeDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.WhatTime;
import clone_project.stagram.service.LikeService;
import clone_project.stagram.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;

    public LikeController(LikeService likeService, PostService postService) {
        this.likeService = likeService;
        this.postService = postService;
    }

<<<<<<< HEAD
    @PostMapping("/like/register")
    @ResponseBody
    public boolean likeRegister(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam Long post_no) {
        System.out.println("like post_no"+ post_no);
=======
/** 좋아요 등록 **/
    @PostMapping("/like/register")
    @ResponseBody
    public boolean likeRegister(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam Long post_no) {
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)

        PostDTO postDTO = postService.findPostByPostNo(post_no);
        if (postDTO == null) {
            return false;
        }

        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setLike_regDate(WhatTime.whatTimeIsItNow());

        likeService.likeRegister(likeDTO, loginMember, postDTO);

        return true;
    }

<<<<<<< HEAD
    @PostMapping("/like/cancel")
    @ResponseBody
    public boolean likeCancel(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam Long post_no) {
        System.out.println("unlike post_no"+ post_no);
=======
/** 좋아요 취소 **/
    @PostMapping("/like/cancel")
    @ResponseBody
    public boolean likeCancel(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam Long post_no) {
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)

        likeService.cancelLike(post_no, loginMember.getUser_no());

        //like 가 정상적으로 취소되었는지 확인.
        LikeDTO likeDTO = likeService.findLikeByUserNoAndPostNo(loginMember.getUser_no(), post_no);
        if (likeDTO == null) {
            return true;
        }

        return false;
    }
}


