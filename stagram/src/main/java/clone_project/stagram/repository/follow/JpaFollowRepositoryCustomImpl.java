package clone_project.stagram.repository.follow;

import clone_project.stagram.Entity.FollowEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaFollowRepositoryCustomImpl implements JpaFollowRepositoryCustom{
    private final EntityManager em;

    public JpaFollowRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<FollowEntity> findByLoginUserNoAndOtherUserNo(Long loginUserNo, Long otherUserNo) {
        List<FollowEntity> followEntity = em.createQuery("select m from FollowEntity m where m.follow_from.user_no =:loginUserNo and m.follow_to.user_no =:otherUserNo", FollowEntity.class)
                .setParameter("loginUserNo", loginUserNo)
                .setParameter("otherUserNo", otherUserNo)
                .getResultList();
        return followEntity.stream().findAny();
    }

    @Override
    public List<FollowEntity> findFollowingUsers(Long user_no) {
        List<FollowEntity> followEntityList = em.createQuery("select m from FollowEntity m where m.follow_from.user_no =: user_no", FollowEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();

        return followEntityList;
    }

    @Override
    public List<FollowEntity> findFollowerUsers(Long user_no) {
        List<FollowEntity> followEntityList = em.createQuery("select m from FollowEntity m where m.follow_to.user_no =: user_no", FollowEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();

        return followEntityList;
    }
}
