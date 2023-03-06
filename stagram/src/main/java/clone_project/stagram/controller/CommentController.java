package clone_project.stagram.controller;

import clone_project.stagram.DTO.CommentsDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SessionConst;
import clone_project.stagram.WhatTime;
import clone_project.stagram.service.CommentsService;
import clone_project.stagram.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

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
        commentsDTO.setComments_regDate(WhatTime.whatTimeIsItNow());

        commentsService.commentsRegister(commentsDTO, loginMember, postDTO);

//        댓글 등록 성공 시 댓글란만 리로드되게 하고 싶음. iframe 사용? 인스타 보니까 게시글 누르면 일반 모달창이 아님. 모달창에 url 부여되어 있음.
//        -게시글을 자세히보기 페이지를 따로 만들고 iframe으로 뿌려주기? 그러면 댓글 저장을 ajax로 수정해야 할 듯.
        return true;
    }
}
