package clone_project.stagram.repository.comments;

import clone_project.stagram.Entity.CommentsEntity;

import java.util.List;
import java.util.Optional;

public interface JpaCommentsRepositoryCustom {
    void deleteAllByPostNo(Long postNo);

    List<CommentsEntity> findCommentsByPostNo(Long post_no);

    void updateCommentsUser(Long user_no, String id);

}
