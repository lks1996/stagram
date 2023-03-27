package clone_project.stagram.Entity;

import lombok.AccessLevel;
<<<<<<< HEAD
import lombok.Builder;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자를 만들어줌
@Table(name= "user_profile_img")
@Getter
public class UserProfileImgEntity {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userImgNo;

    @Column(name = "profileImgOriginName")
    private String profileImgOriginName;

    @Column(name = "profileImgName")
    private String profileImgName;

    @Column(name = "profileImgSize")
    private long profileImgSize;

    @Column(name = "regDate")
    private String regDate;

    @OneToOne
    @JoinColumn(name = "user_no")
    private UserEntity userEntity;


    public UserProfileImgEntity(Long userImgNo, String profileImgOriginName, String profileImgName,
                                Long profileImgSize, String regDate, UserEntity userEntity) {
        this.userImgNo = userImgNo;
        this.profileImgOriginName = profileImgOriginName;
        this.profileImgName = profileImgName;
        this.profileImgSize = profileImgSize;
        this.regDate = regDate;
        this.userEntity = userEntity;
    }
}
