package clone_project.stagram.service;

import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.post.JpaPostRepository;
import clone_project.stagram.repository.post.JpaPostRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
//본인 게시글과 본인이 팔로우 하는 회원의 게시글을 최신순으로 출력.
    public List<PostDTO> selectPost(List<FollowDTO> followingList, UserDTO loginMember) {

        //회원이 팔로우하는 회원의 게시글과 본인의 게시글을 담을 게시글 엔티티 선언.
        List<PostEntity> followerPostList = new ArrayList<>();

        //모든 게시글을 등록일 기준 최신순으로 가져옴.
        List<PostEntity> postEntities = jpaPostRepository.findAll(Sort.by(Sort.Direction.DESC, "postRegDate"));

        //db에서 가져온 게시글의 수 만큼 i 반복, 팔로잉 수 만큼 j 반복 -> 2중 for 문으로 일일히 비교하면서 출력할 게시글 판별 (상당히 비효율적.)
        for (int i = 0; i < postEntities.size(); i++) {
            for (int j = 0; j < followingList.size(); j++) {
                if (postEntities.get(i).getUserEntity().getUser_no().equals(followingList.get(j).getFollow_to_user_no()) ||
                        postEntities.get(i).getUserEntity().getUser_no().equals(loginMember.getUser_no())) {
                    followerPostList.add(postEntities.get(i));
                }
            }
        }

        //정리한 게시글 리스트를 DTO로 변환
        List<PostDTO> postDTOS = Mapper.ListMapToPostDTO(followerPostList);

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

/** 전체 게시글 조회 **/
    public List<PostDTO> findAllPost() {
        List<PostEntity> postEntities = jpaPostRepository.findAll();
        List<PostDTO> postDTOS = Mapper.ListMapToPostDTO(postEntities);

        return postDTOS;
    }


/** 게시글 페이징 **/
    public List<PostDTO> pagination(List<PostDTO> postDTOS, int pageCount) {
        //페이징에 필요한 변수들
        int paginationSize = 4;
        int firstIndex = 0 + (paginationSize * pageCount);
        int lastIndex = 4 + (paginationSize * pageCount);

        System.out.println("정리 된 postDTO 사이즈 " + postDTOS.size());
        System.out.println("lastIndex ====" + lastIndex);
        System.out.println("postDTOS.size() - 1 ======" + (postDTOS.size() - 1));
        System.out.println("pageCount =====  " + pageCount);

        if (firstIndex > (postDTOS.size() - 1)) {        //firstIndex 값이 게시글 리스트의 개수보다 크다면, 더 이상 보여줄 게시글이 없는 것.
            return null;

        } else if (lastIndex > postDTOS.size()) {        //lastIndex 값이 게시글 리스트의 개수보다 크다면, lastIndex 는 게시글 개수의 마지막 인덱스로 함.
            lastIndex = postDTOS.size();

        }

        List<PostDTO> paginatedPostList = postDTOS.subList(firstIndex, lastIndex);

        return paginatedPostList;
    }


}
