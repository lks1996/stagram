package clone_project.stagram.service;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.post.JpaPostRepository;
import clone_project.stagram.repository.post.JpaPostRepositoryCustom;
import org.springframework.stereotype.Service;

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
}
