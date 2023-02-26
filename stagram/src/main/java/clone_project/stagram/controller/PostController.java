package clone_project.stagram.controller;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.SavePath;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.PostService;
import clone_project.stagram.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


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

    @GetMapping(value = "/post/display", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> postDisplay(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, String postName) throws IOException {
        String profileImg;

        PostDTO postDTO = postService.isValidPost(postName);

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

    public String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
