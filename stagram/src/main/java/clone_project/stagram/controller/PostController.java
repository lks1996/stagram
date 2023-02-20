package clone_project.stagram.controller;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.SavePath;
import clone_project.stagram.SessionConst;
import clone_project.stagram.service.PostService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
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

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/upload/post")
    public String upload_profile_pic(@SessionAttribute(name =SessionConst.LOGIN_MEMBER) UserDTO loginMember, @RequestParam MultipartFile postImg, @RequestParam String postContentsInForm) throws Exception{

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


            postService.savePost(postDTO);


            return "redirect:/";
        }

        return "redirect:/";
    }
    public String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
