package clone_project.stagram.service;

import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.repository.JpaUserRepository;
import clone_project.stagram.repository.JpaUserRepositoryCustom;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public UserService(JpaUserRepository jpaUserRepository, JpaUserRepositoryCustom jpaUserRepositoryCustom) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserRepositoryCustom = jpaUserRepositoryCustom;
    }


    private final JpaUserRepository jpaUserRepository;

    private final JpaUserRepositoryCustom jpaUserRepositoryCustom;
    @Autowired
    private ModelMapper modelMapper;


    /** 전체 회원 조회 **/
    @Transactional
    public List<UserDTO> findMembers() {
        List<UserEntity> userEntity = jpaUserRepository.findAll();
        List<UserDTO> userDto = userEntity
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        //List<UserDTO> userDto = modelMapper(userEntity, List<UserDTO>.class);



        return userDto;
    }

    public UserDTO findByEmail(String email) {
        UserEntity userEntity = jpaUserRepositoryCustom.findByEmail(email);
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
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


}
