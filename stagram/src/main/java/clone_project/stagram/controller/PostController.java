package clone_project.stagram.controller;

import clone_project.stagram.DTO.CommentsDTO;
import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SavePath;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.CommentsService;
import clone_project.stagram.service.FollowService;
import clone_project.stagram.service.PostService;
import clone_project.stagram.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static clone_project.stagram.WhatTime.whatTimeIsItNow;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentsService commentsService;
    private final FollowService followService;

    public PostController(PostService postService, UserService userService, CommentsService commentsService, FollowService followService) {
        this.postService = postService;
        this.userService = userService;
        this.commentsService = commentsService;
        this.followService = followService;
    }



/** 게시글 업로드 **/
    @PostMapping("/upload/post")
    public String upload_profile_pic(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam MultipartFile postImg,
                                     @RequestParam String postContentsInForm) throws Exception{

        if( !postImg.isEmpty() ) {   //파일이 비어있지 않다면.
            String uuidForProfilePicName = UUID.randomUUID().toString()+".jpg";
            File converFile = new File(SavePath.POST_IMG_SAVE_PATH, uuidForProfilePicName);
            postImg.transferTo(converFile);  //--- 저장할 경로를 설정 해당 경로는 각자 원하는 위치로 설정하면 됩니다. 다만, 해당 경로에 접근할 수 있는 권한이 없으면 에러 발생

            PostDTO postDTO = new PostDTO();

            postDTO.setContents(postContentsInForm);
            postDTO.setPostImgOriginName(postImg.getOriginalFilename());
            postDTO.setPostImgName(uuidForProfilePicName);
            postDTO.setPostImgSize(postImg.getSize());
            postDTO.setPost_regDate(whatTimeIsItNow());
            postDTO.setUser_no(loginMember.getUser_no());
            postDTO.setUser_id(loginMember.getId());


            System.out.println(postDTO.getContents());
            System.out.println(postDTO.getPostImgOriginName());
            System.out.println(postDTO.getPostImgName());
            System.out.println(postDTO.getPostImgSize());
            System.out.println(postDTO.getPost_regDate());
            System.out.println(postDTO.getUser_no());
            System.out.println(postDTO.getUser_id());


            postService.savePost(postDTO, loginMember);


            return "redirect:/";
        }

        return "redirect:/";
    }





/** 게시글 수정. **/
    @PostMapping("/post/update")
    @ResponseBody
    public String updatePost(@RequestParam Long postNo, @RequestParam String postContents) {
        System.out.println(postNo + "|||||||||" + postContents);

        postService.updatePostContents(postNo, postContents);

        return null;
    }




/** 게시글 삭제. **/
    @PostMapping("/post/delete")
    @ResponseBody
    public String deletePost(@RequestParam Long postNo) {
        System.out.println("삭제할 게시글 postNo : " + postNo);

        commentsService.deleteAllComments(postNo);
        postService.deleteOnePost(postNo);

        //postNo 게시글이 삭제되었는지 확인
        PostDTO postDTO = postService.findPostByPostNo(postNo);
        if (postDTO == null) {
            return "[post] delete_success";

        }


        return null;
    }



/** 게시글 사진 가져오기 **/
    @GetMapping(value = "/post/display", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> postDisplay(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, String postName) throws IOException {
        String profileImg;

        PostDTO postDTO = postService.isValidPost(postName);

        System.out.println("프로필 페이지에서 넘어온 포스트 네임 : " + postName);

        //등록된 프로필 사진이 없다면, default 이미지 경로 설정.
        if (postDTO == null) {
            profileImg = SavePath.USER_PROFILE_IMG_DEFAULT;

        } else {
            profileImg = SavePath.POST_IMG_SAVE_PATH + "\\" + postName;
        }

        InputStream imageStream = new FileInputStream(profileImg);

        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }

/** 게시글 정보 넘겨주기. **/
    @GetMapping("/post/info")
    @ResponseBody
    public PostDTO postInfo(Long postNo) {
        System.out.println("넘어온 post_no : " + postNo);

        PostDTO postDTO = postService.findPostByPostNo(postNo);
        System.out.println(postDTO.getCommentsDTOS().get(2).getComments_contents());

        return postDTO;
    }


/** 게시글 자세히 보기 페이지 및 iframe **/
    @GetMapping("/post/page")
    public String postDetail(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, String postName, Model model) {
        System.out.println("EKEKEKEKEKEKEK!! = " + postName);

        PostDTO postDTO = postService.isValidPost(postName);

        if (postDTO == null) {
            return null;
        }

        System.out.println("postDTO.getUser_no() == " + postDTO.getUser_no());
        System.out.println("loginMember.getUser_no() == " + loginMember.getUser_no());
        System.out.println("postDTO.getUser_no().equals(loginMember.getUser_no() == " + postDTO.getUser_no().equals(loginMember.getUser_no()));

        if (postDTO.getUser_no().equals(loginMember.getUser_no())) {
            model.addAttribute("hiddenThreeDotBtn", false);
        } else {
            model.addAttribute("hiddenThreeDotBtn", true);
        }

        List<CommentsDTO> comments = commentsService.findCommentsByPostNo(postDTO.getPost_no());

        model.addAttribute("comments", comments);
        model.addAttribute("post", postDTO);
        return "postDetail";
    }



/** 무한 스크롤로 보여줄 게시글보내주기. **/
    @GetMapping("/post/infiniteScroll")
    public String getMembers(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, Model model, Long post_no, int pageCount) {

        System.out.println("현재 페이지는 바로!!!!!!!! --> " +pageCount);

        //로그인한 회원이 팔로우하는(팔로잉) 회원들의 회원번호 가져오기.
        List<FollowDTO> followingList = followService.followingList(loginMember.getUser_no());
        List<PostDTO> postDTO = postService.selectPost(followingList, loginMember, pageCount);

        model.addAttribute("posts", postDTO);

        return "timeline2 :: #moreList"; // template html 파일 이름 + '::' + fragment의 id

    }
}
