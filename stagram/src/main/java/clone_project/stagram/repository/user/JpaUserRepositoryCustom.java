package clone_project.stagram.repository.user;

import clone_project.stagram.Entity.UserEntity;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
import java.util.Optional;

public interface JpaUserRepositoryCustom {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(String id);

}
