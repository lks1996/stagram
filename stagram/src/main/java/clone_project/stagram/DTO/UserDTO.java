package clone_project.stagram.DTO;

import lombok.*;

import javax.validation.constraints.Email;

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
}
