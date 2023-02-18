package clone_project.stagram.repository.user;

import clone_project.stagram.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, JpaUserRepositoryCustom {
}
