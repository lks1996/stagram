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

<<<<<<< HEAD
=======
    /** 게시글 번호에 해당하는 특정 회원의 좋아요 모두 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public void deleteLike(Long post_no, Long user_no) {
        em.createQuery("delete from LikeEntity m where m.postEntity.post_no = :post_no and m.userEntity.user_no = :user_no")
                .setParameter("post_no", post_no)
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();
    }

<<<<<<< HEAD
=======
    /** 회원 번호와 게시글 번호로 해당 회원이 게시글을 좋아요 했는지 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public Optional<LikeEntity> findByUserNoAndPostNo(Long user_no, Long post_no) {
        List<LikeEntity> likeEntity = em.createQuery("select m from LikeEntity m where m.postEntity.post_no = :post_no and m.userEntity.user_no = :user_no", LikeEntity.class)
                .setParameter("post_no", post_no)
                .setParameter("user_no", user_no)
                .getResultList();
        return likeEntity.stream().findAny();
    }

<<<<<<< HEAD
=======
    /** 게시글에 해당하는 좋아요 모두 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public void deleteAllLikes(Long post_no) {
        em.createQuery("delete from LikeEntity m where m.postEntity.post_no = :post_no")
                .setParameter("post_no", post_no)
                .executeUpdate();
        em.clear();
    }
}
