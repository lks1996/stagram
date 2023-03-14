package clone_project.stagram.DTO;

import lombok.Data;

@Data
public class FollowDTO {

    private Long follow_no;

    private Long follow_from_user_no;
    private Long follow_to_user_no;
}
