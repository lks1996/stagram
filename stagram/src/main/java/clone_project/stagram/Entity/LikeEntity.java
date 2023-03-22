package clone_project.stagram.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name = "likey")
@Getter
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_no;

    @Column(name = "like_regDate")
    private String like_regDate;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_no")
    @JoinColumns(value = {
            @JoinColumn(name = "user_no", referencedColumnName="user_no"),
            @JoinColumn(name = "user_id", referencedColumnName="user_id")
    })

    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private PostEntity postEntity;

    public LikeEntity(Long like_no, String like_regDate, UserEntity userEntity, PostEntity postEntity) {
        this.like_no = like_no;
        this.like_regDate = like_regDate;
        this.userEntity = userEntity;
        this.postEntity = postEntity;
    }
}
