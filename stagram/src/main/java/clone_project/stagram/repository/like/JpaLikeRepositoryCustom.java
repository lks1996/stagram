package clone_project.stagram.repository.like;

import clone_project.stagram.Entity.LikeEntity;

import java.util.List;
import java.util.Optional;

public interface JpaLikeRepositoryCustom {
    void deleteLike(Long post_no, Long user_no);

    Optional<LikeEntity> findByUserNoAndPostNo(Long user_no, Long post_no);

}
