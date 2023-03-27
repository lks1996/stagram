package clone_project.stagram.repository.like;

import clone_project.stagram.Entity.LikeEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaLikeRepositoryCustomImpl implements JpaLikeRepositoryCustom{

    private final EntityManager em;

    public JpaLikeRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void deleteLike(Long post_no, Long user_no) {
        em.createQuery("delete from LikeEntity m where m.postEntity.post_no = :post_no and m.userEntity.user_no = :user_no")
                .setParameter("post_no", post_no)
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();
    }

    @Override
    public Optional<LikeEntity> findByUserNoAndPostNo(Long user_no, Long post_no) {
        List<LikeEntity> likeEntity = em.createQuery("select m from LikeEntity m where m.postEntity.post_no = :post_no and m.userEntity.user_no = :user_no", LikeEntity.class)
                .setParameter("post_no", post_no)
                .setParameter("user_no", user_no)
                .getResultList();
        return likeEntity.stream().findAny();
    }

    @Override
    public void deleteAllLikes(Long post_no) {
        em.createQuery("delete from LikeEntity m where m.postEntity.post_no = :post_no")
                .setParameter("post_no", post_no)
                .executeUpdate();
        em.clear();
    }
}
