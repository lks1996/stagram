package clone_project.stagram.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;


@Entity
@AllArgsConstructor //여기에 필드에 쓴 모든생성자만 만들어줌
@NoArgsConstructor //기본 생성자를 만들어줌
@Table(name= "user_profile_img")
@Getter
public class UserProfileImgEntity {

    @Id  // Primary Key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userImgNo;
    @Column(name = "profileImgName")
    private String profileImgName;
    @Column(name = "profileImgSize")
    private long profileImgSize;
    @Column(name = "profileImgContentType")
    private String profileImgContentType;
    @Column(name = "profileData")
    private byte[] profileData;
    @Column(name = "user_no")
    private int UserNo;

}
