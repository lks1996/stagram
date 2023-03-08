package clone_project.stagram.controller;

import clone_project.stagram.DTO.CommentsDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.WhatTime;
import clone_project.stagram.service.CommentsService;
import clone_project.stagram.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentsService commentsService;
    private final PostService postService;

    public CommentController(CommentsService commentService, PostService postService) {
        this.commentsService = commentService;
        this.postService = postService;
    }

/** 댓글 등록 **/
    @PostMapping("/comments/register")
    @ResponseBody
    public boolean commentsRegister(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                   @RequestBody CommentsDTO commentsDTO) {
        PostDTO postDTO = postService.findPostByPostNo(commentsDTO.getPost_no());
        if (postDTO == null) {
            return false;
        }

        commentsDTO.setUser_no(loginMember.getUser_no());
        commentsDTO.setUser_id(loginMember.getId());
        commentsDTO.setComments_regDate(WhatTime.whatTimeIsItNow());

        commentsService.commentsRegister(commentsDTO, loginMember, postDTO);

        return true;
    }

/** 댓글 삭제 **/
    @PostMapping("/comments/delete")
    @ResponseBody
    public boolean commentsDelete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) UserDTO loginMember,
                                    @RequestParam Long comments_no) {

        System.out.println("comments_no :::::::: " + comments_no);

        commentsService.deleteComments(comments_no);

        //comments_no 댓글이 삭제되었는지 확인
        CommentsDTO commentsDTO = commentsService.findCommentsByCommentsNo(comments_no);
        if (commentsDTO == null) {
            return true;
        }

        return false;
    }
}
