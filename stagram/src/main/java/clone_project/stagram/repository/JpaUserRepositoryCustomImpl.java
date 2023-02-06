package clone_project.stagram.repository;

import clone_project.stagram.Entity.UserEntity;

import javax.persistence.EntityManager;

public class JpaUserRepositoryCustomImpl implements JpaUserRepositoryCustom{

    private final EntityManager em;

    public JpaUserRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public UserEntity findByEmail(String email) {
        UserEntity userEntity = em.createQuery("select m from UserEntity m where m.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getSingleResult();
        return userEntity;
    }
}
