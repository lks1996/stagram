package clone_project.stagram.repository.comments;

import clone_project.stagram.Entity.CommentsEntity;

import javax.persistence.EntityManager;
import java.util.List;

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

    @Override
    public void updateCommentsUser(Long user_no, String id) {
        System.out.println("[Comments] user_no가 " + user_no + "인 사용자의 사용자 이름을 " + id + "수정시작.");

        em.createQuery("update CommentsEntity m set m.user_id =:user_id where m.userEntity.user_no = :user_no")
                .setParameter("user_id", id)
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();

        System.out.println("[Comments] 수정 완료");
    }
}
