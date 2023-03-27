package clone_project.stagram.repository.userProfile;

import clone_project.stagram.Entity.UserProfileImgEntity;
<<<<<<< HEAD
import clone_project.stagram.repository.userProfile.JpaUserProfileImgRepositoryCustom;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUserProfileImgRepositoryCustomImpl implements JpaUserProfileImgRepositoryCustom {

    private final EntityManager em;

    public JpaUserProfileImgRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

<<<<<<< HEAD
=======
    /** 회원 번호에 해당하는 프로필 사진 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public Optional<UserProfileImgEntity> findByUserNo(Long user_no) {
        List<UserProfileImgEntity> userProfileImgEntities = em.createQuery("select m from UserProfileImgEntity m where m.userEntity.user_no = :user_no", UserProfileImgEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();
        return userProfileImgEntities.stream().findAny();
    }

<<<<<<< HEAD
=======
    /** 회원 번호에 해당하는 프로필 사진 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public void deleteByUserNo(Long user_no) {
        em.createQuery("delete from UserProfileImgEntity m where m.userEntity.user_no = :user_no")
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();
    }

}
