package clone_project.stagram.DTO;

import lombok.Data;


@Data
public class CommentsDTO {
    private Long comments_no;
    private String comments_regDate;
    private String comments_contents;

    private String user_id;
    private Long user_no;
    private Long post_no;
}
