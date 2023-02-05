package clone_project.stagram;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class config {
    private final DataSource dataSource;

    public config(DataSource dataSource) {
        this.dataSource = dataSource;
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
//    @Bean
//    public UserRepository userRepository() {
//        return new JPAUserRepository(dataSource);
////          return new JpaMemberRepository(em);
//    }
}
