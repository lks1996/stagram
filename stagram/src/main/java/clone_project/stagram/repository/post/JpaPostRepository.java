package clone_project.stagram.repository.post;

import clone_project.stagram.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long>, JpaPostRepositoryCustom {

}
