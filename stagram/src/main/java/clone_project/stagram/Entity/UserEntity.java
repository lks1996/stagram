package clone_project.stagram.Entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "user")
@Getter
public class UserEntity implements Serializable {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_no;
    @Column(name = "email")
    private String email;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "bio")
    private String bio;
    @Column(name = "regDate")
    private String regDate;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postEntityList = new ArrayList<>();

//    @OneToOne(mappedBy = "userEntity")
//    private UserProfileImgEntity userProfileImgEntity = new UserProfileImgEntity();

    @OneToMany(mappedBy = "userEntity")
    private List<CommentsEntity> commentsEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<LikeEntity> likeEntityList = new ArrayList<>();
    
    @OneToMany(mappedBy = "follow_from_user_no")
    private List<FollowEntity> followerEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "follow_to_user_no")
    private List<FollowEntity> followingEntityList = new ArrayList<>();

    @Builder
    public UserEntity(Long user_no, String email, String user_id,
                      String password, String name, String bio,
                      String regDate) {
        this.user_no = user_no;
        this.email = email;
        this.user_id = user_id;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.regDate = regDate;
    }

}

