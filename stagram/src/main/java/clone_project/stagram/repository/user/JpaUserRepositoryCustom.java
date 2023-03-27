package clone_project.stagram.repository.user;

import clone_project.stagram.Entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepositoryCustom {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(String id);

}
