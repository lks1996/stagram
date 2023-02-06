package clone_project.stagram.repository;

import clone_project.stagram.Entity.UserEntity;

public interface JpaUserRepositoryCustom {
    UserEntity findByEmail(String email);
}
