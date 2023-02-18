package clone_project.stagram.service;

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
}
