package clone_project.stagram.service;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.post.JpaPostRepository;
import clone_project.stagram.repository.post.JpaPostRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final JpaPostRepository jpaPostRepository;
    private final JpaPostRepositoryCustom jpaPostRepositoryCustom;

    public PostService(JpaPostRepository jpaPostRepository, JpaPostRepositoryCustom jpaPostRepositoryCustom) {
        this.jpaPostRepository = jpaPostRepository;
        this.jpaPostRepositoryCustom = jpaPostRepositoryCustom;
    }

    public void savePost(PostDTO postDTO) {
        PostEntity postEntity = Mapper.mapToPostEntity(postDTO);

        jpaPostRepository.save(postEntity);
    }

    public PostDTO isValidPost(String postImgName) {
        Optional<PostEntity> postEntity = jpaPostRepositoryCustom.findByPostImgName(postImgName);

        if (postEntity.isPresent()) {
            PostDTO postDTO = Mapper.mapToPostDTO(postEntity);

            return postDTO;
        }
        return null;
    }

    public List<PostDTO> selectPost() {
        List<PostEntity> postEntities = jpaPostRepository.findAll();
        List<PostDTO> postDTOS = Mapper.ListMapToPostDTO(postEntities);

        return postDTOS;
    }

    public List<PostDTO> getOwnPost(Long user_no) {
        List<PostEntity> postEntities = jpaPostRepositoryCustom.findAllUserById(user_no);
        List<PostDTO> postDTOS = Mapper.ListMapToPostDTO(postEntities);

        return postDTOS;
    }
}
