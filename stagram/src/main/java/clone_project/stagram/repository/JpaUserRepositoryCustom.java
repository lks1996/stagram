package clone_project.stagram.repository;

import clone_project.stagram.Entity.UserEntity;

import java.util.Optional;

public interface JpaUserRepositoryCustom {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(String id);
}
