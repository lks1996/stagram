package clone_project.stagram.service;

import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.DTO.UserProfileImgDTO;
<<<<<<< HEAD
import clone_project.stagram.Entity.FollowEntity;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)
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
<<<<<<< HEAD
import java.util.stream.Collectors;
=======
>>>>>>> bc74343 (주석 정리, 타임라인 로딩 효과 개선)

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
        List<UserDTO> userDto = Mapper.ListMapToUserDTO(userEntity);


//        List<UserEntity> filteredFollowList = userEntity.stream().
//                filter(follow -> userEntity.stream()
//                        .anyMatch(user ->
//                                user.getUser_no() == 1))
//                        .collect(Collectors.toList());
//
//        System.out.println("######" + filteredFollowList.size());

        return userDto;
    }

/** 이메일이 유효한지 판별 및 해당 이메일 유저 DTO 리턴 **/
    public UserDTO isDuplicateEmail(String email) {
        Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findByEmail(email);

        if (userEntity.isPresent()) {
            UserDTO userDTO = Mapper.OptionalMapToUserDTO(userEntity);
            System.out.println("isDuplicateEmail에서 출력 ..... "+ userDTO.getUser_no() + "@@" + userDTO.getPassword() + "||" + userDTO.getName() + "||" + userDTO.getName());

            return userDTO;
        }
        return null;
    }

/** 아이디가 유효한지 판별 및 해당 아이디 유저 DTO 리턴 **/
    public UserDTO isDuplicateId(String id) {
        Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findById(id);
        if (userEntity.isPresent()) {
            UserDTO userDTO = Mapper.OptionalMapToUserDTO(userEntity);

            return userDTO;
        }
        return null;
    }

/** 회원 등록(가입) **/
    public void register(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("암호화 전 pw : " + userDTO.getPassword());
        String securedPw = encoder.encode(userDTO.getPassword());
        userDTO.setPassword(securedPw);
        System.out.println("암호화 후 pw : " + userDTO.getPassword());

        UserEntity userEntity = Mapper.mapToUserEntity(userDTO);
        System.out.println("userEntity" + userEntity.getEmail());

        jpaUserRepository.save(userEntity);
    }

/** 회원 정보 수정 **/
    public void updateProfile(UserDTO updatedUserDTO) {
        UserEntity updatedUserEntity = Mapper.mapToUserEntity(updatedUserDTO);
        jpaUserRepository.save(updatedUserEntity);
    }

/** 로그인 **/
    public UserDTO login(String idOrEmail) {
        //전달 받은 데이터가 이메일이면,
        if (idOrEmail.contains("@") && idOrEmail.contains(".")) {
            Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findByEmail(idOrEmail);
            System.out.println("findByEmail 한 결과의 userEntity.password : " + userEntity.get().getPassword());

            if (userEntity.isPresent()) {
                //UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
                UserDTO userDTO = Mapper.OptionalMapToUserDTO(userEntity);
                System.out.println("findByEmail 한 결과가 있을 경우 UserDTO.getName : " + userDTO.getName());

                return userDTO;
            }
        } else {//전달 받은 데이터가 ID 라면,
            Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findById(idOrEmail);
            if (userEntity.isPresent()) {
                UserDTO userDTO = Mapper.OptionalMapToUserDTO(userEntity);

                return userDTO;
            }
        }

        return null;
    }

/** 회원 프로필 사진 업로드 **/
    public void saveProfileImg(UserProfileImgDTO userProfileImgDTO, UserDTO userDTO) {
        UserEntity userEntity = Mapper.mapToUserEntity(userDTO);
        UserProfileImgEntity userProfileImgEntity = Mapper.mapToUserProfileImgEntity(userProfileImgDTO, userEntity);

        jpaUserProfileImgRepository.save(userProfileImgEntity);
    }


/** 사용자가 프로필 사진을 등록했었는지 판별 및 프로필 사진 DTO 리턴 **/
    public UserProfileImgDTO hasProfileImg(UserDTO userDTO) {
        Optional<UserProfileImgEntity> userProfileImgEntity = jpaUserProfileImgRepositoryCustom.findByUserNo(userDTO.getUser_no());

        if (userProfileImgEntity.isPresent()) {
            UserProfileImgDTO userProfileImgDTO = Mapper.mapToUserProfileImgDTO(userProfileImgEntity);
            return userProfileImgDTO;
        }

        return null;
    }

/** 프로필 사진 삭제 **/
    @Transactional
    public void deleteProfileImg(Long user_no) {
        jpaUserProfileImgRepositoryCustom.deleteByUserNo(user_no);

        System.out.println("2. 사용자 프로필 사진 삭제 완료.");

    }

/** 회원 탈퇴 **/
    public void deleteUser(Long user_no) {
        jpaUserRepository.deleteById(user_no);
        System.out.println("2. 사용자 회원 탈퇴 완료");
    }

/** 회원 번호에 해당하는 회원이 있는지 확인(회원 탈퇴 후 검증용) **/
    public UserDTO findUserByUserNo(Long user_no) {
        Optional<UserEntity> userEntity = jpaUserRepository.findById(user_no);


        if (userEntity.isPresent()) {
            UserDTO userDTO = Mapper.OptionalMapToUserDTO(userEntity);
            return userDTO;
        }
        return null;
    }
}
