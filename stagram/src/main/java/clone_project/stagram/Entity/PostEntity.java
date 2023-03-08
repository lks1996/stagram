package clone_project.stagram.Entity;

import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "post")
@Getter
public class PostEntity implements Serializable {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_no;
    @Column(name = "contents")
    private String contents;
    @Column(name = "post_regDate")
    private String post_regDate;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "postImgOriginName")
    private String postImgOriginName;
    @Column(name = "postImgName")
    private String postImgName;
    @Column(name = "postImgSize")
    private Long postImgSize;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "postEntity")
    private List<CommentsEntity> commentsEntityList = new ArrayList<>();


    public PostEntity(Long post_no, String contents, String post_regDate,
                      String user_id, String postImgOriginName, String postImgName,
                      Long postImgSize, UserEntity userEntity) {
        this.post_no = post_no;
        this.contents = contents;
        this.post_regDate = post_regDate;
        this.user_id = user_id;
        this.postImgOriginName = postImgOriginName;
        this.postImgName = postImgName;
        this.postImgSize = postImgSize;
        this.userEntity = userEntity;
    }

}
