package clone_project.stagram.DTO;

import lombok.Data;

@Data
public class LikeDTO {

    private Long like_no;
    private String like_regDate;

    private Long user_no;
    private Long post_no;
}
