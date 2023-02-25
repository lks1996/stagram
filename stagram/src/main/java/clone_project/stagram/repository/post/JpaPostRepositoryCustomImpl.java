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
    public List<PostEntity> findAllUserById(Long user_no) {
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDD:" + user_no);
        List<PostEntity> postEntity = em.createQuery("select m from PostEntity m where m.userEntity.user_no =:user_no", PostEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();
        return postEntity;
    }
}
