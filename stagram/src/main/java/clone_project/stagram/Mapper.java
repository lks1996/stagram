package clone_project.stagram;

import clone_project.stagram.DTO.PostDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.Entity.PostEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Entity.UserProfileImgEntity;
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

    public static List<UserDTO> ListMaptoDTO(List<UserEntity> userEntities) {
        List<UserDTO> userDto = userEntities
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDto;
    }


//    public UserEntity mapToEntity1(UserDTO userDTO) {
//        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
//    }
    public static UserEntity mapToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity(userDTO.getUser_no(), userDTO.getEmail(),
                userDTO.getId(), userDTO.getPassword(), userDTO.getName(), userDTO.getBio(),
                userDTO.getRegDate());
        return userEntity;
    }

    public UserDTO mapToDTO(UserEntity userEntity) {
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

    public static UserDTO OptionalMapToDTO(Optional<UserEntity> userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_no(userEntity.get().getUser_no());
        userDTO.setEmail(userEntity.get().getEmail());
        userDTO.setId(userEntity.get().getId());
        userDTO.setPassword(userEntity.get().getPassword());
        userDTO.setName(userEntity.get().getName());
        userDTO.setBio(userEntity.get().getBio());
        userDTO.setRegDate(userEntity.get().getRegDate());

        return userDTO;
    }


    public static UserProfileImgEntity mapToUserProfileImgEntity(UserProfileImgDTO userProfileImgDTO) {
        UserProfileImgEntity userProfileImgEntity = new UserProfileImgEntity(userProfileImgDTO.getUserImgNo(),
                userProfileImgDTO.getProfileImgOriginName(), userProfileImgDTO.getProfileImgName(),
                userProfileImgDTO.getProfileImgSize(), userProfileImgDTO.getRegDate(),
                userProfileImgDTO.getUserNo());
        return userProfileImgEntity;
    }

    public static UserProfileImgDTO mapToUserProfileImgDTO(Optional<UserProfileImgEntity> upiEntity) {
        UserProfileImgDTO upiDTO = new UserProfileImgDTO();
        upiDTO.setUserImgNo(upiEntity.get().getUserImgNo());
        upiDTO.setProfileImgOriginName(upiEntity.get().getProfileImgOriginName());
        upiDTO.setProfileImgName(upiEntity.get().getProfileImgName());
        upiDTO.setProfileImgSize(upiEntity.get().getProfileImgSize());
        upiDTO.setRegDate(upiEntity.get().getRegDate());
        upiDTO.setUserNo(upiEntity.get().getUser_no());

        return upiDTO;
    }

    public static PostEntity mapToPostEntity(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity(postDTO.getPost_no(), postDTO.getContents(), postDTO.getPost_regDate(),
                postDTO.getUser_no(), postDTO.getUser_id(), postDTO.getPostImgOriginName(), postDTO.getPostImgName(), postDTO.getPostImgSize());

        return postEntity;
    }
}
