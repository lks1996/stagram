package clone_project.stagram.repository.post;

import clone_project.stagram.Entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepositoryCustom {

    Optional<PostEntity> findByPostImgName(String postImgName);

    List<PostEntity> findAllUserById(String user_id);
}
