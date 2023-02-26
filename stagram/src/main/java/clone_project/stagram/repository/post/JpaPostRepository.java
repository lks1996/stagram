package clone_project.stagram.repository.post;

import clone_project.stagram.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long>, JpaPostRepositoryCustom {
//    @Modifying
//    @Query("UPDATE post m set m.user_id =:id where m.user_no = :user_no")
//    void updatePostUserId(Long user_no, String id);
}
