package clone_project.stagram.DTO;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfileImgDTO {

    private int userImgNo;

    private String profileImgName;

    private long profileImgSize;

    private String profileImgContentType;

    private byte[] profileData;

    private int UserNo;

    private MultipartFile file;
}
