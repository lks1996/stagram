package clone_project.stagram.controller;

import clone_project.stagram.service.PostService;
import org.springframework.stereotype.Controller;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
}
