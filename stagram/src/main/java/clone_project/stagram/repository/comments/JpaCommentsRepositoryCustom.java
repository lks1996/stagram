package clone_project.stagram.repository.comments;

import clone_project.stagram.Entity.CommentsEntity;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)

public interface JpaCommentsRepositoryCustom {
    void deleteAllByPostNo(Long postNo);

    List<CommentsEntity> findCommentsByPostNo(Long post_no);

    void updateCommentsUser(Long user_no, String id);

}
