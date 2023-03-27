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

<<<<<<< HEAD
=======
    /** 게시글 사진 이름으로 게시글 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public Optional<PostEntity> findByPostImgName(String postImgName) {
        List<PostEntity> postEntity = em.createQuery("select m from PostEntity m where m.postImgName =:postImgName", PostEntity.class)
                .setParameter("postImgName", postImgName)
                .getResultList();
        return postEntity.stream().findAny();
    }

<<<<<<< HEAD
    @Override
    public List<PostEntity> findAllUserById(Long user_no) {
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDD:" + user_no);
=======
    /** 회원 번호로 모든 게시글 찾기 **/
    @Override
    public List<PostEntity> findAllUserById(Long user_no) {
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
        List<PostEntity> postEntity = em.createQuery("select m from PostEntity m where m.userEntity.user_no =:user_no", PostEntity.class)
                .setParameter("user_no", user_no)
                .getResultList();
        return postEntity;
    }

<<<<<<< HEAD
=======
    /** 게시글에 있는 사용자 정보 업데이트 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public void updatePostUser(Long user_no, String id) {
        System.out.println("[Post] user_no가 " + user_no + "인 사용자의 사용자 이름을 " + id + "수정시작.");

        em.createQuery("update PostEntity m set m.user_id =:user_id where m.userEntity.user_no = :user_no")
                .setParameter("user_id", id)
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();

        System.out.println("[Post] 수정 완료");
    }

<<<<<<< HEAD
=======
    /** 회원 번호에 해당하는 모든 게시글 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public void deleteByUserNo(Long user_no) {
        em.createQuery("delete from PostEntity m where m.userEntity.user_no = :user_no")
                .setParameter("user_no", user_no)
                .executeUpdate();
        em.clear();
    }

<<<<<<< HEAD
=======
    /** 게시글 내용 수정 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Override
    public void updatePostContents(Long post_no, String contents) {
        System.out.println("post_no " + post_no + "인 게시글의 내용을 " + contents + "로 수정시작.");

        em.createQuery("update PostEntity m set m.contents =:contents where m.post_no = :post_no")
                .setParameter("contents", contents)
                .setParameter("post_no", post_no)
                .executeUpdate();
        em.clear();

        System.out.println("수정 완료");
    }

}
