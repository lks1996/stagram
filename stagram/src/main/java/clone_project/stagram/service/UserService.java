package clone_project.stagram.service;

import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.repository.JpaUserRepository;
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

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    private final JpaUserRepository jpaUserRepository;
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

    public void register(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("암호화 전 pw : " + userDTO.getPw());
        String securedPw = encoder.encode(userDTO.getPw());
        userDTO.setPw(securedPw);
        System.out.println("안호화 후 pw : " + userDTO.getPw());

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);

        jpaUserRepository.save(userEntity);
    }
}
