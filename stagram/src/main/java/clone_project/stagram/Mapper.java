package clone_project.stagram;

import clone_project.stagram.DTO.*;
import clone_project.stagram.Entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Configuration
public class Mapper {
    private static ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

/** User **/
    public static List<UserDTO> ListMapToUserDTO(List<UserEntity> userEntities) {
        List<UserDTO> userDto = userEntities
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < userEntities.size(); i++) {
            List<FollowDTO> followerDTOS = userEntities.get(i).getFollowerEntityList()
                    .stream()
                    .map(follower->modelMapper.map(follower, FollowDTO.class))
                    .collect(Collectors.toList());
            userDto.set(i, userDto.get(i)).setFollowerDTOS(followerDTOS);

            List<FollowDTO> followingDTOS = userEntities.get(i).getFollowerEntityList()
                    .stream()
                    .map(following->modelMapper.map(following, FollowDTO.class))
                    .collect(Collectors.toList());
            userDto.set(i, userDto.get(i)).setFollowingDTOS(followingDTOS);
        }

        return userDto;
    }


//    public UserEntity mapToEntity1(UserDTO userDTO) {
//        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
//    }
    public static UserEntity mapToUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity(userDTO.getUser_no(), userDTO.getEmail(),
                userDTO.getId(), userDTO.getPassword(), userDTO.getName(), userDTO.getBio(),
                userDTO.getRegDate());
        return userEntity;
    }


    public UserDTO mapToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
    }
//    public static UserDTO mapToDTO(UserEntity userEntity) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUser_no(userEntity.getUser_no());
//        userDTO.setEmail(userEntity.getEmail());
//        userDTO.setId(userEntity.getId());
//        userDTO.setPassword(userEntity.getPassword());
//        userDTO.setName(userEntity.getName());
//        userDTO.setBio(userEntity.getBio());
//        userDTO.setRegDate(userEntity.getRegDate());
//
//        return userDTO;
//    }

    public static UserDTO OptionalMapToUserDTO(Optional<UserEntity> userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_no(userEntity.get().getUser_no());
        userDTO.setEmail(userEntity.get().getEmail());
        userDTO.setId(userEntity.get().getUser_id());
        userDTO.setPassword(userEntity.get().getPassword());
        userDTO.setName(userEntity.get().getName());
        userDTO.setBio(userEntity.get().getBio());
        userDTO.setRegDate(userEntity.get().getRegDate());

        return userDTO;
    }


/** UserProfileImg **/
    public static UserProfileImgEntity mapToUserProfileImgEntity(UserProfileImgDTO userProfileImgDTO, UserEntity userEntity) {
        UserProfileImgEntity userProfileImgEntity = new UserProfileImgEntity(userProfileImgDTO.getUserImgNo(),
                userProfileImgDTO.getProfileImgOriginName(), userProfileImgDTO.getProfileImgName(),
                userProfileImgDTO.getProfileImgSize(), userProfileImgDTO.getRegDate(),
                userEntity);
        return userProfileImgEntity;
    }

    public static UserProfileImgDTO mapToUserProfileImgDTO(Optional<UserProfileImgEntity> upiEntity) {
        UserProfileImgDTO upiDTO = new UserProfileImgDTO();
        upiDTO.setUserImgNo(upiEntity.get().getUserImgNo());
        upiDTO.setProfileImgOriginName(upiEntity.get().getProfileImgOriginName());
        upiDTO.setProfileImgName(upiEntity.get().getProfileImgName());
        upiDTO.setProfileImgSize(upiEntity.get().getProfileImgSize());
        upiDTO.setRegDate(upiEntity.get().getRegDate());
        upiDTO.setUserNo(upiEntity.get().getUserEntity().getUser_no());

        return upiDTO;
    }


