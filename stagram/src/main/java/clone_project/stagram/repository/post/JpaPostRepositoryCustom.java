package clone_project.stagram.repository.post;

import clone_project.stagram.Entity.PostEntity;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)

import java.util.List;
import java.util.Optional;

public interface JpaPostRepositoryCustom {

    Optional<PostEntity> findByPostImgName(String postImgName);

    List<PostEntity> findAllUserById(Long user_no);

    void updatePostUser(Long user_no, String id);

    void deleteByUserNo(Long user_no);

    void updatePostContents(Long post_no, String contents);
}
