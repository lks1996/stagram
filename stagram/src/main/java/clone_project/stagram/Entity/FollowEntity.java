package clone_project.stagram.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "follow")
@Getter
public class FollowEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follow_no;

    // follow_from 가 나를 팔로우함.(팔로워)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_from_user_no")
    private UserEntity follow_from_user_no;
    // 내가 follow_to 유저를 팔로우함.(팔로잉)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_to_user_no")
    private UserEntity follow_to_user_no;

    public FollowEntity(Long follow_no, UserEntity follow_from_user_no, UserEntity follow_to_user_no) {
        this.follow_no = follow_no;
        this.follow_from_user_no = follow_from_user_no;
        this.follow_to_user_no = follow_to_user_no;
    }
}
