package clone_project.stagram.service;

import clone_project.stagram.DTO.LikeDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.LikeEntity;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.like.JpaLIkeRepository;
import clone_project.stagram.repository.like.JpaLikeRepositoryCustom;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class LikeService {

    private final JpaLIkeRepository jpaLikeRepository;
    private final JpaLikeRepositoryCustom jpaLikeRepositoryCustom;

    public LikeService(JpaLIkeRepository jpaLIkeRepository, JpaLikeRepositoryCustom jpaLikeRepositoryCustom) {
        this.jpaLikeRepository = jpaLIkeRepository;
        this.jpaLikeRepositoryCustom = jpaLikeRepositoryCustom;
    }

<<<<<<< HEAD

=======
    /** 좋아요 등록 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    public void likeRegister(LikeDTO likeDTO, UserDTO loginMember, PostDTO postDTO) {
        UserEntity userEntity = Mapper.mapToUserEntity(loginMember);
        PostEntity postEntity = Mapper.mapToPostEntity(postDTO, userEntity);

        LikeEntity likeEntity = Mapper.mapToLikeEntity(likeDTO, userEntity, postEntity);

        jpaLikeRepository.save(likeEntity);
    }

<<<<<<< HEAD
=======
    /** 좋아요 취소 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Transactional
    public void cancelLike(Long post_no, Long user_no) {

        jpaLikeRepositoryCustom.deleteLike(post_no, user_no);

    }

<<<<<<< HEAD
=======
    /** 게시글에 회원이 좋아요 했는지 확인 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    public LikeDTO findLikeByUserNoAndPostNo(Long user_no, Long post_no) {
        Optional<LikeEntity> likeEntity = jpaLikeRepositoryCustom.findByUserNoAndPostNo(user_no, post_no);

        if (likeEntity.isPresent()) {
            LikeDTO likeDTO = Mapper.mapToLikeDTO(likeEntity);
            return likeDTO;
        }

        return null;
    }

<<<<<<< HEAD
=======
    /** 특정 게시글의 모든 좋아요 삭제 **/
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
    @Transactional
    public void deleteAllLikes(Long postNo) {
        jpaLikeRepositoryCustom.deleteAllLikes(postNo);
    }
}
