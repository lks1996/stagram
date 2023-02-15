package clone_project.stagram.Entity;

import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "user")
@Getter
public class UserEntity {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_no;
    @Column(name = "email")
    private String email;
    @Column(name = "id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "bio")
    private String bio;
    @Column(name = "regDate")
    private String regDate;

    @Builder
    public UserEntity(Long user_no, String email, String id,
                      String password, String name, String bio,
                      String regDate) {
        this.user_no = user_no;
        this.email = email;
        this.id = id;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.regDate = regDate;
    }
}

