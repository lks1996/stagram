package clone_project.stagram;

import clone_project.stagram.repository.JpaUserRepositoryCustom;
import clone_project.stagram.repository.JpaUserRepositoryCustomImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class config {
    private final EntityManager em;

    public config(EntityManager em) {
        this.em = em;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


//    @Bean
//    public UserService userService() {
//        return new UserService(userRepository());
//    }
//
    @Bean
    public JpaUserRepositoryCustom jpaUserRepositoryCustom() {
          return new JpaUserRepositoryCustomImpl(em);
    }
}
