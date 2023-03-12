package clone_project.stagram.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "comments")
@Getter
public class CommentsEntity {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comments_no;

    @Column(name = "comments_regDate")
    private String comments_regDate;

    @Column(name = "comments_contents")
    private String comments_contents;

    @Column(name = "user_id")
    private String user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private PostEntity postEntity;

    public CommentsEntity(Long comments_no, String comments_regDate, String comments_contents, String user_id,
                          UserEntity userEntity, PostEntity postEntity) {
        this.comments_no = comments_no;
        this.comments_regDate = comments_regDate;
        this.comments_contents = comments_contents;
        this.user_id = user_id;
        this.userEntity = userEntity;
        this.postEntity = postEntity;
    }
}
