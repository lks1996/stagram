package clone_project.stagram.service;

import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Entity.UserProfileImgEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.userProfile.JpaUserProfileImgRepository;
import clone_project.stagram.repository.userProfile.JpaUserProfileImgRepositoryCustom;
import clone_project.stagram.repository.user.JpaUserRepository;
import clone_project.stagram.repository.user.JpaUserRepositoryCustom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final JpaUserRepository jpaUserRepository;
    private final JpaUserRepositoryCustom jpaUserRepositoryCustom;
    private final JpaUserProfileImgRepository jpaUserProfileImgRepository;
    private final JpaUserProfileImgRepositoryCustom jpaUserProfileImgRepositoryCustom;
    public UserService(JpaUserRepository jpaUserRepository, JpaUserRepositoryCustom jpaUserRepositoryCustom,
                       JpaUserProfileImgRepository jpaUserProfileImgRepository, JpaUserProfileImgRepositoryCustom jpaUserProfileImgRepositoryCustom) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserRepositoryCustom = jpaUserRepositoryCustom;
        this.jpaUserProfileImgRepository = jpaUserProfileImgRepository;
        this.jpaUserProfileImgRepositoryCustom = jpaUserProfileImgRepositoryCustom;
    }


    /** 전체 회원 조회 **/
    @Transactional
    public List<UserDTO> findAllMembers() {
        List<UserEntity> userEntity = jpaUserRepository.findAll();
        List<UserDTO> userDto = Mapper.ListMaptoDTO(userEntity);

        return userDto;
    }

    public UserDTO isDuplicateEmail(String email) {
        Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findByEmail(email);

        if (userEntity.isPresent()) {
            UserDTO userDTO = Mapper.OptionalMapToDTO(userEntity);
            System.out.println("isDuplicateEmail에서 출력 ..... "+ userDTO.getUser_no() + "@@" + userDTO.getPassword() + "||" + userDTO.getName() + "||" + userDTO.getName());

            return userDTO;
        }
        return null;
    }

    public UserDTO isDuplicateId(String id) {
        Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findById(id);
        if (userEntity.isPresent()) {
            UserDTO userDTO = Mapper.OptionalMapToDTO(userEntity);

            return userDTO;
        }
        return null;
    }

    public void register(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("암호화 전 pw : " + userDTO.getPassword());
        String securedPw = encoder.encode(userDTO.getPassword());
        userDTO.setPassword(securedPw);
        System.out.println("암호화 후 pw : " + userDTO.getPassword());

        UserEntity userEntity = Mapper.mapToEntity(userDTO);
        System.out.println("userEntity" + userEntity.getEmail());

        jpaUserRepository.save(userEntity);
    }

    public void updateProfile(UserDTO updatedUserDTO) {
        UserEntity updatedUserEntity = Mapper.mapToEntity(updatedUserDTO);
        jpaUserRepository.save(updatedUserEntity);
    }


    public UserDTO login(String idOrEmail) {
        //전달 받은 데이터가 이메일이면,
        if (idOrEmail.contains("@") && idOrEmail.contains(".")) {
            Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findByEmail(idOrEmail);
            System.out.println("findByEmail 한 결과의 userEntity.password : " + userEntity.get().getPassword());

            if (userEntity.isPresent()) {
                //UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
                UserDTO userDTO = Mapper.OptionalMapToDTO(userEntity);
                System.out.println("findByEmail 한 결과가 있을 경우 UserDTO.getName : " + userDTO.getName());

                return userDTO;
            }
        } else {//전달 받은 데이터가 ID 라면,
            Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findById(idOrEmail);
            if (userEntity.isPresent()) {
                UserDTO userDTO = Mapper.OptionalMapToDTO(userEntity);

                return userDTO;
            }
        }

        return null;
    }

    public void saveProfileImg(UserProfileImgDTO userProfileImgDTO, UserDTO userDTO) {
        UserEntity userEntity = Mapper.mapToEntity(userDTO);
        UserProfileImgEntity userProfileImgEntity = Mapper.mapToUserProfileImgEntity(userProfileImgDTO, userEntity);

        jpaUserProfileImgRepository.save(userProfileImgEntity);
    }


/** 사용자가 프로필 사진을 등록했었는지 판별 **/
    public UserProfileImgDTO hasProfileImg(UserDTO userDTO) {
        Optional<UserProfileImgEntity> userProfileImgEntity = jpaUserProfileImgRepositoryCustom.findByUserNo(userDTO.getUser_no());

        if (userProfileImgEntity.isPresent()) {
            UserProfileImgDTO userProfileImgDTO = Mapper.mapToUserProfileImgDTO(userProfileImgEntity);
            return userProfileImgDTO;
        }

        return null;
    }

    @Transactional
    public void deleteProfileImg(Long user_no) {
        jpaUserProfileImgRepositoryCustom.deleteByUserNo(user_no);

        System.out.println("2. 사용자 프로필 사진 삭제 완료.");

    }

    public void deleteUser(Long user_no) {
        jpaUserRepository.deleteById(user_no);
        System.out.println("2. 사용자 회원 탈퇴 완료");
    }
}
