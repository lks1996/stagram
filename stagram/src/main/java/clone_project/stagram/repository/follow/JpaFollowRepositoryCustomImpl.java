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

<<<<<<< HEAD
=======
    /** 로그인한 유저와 특정 유저(otherUser)가 팔로우 관계인지 확인 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public Optional<FollowEntity> findByLoginUserNoAndOtherUserNo(Long loginUserNo, Long otherUserNo) {
        List<FollowEntity> followEntity = em.createQuery("select m from FollowEntity m where m.follow_from_user_no.user_no =:loginUserNo and m.follow_to_user_no.user_no =:otherUserNo", FollowEntity.class)
                .setParameter("loginUserNo", loginUserNo)
                .setParameter("otherUserNo", otherUserNo)
                .getResultList();
        return followEntity.stream().findAny();
    }

<<<<<<< HEAD
=======
    /** 회원의 팔로잉 모두 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public List<FollowEntity> findFollowingUsers(Long user_no) {
        List<FollowEntity> followEntityList = em.createQuery("select m from FollowEntity m where m.follow_from_user_no.user_no =: user_no", FollowEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();

        return followEntityList;
    }

<<<<<<< HEAD
=======
    /** 회원의 팔로워 모두 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public List<FollowEntity> findFollowerUsers(Long user_no) {
        List<FollowEntity> followEntityList = em.createQuery("select m from FollowEntity m where m.follow_to_user_no.user_no =: user_no", FollowEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();

        return followEntityList;
    }
}
