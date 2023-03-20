package clone_project.stagram.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private Long user_no;
    @Email
    private String email;
    private String id;
    private String password;
    private String name;
    private String bio;
    private String regDate;

    private UserProfileImgDTO userProfileImgDTO = new UserProfileImgDTO();
    private List<PostDTO> postDTOS = new ArrayList<>();
    private List<CommentsDTO> commentsDTOS = new ArrayList<>();
    private List<FollowDTO> followingDTOS = new ArrayList<>();
    private List<FollowDTO> followerDTOS = new ArrayList<>();
    private List<LikeDTO> likeDTOS = new ArrayList<>();

}
