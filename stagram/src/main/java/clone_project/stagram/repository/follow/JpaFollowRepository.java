package clone_project.stagram.repository.follow;

import clone_project.stagram.Entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFollowRepository extends JpaRepository<FollowEntity, Long>, JpaFollowRepositoryCustom {
}
