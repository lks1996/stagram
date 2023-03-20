package clone_project.stagram.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {

    private Long post_no;
    private String contents;
    private String post_regDate;

    private Long user_no;
    private String user_id;

    private String postImgOriginName;
    private String postImgName;
    private Long postImgSize;

    private List<CommentsDTO> commentsDTOS = new ArrayList<>();
    private List<LikeDTO> likeDTOS = new ArrayList<>();
}
