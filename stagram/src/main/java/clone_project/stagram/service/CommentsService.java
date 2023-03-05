package clone_project.stagram.service;


import clone_project.stagram.DTO.CommentsDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.CommentsEntity;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.comments.JpaCommentsRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private final JpaCommentsRepository jpaCommentsRepository;

    public CommentsService(JpaCommentsRepository jpaCommentsRepository) {
        this.jpaCommentsRepository = jpaCommentsRepository;
    }

    public void commentsRegister(CommentsDTO commentsDTO, UserDTO userDTO, PostDTO postDTO) {
        UserEntity userEntity = Mapper.mapToEntity(userDTO);
        PostEntity postEntity = Mapper.mapToPostEntity(postDTO, userEntity);

        CommentsEntity commentsEntity = Mapper.mapToCommentsEntity(commentsDTO, userEntity, postEntity);

        jpaCommentsRepository.save(commentsEntity);
    }
}
