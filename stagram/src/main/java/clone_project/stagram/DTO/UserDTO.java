package clone_project.stagram.DTO;

import lombok.*;

@Data
public class UserDTO {
    private Long user_no;
    private String email;
    private String id;
    private String password;
    private String name;
    private String bio;
    private String regDate;
}
