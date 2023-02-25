package clone_project.stagram.repository.user;

import clone_project.stagram.Entity.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUserRepositoryCustomImpl implements JpaUserRepositoryCustom {

    private final EntityManager em;

    public JpaUserRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        List<UserEntity> userEntity = em.createQuery("select m from UserEntity m where m.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultList();
        return userEntity.stream().findAny();
    }

    @Override
    public Optional<UserEntity> findById(String id) {
        List<UserEntity> userEntity = em.createQuery("select m from UserEntity m where m.id = :id", UserEntity.class)
                .setParameter("id", id)
                .getResultList();
        return userEntity.stream().findAny();    }


}
