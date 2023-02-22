package clone_project.stagram.repository.post;

import clone_project.stagram.Entity.PostEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaPostRepositoryCustomImpl implements JpaPostRepositoryCustom {

    private final EntityManager em;

    public JpaPostRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<PostEntity> findByPostImgName(String postImgName) {
        List<PostEntity> postEntity = em.createQuery("select m from PostEntity m where m.postImgName =:postImgName", PostEntity.class)
                .setParameter("postImgName", postImgName)
                .getResultList();
        return postEntity.stream().findAny();
    }

    @Override
    public List<PostEntity> findAllUserById(String user_id) {
        List<PostEntity> postEntity = em.createQuery("select m from PostEntity m where m.user_id =:user_id", PostEntity.class)
                .setParameter("user_id", user_id)
                .getResultList();
        return postEntity;
    }
}
