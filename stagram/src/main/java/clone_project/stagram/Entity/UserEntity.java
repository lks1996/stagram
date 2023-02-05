package clone_project.stagram.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor //여기에 필드에 쓴 모든생성자만 만들어줌
@NoArgsConstructor //기본 생성자를 만들어줌
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
    private String pw;
    @Column(name = "name")
    private String name;
    @Column(name = "bio")
    private String bio;
    @Column(name = "regDate")
    private Date regDate;
}
