package clone_project.stagram.repository;

import clone_project.stagram.Entity.UserProfileImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserProfileImgRepository extends JpaRepository<UserProfileImgEntity, Long> {
}
