package clone_project.stagram.repository.like;

import clone_project.stagram.Entity.LikeEntity;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
import java.util.Optional;

public interface JpaLikeRepositoryCustom {
    void deleteLike(Long post_no, Long user_no);

    Optional<LikeEntity> findByUserNoAndPostNo(Long user_no, Long post_no);

    void deleteAllLikes(Long postNo);

}