/** Post **/
    public static PostEntity mapToPostEntity(PostDTO postDTO, UserEntity userEntity) {
        PostEntity postEntity = new PostEntity(postDTO.getPost_no(), postDTO.getContents(), postDTO.getPost_regDate(),
                postDTO.getUser_id(), postDTO.getPostImgOriginName(), postDTO.getPostImgName(), postDTO.getPostImgSize(),
                userEntity);

        return postEntity;
    }

    public static PostDTO mapToPostDTO(Optional<PostEntity> postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPost_no(postEntity.get().getPost_no());
        postDTO.setContents(postEntity.get().getContents());
        postDTO.setPost_regDate(postEntity.get().getPostRegDate());
        postDTO.setUser_no(postEntity.get().getUserEntity().getUser_no());
        postDTO.setUser_id(postEntity.get().getUser_id());
        postDTO.setPostImgOriginName(postEntity.get().getPostImgOriginName());
        postDTO.setPostImgName(postEntity.get().getPostImgName());
        postDTO.setPostImgSize(postEntity.get().getPostImgSize());

        postDTO.setCommentsDTOS(Mapper.ListMapToCommentsDTO(postEntity.get().getCommentsEntityList()));

        return postDTO;
    }

    public static List<PostDTO> ListMapToPostDTO(List<PostEntity> postEntities) {
        List<PostDTO> postDTO = postEntities
                .stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < postEntities.size(); i++) {
            List<CommentsDTO> commentsDTOS = postEntities.get(i).getCommentsEntityList()
                    .stream()
                    .map(comments -> modelMapper.map(comments, CommentsDTO.class))
                    .collect(Collectors.toList());

            postDTO.set(i, postDTO.get(i)).setCommentsDTOS(commentsDTOS);
        }

        for (int i = 0; i < postEntities.size(); i++) {
            List<LikeDTO> likeDTOS = postEntities.get(i).getLikeEntityList()
                    .stream()
                    .map(like -> modelMapper.map(like, LikeDTO.class))
                    .collect(Collectors.toList());
            postDTO.set(i, postDTO.get(i)).setLikeDTOS(likeDTOS);
        }


        return postDTO;
    }

/** Comments **/
    public static CommentsEntity mapToCommentsEntity(CommentsDTO commentsDTO, UserEntity userEntity, PostEntity postEntity) {
        CommentsEntity commentsEntity = new CommentsEntity(commentsDTO.getComments_no(), commentsDTO.getComments_regDate(),
                commentsDTO.getComments_contents(), commentsDTO.getUser_id(), userEntity, postEntity);

        return commentsEntity;
    }

    public static CommentsDTO mapToCommentsDTO(Optional<CommentsEntity> commentsEntity) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setComments_no(commentsEntity.get().getComments_no());
        commentsDTO.setComments_regDate(commentsEntity.get().getComments_regDate());
        commentsDTO.setComments_contents(commentsEntity.get().getComments_contents());
        commentsDTO.setUser_id(commentsEntity.get().getUser_id());
        commentsDTO.setUser_no(commentsEntity.get().getUserEntity().getUser_no());
        commentsDTO.setPost_no(commentsEntity.get().getPostEntity().getPost_no());

        return commentsDTO;
    }

    public static List<CommentsDTO> ListMapToCommentsDTO(List<CommentsEntity> commentsEntities) {
        List<CommentsDTO> commentsDTOS = commentsEntities
                .stream()
                .map(comments -> modelMapper.map(comments, CommentsDTO.class))
                .collect(Collectors.toList());
        return commentsDTOS;
    }

/** Follow **/
    public static FollowEntity mapToFollowEntity(FollowDTO followDTO, UserEntity followFrom, UserEntity followTo) {
        FollowEntity followEntity = new FollowEntity(followDTO.getFollow_no(), followFrom, followTo);
        return followEntity;
    }

    public static FollowDTO mapToFollowDTO(Optional<FollowEntity> followEntity) {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollow_no(followEntity.get().getFollow_no());
        followDTO.setFollow_from_user_no(followEntity.get().getFollow_from_user_no().getUser_no());
        followDTO.setFollow_to_user_no(followEntity.get().getFollow_to_user_no().getUser_no());

        return followDTO;
    }

    public static List<FollowDTO> ListMapToFollowDTO(List<FollowEntity> followEntityList) {
        List<FollowDTO> followDTOS = followEntityList
                .stream()
                .map(follow -> modelMapper.map(follow, FollowDTO.class))
                .collect(Collectors.toList());
        return followDTOS;
    }

/** Like **/
    public static LikeEntity mapToLikeEntity(LikeDTO likeDTO, UserEntity userEntity, PostEntity postEntity) {

        LikeEntity likeEntity = new LikeEntity(likeDTO.getLike_no(), likeDTO.getLike_regDate(),
                userEntity, postEntity);

        return likeEntity;
    }

    public static LikeDTO mapToLikeDTO(Optional<LikeEntity> likeEntity) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setLike_no(likeEntity.get().getLike_no());
        likeDTO.setLike_regDate(likeEntity.get().getLike_regDate());
        likeDTO.setUser_no(likeEntity.get().getUserEntity().getUser_no());
        likeDTO.setPost_no(likeEntity.get().getPostEntity().getPost_no());

        return likeDTO;
    }


}
