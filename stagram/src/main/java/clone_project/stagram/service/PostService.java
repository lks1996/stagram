package clone_project.stagram.service;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.post.JpaPostRepository;
import clone_project.stagram.repository.post.JpaPostRepositoryCustom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/** 게시글 저장 **/
    public void savePost(PostDTO postDTO, UserDTO userDTO) {
        UserEntity userEntity = Mapper.mapToUserEntity(userDTO);
        PostEntity postEntity = Mapper.mapToPostEntity(postDTO, userEntity);

        jpaPostRepository.save(postEntity);
    }


/** 게시글 이름이 유효한 것인지 판별. **/
    public PostDTO isValidPost(String postImgName) {
        Optional<PostEntity> postEntity = jpaPostRepositoryCustom.findByPostImgName(postImgName);

        if (postEntity.isPresent()) {
            PostDTO postDTO = Mapper.mapToPostDTO(postEntity);

            return postDTO;
        }
        return null;
    }


/** 게시글 모두 보여주기(어떤 게시글을 보여줄지 로직 작성 할 것.) **/
    public List<PostDTO> selectPost() {
        List<PostEntity> postEntities = jpaPostRepository.findAll();
        List<PostDTO> postDTOS = Mapper.ListMapToPostDTO(postEntities);

        return postDTOS;
    }


/** 회원 번호로 해당 회원이 작성한 게시글 전부 찾기. **/
    public List<PostDTO> getOwnPost(Long user_no) {
        List<PostEntity> postEntities = jpaPostRepositoryCustom.findAllUserById(user_no);
        List<PostDTO> postDTOS = Mapper.ListMapToPostDTO(postEntities);

        return postDTOS;
    }

/** 게시글 번호로 해당 게시글 찾기. **/
    public PostDTO findPostByPostNo(Long postNo) {
        Optional<PostEntity> postEntity = jpaPostRepository.findById(postNo);

        if (postEntity.isPresent()) {
            PostDTO postDTO = Mapper.mapToPostDTO(postEntity);
            return postDTO;
        }

        return null;
    }

/** 게시글 정보에 저장되어있는 회원 아이디 수정. **/
    @Transactional
    public void updatePostUserId(Long user_no, String id) {
        jpaPostRepositoryCustom.updatePostUser(user_no, id);
    }

/** 게시글 내용 수정 **/
    @Transactional
    public void updatePostContents(Long postNo, String postContents) {
        jpaPostRepositoryCustom.updatePostContents(postNo, postContents);
    }

/** 회원 번호로 해당 회원이 작성한 게시글 모두 삭제. **/
    @Transactional
    public void deletePost(Long user_no) {
        jpaPostRepositoryCustom.deleteByUserNo(user_no);

        System.out.println("1. 사용자의 게시글 모두 삭제 완료.");
    }

/** 게시글 번호로 해당 게시글만 삭제 **/
    public void deleteOnePost(Long post_no) {
        jpaPostRepository.deleteById(post_no);

    }


}
