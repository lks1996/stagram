package clone_project.stagram.repository.userProfile;

import clone_project.stagram.Entity.UserProfileImgEntity;
import clone_project.stagram.repository.userProfile.JpaUserProfileImgRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUserProfileImgRepositoryCustomImpl implements JpaUserProfileImgRepositoryCustom {

    private final EntityManager em;

    public JpaUserProfileImgRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<UserProfileImgEntity> findByUserNo(Long user_no) {
        List<UserProfileImgEntity> userProfileImgEntities = em.createQuery("select m from UserProfileImgEntity m where m.userEntity.user_no = :user_no", UserProfileImgEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();
        return userProfileImgEntities.stream().findAny();
    }

    @Override
    public void deleteByUserNo(Long user_no) {
        em.createQuery("delete from UserProfileImgEntity m where m.userEntity.user_no = :user_no")
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();
    }

}
