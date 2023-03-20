package clone_project.stagram.repository.like;

import clone_project.stagram.Entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLIkeRepository extends JpaRepository<LikeEntity, Long> {
}
