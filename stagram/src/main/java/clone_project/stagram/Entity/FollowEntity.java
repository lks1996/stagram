package clone_project.stagram.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "follow")
@Getter
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follow_no;

    // follow_from 가 나를 팔로우함.(팔로잉)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_from")
    private UserEntity follow_from_userEntity;
    // 내가 follow_to 유저를 팔로우함.(팔로워)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_to")
    private UserEntity follow_to_userEntity;

    public FollowEntity(Long follow_no, UserEntity follow_from, UserEntity follow_to) {
        this.follow_no = follow_no;
        this.follow_from_userEntity = follow_from;
        this.follow_to_userEntity = follow_to;
    }
}
