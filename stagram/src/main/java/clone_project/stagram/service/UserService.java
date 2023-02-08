package clone_project.stagram.service;

import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.repository.JpaUserRepository;
import clone_project.stagram.repository.JpaUserRepositoryCustom;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    public UserService(JpaUserRepository jpaUserRepository, JpaUserRepositoryCustom jpaUserRepositoryCustom, ModelMapper modelMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserRepositoryCustom = jpaUserRepositoryCustom;
        this.modelMapper = modelMapper;
    }


    private final JpaUserRepository jpaUserRepository;

    private final JpaUserRepositoryCustom jpaUserRepositoryCustom;
    @Autowired
    private ModelMapper modelMapper;


    /** 전체 회원 조회 **/
    @Transactional
    public List<UserDTO> findAllMembers() {
        List<UserEntity> userEntity = jpaUserRepository.findAll();
        List<UserDTO> userDto = userEntity
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        //List<UserDTO> userDto = modelMapper(userEntity, List<UserDTO>.class);



        return userDto;
    }

    public UserDTO isDuplicateEmail(String email) {
        Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findByEmail(email);
        System.out.println("isDuplicateEmail에서 출력 ..... "+ userEntity.get().getUser_no() + "@@" + userEntity.get().getPassword() + "||" + userEntity.get().getName() + "||" + userEntity.get().getName());

        if (userEntity.isPresent()) {

            //UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
            UserDTO userDTO = new UserDTO();
            userDTO.setUser_no(userEntity.get().getUser_no());
            userDTO.setEmail(userEntity.get().getEmail());
            userDTO.setId(userEntity.get().getId());
            userDTO.setPassword(userEntity.get().getPassword());
            userDTO.setName(userEntity.get().getName());
            userDTO.setBio(userEntity.get().getBio());
            userDTO.setRegDate(userEntity.get().getRegDate());

            System.out.println("isDuplicateEmail에서 출력 ..... "+ userDTO.getUser_no() + "@@" + userDTO.getPassword() + "||" + userDTO.getName() + "||" + userDTO.getName());
            return userDTO;
        }
        return null;
    }

    public UserDTO isDuplicateId(String id) {
        Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findById(id);
        if (userEntity.isPresent()) {
            //UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

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
        return null;
    }

    public void register(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("암호화 전 pw : " + userDTO.getPassword());
        String securedPw = encoder.encode(userDTO.getPassword());
        userDTO.setPassword(securedPw);
        System.out.println("암호화 후 pw : " + userDTO.getPassword());

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        System.out.println("userEntity" + userEntity.getEmail());

        jpaUserRepository.save(userEntity);
    }


    public UserDTO login(String idOrEmail) {
        //전달 받은 데이터가 이메일이면,
        if (idOrEmail.contains("@") && idOrEmail.contains(".")) {
            Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findByEmail(idOrEmail);
            System.out.println("findByEmail 한 결과의 userEntity.password : " + userEntity.get().getPassword());


            if (userEntity.isPresent()) {
                //UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

                UserDTO userDTO = new UserDTO();
                userDTO.setUser_no(userEntity.get().getUser_no());
                userDTO.setEmail(userEntity.get().getEmail());
                userDTO.setId(userEntity.get().getId());
                userDTO.setPassword(userEntity.get().getPassword());
                userDTO.setName(userEntity.get().getName());
                userDTO.setBio(userEntity.get().getBio());
                userDTO.setRegDate(userEntity.get().getRegDate());

                System.out.println("findByEmail 한 결과가 있을 경우 UserDTO.getName : " + userDTO.getName());

                return userDTO;
            }
        } else {//전달 받은 데이터가 ID 라면,
            Optional<UserEntity> userEntity = jpaUserRepositoryCustom.findById(idOrEmail);
            if (userEntity.isPresent()) {
                //UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);

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
        }
        return null;
    }
}
