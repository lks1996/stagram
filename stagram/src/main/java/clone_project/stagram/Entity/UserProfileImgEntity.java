package clone_project.stagram.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor //기본 생성자를 만들어줌
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

    @Column(name = "user_no")
    private Long user_no;


    @Builder
    public UserProfileImgEntity(Long userImgNo, String profileImgOriginName, String profileImgName,
                                long profileImgSize, String regDate, Long UserNo) {
        this.userImgNo = userImgNo;
        this.profileImgOriginName = profileImgOriginName;
        this.profileImgName = profileImgName;
        this.profileImgSize = profileImgSize;
        this.regDate = regDate;
        this.user_no = UserNo;
    }
}
