package clone_project.stagram.repository.follow;

import clone_project.stagram.Entity.FollowEntity;

import java.util.List;
import java.util.Optional;

public interface JpaFollowRepositoryCustom {
    Optional<FollowEntity> findByLoginUserNoAndOtherUserNo(Long loginUserNo, Long otherUserNo);

    List<FollowEntity> findFollowingUsers(Long user_no);

    List<FollowEntity> findFollowerUsers(Long user_no);

}
