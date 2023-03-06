package clone_project.stagram.repository.comments;

import clone_project.stagram.Entity.CommentsEntity;
import clone_project.stagram.Entity.PostEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaCommentsRepositoryCustomImpl implements JpaCommentsRepositoryCustom{
    private final EntityManager em;

    public JpaCommentsRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void deleteAllByPostNo(Long post_no) {
        em.createQuery("delete from CommentsEntity m where m.postEntity.post_no = :post_no")
                .setParameter("post_no", post_no)
                .executeUpdate();
        em.clear();
    }

    @Override
    public List<CommentsEntity> findCommentsByPostNo(Long post_no) {
        List<CommentsEntity> commentsEntities = em.createQuery("select m from CommentsEntity m where m.postEntity.post_no =:post_no", CommentsEntity.class)
                .setParameter("post_no", post_no)
                .getResultList();
        return commentsEntities;
    }
}
