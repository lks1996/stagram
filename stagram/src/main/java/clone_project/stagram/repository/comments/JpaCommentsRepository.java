package clone_project.stagram.repository.comments;

import clone_project.stagram.Entity.CommentsEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentsRepository extends JpaRepository<CommentsEntity, Long> {
}
