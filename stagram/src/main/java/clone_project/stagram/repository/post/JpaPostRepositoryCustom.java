package clone_project.stagram.repository.post;

import clone_project.stagram.Entity.PostEntity;

import java.util.Optional;

public interface JpaPostRepositoryCustom {

    Optional<PostEntity> findByUserNoAndPostImgName(String postImgName);

}
