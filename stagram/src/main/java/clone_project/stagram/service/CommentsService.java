package clone_project.stagram.service;


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

    public void commentsRegister(CommentsDTO commentsDTO, UserDTO userDTO, PostDTO postDTO) {
        UserEntity userEntity = Mapper.mapToUserEntity(userDTO);
        PostEntity postEntity = Mapper.mapToPostEntity(postDTO, userEntity);

        CommentsEntity commentsEntity = Mapper.mapToCommentsEntity(commentsDTO, userEntity, postEntity);

        jpaCommentsRepository.save(commentsEntity);
    }

    @Transactional
    public void updateCommentsUserId(Long user_no, String id) {
        jpaCommentsRepositoryCustom.updateCommentsUser(user_no, id);
    }

    public void deleteComments(Long comments_no) {
        jpaCommentsRepository.deleteById(comments_no);
    }

    @Transactional
    public void deleteAllComments(Long postNo) {
        jpaCommentsRepositoryCustom.deleteAllByPostNo(postNo);
    }

    public List<CommentsDTO> findCommentsByPostNo(Long post_no) {

        List<CommentsEntity> commentsEntities = jpaCommentsRepositoryCustom.findCommentsByPostNo(post_no);

        List<CommentsDTO> commentsDTOS = Mapper.ListMapToCommentsDTO(commentsEntities);
        return commentsDTOS;
    }

    public CommentsDTO findCommentsByCommentsNo(Long comments_no) {
        Optional<CommentsEntity> commentsEntity = jpaCommentsRepository.findById(comments_no);

        if (commentsEntity.isPresent()) {
            CommentsDTO commentsDTO = Mapper.mapToCommentsDTO(commentsEntity);
            return commentsDTO;
        }

        return null;
    }
}
