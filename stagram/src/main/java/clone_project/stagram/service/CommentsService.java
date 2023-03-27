package clone_project.stagram.service;

<<<<<<< HEAD

=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
import clone_project.stagram.DTO.CommentsDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.CommentsEntity;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.comments.JpaCommentsRepository;
import clone_project.stagram.repository.comments.JpaCommentsRepositoryCustom;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {
    private final JpaCommentsRepository jpaCommentsRepository;
    private final JpaCommentsRepositoryCustom jpaCommentsRepositoryCustom;

    public CommentsService(JpaCommentsRepository jpaCommentsRepository, JpaCommentsRepositoryCustom jpaCommentsRepositoryCustom) {
        this.jpaCommentsRepository = jpaCommentsRepository;
        this.jpaCommentsRepositoryCustom = jpaCommentsRepositoryCustom;
    }

<<<<<<< HEAD
=======
    /** 댓글 등록 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    public void commentsRegister(CommentsDTO commentsDTO, UserDTO userDTO, PostDTO postDTO) {
        UserEntity userEntity = Mapper.mapToUserEntity(userDTO);
        PostEntity postEntity = Mapper.mapToPostEntity(postDTO, userEntity);

        CommentsEntity commentsEntity = Mapper.mapToCommentsEntity(commentsDTO, userEntity, postEntity);

        jpaCommentsRepository.save(commentsEntity);
    }

<<<<<<< HEAD
=======
    /** 댓글에 있는 회원 정보 업데이트 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Transactional
    public void updateCommentsUserId(Long user_no, String id) {
        jpaCommentsRepositoryCustom.updateCommentsUser(user_no, id);
    }

<<<<<<< HEAD
=======
    /** 댓글 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    public void deleteComments(Long comments_no) {
        jpaCommentsRepository.deleteById(comments_no);
    }

<<<<<<< HEAD
=======
    /** 게시글에 있는 댓글 모두 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Transactional
    public void deleteAllComments(Long postNo) {
        jpaCommentsRepositoryCustom.deleteAllByPostNo(postNo);
    }

<<<<<<< HEAD
=======
    /** 게시글에 있는 댓글 모두 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    public List<CommentsDTO> findCommentsByPostNo(Long post_no) {

        List<CommentsEntity> commentsEntities = jpaCommentsRepositoryCustom.findCommentsByPostNo(post_no);

        List<CommentsDTO> commentsDTOS = Mapper.ListMapToCommentsDTO(commentsEntities);
        return commentsDTOS;
    }

<<<<<<< HEAD
=======
    /** 댓글 번호로 댓글 찾기 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    public CommentsDTO findCommentsByCommentsNo(Long comments_no) {
        Optional<CommentsEntity> commentsEntity = jpaCommentsRepository.findById(comments_no);

        if (commentsEntity.isPresent()) {
            CommentsDTO commentsDTO = Mapper.mapToCommentsDTO(commentsEntity);
            return commentsDTO;
        }

        return null;
    }
}
