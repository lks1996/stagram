package clone_project.stagram.repository;

import clone_project.stagram.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

//    private final DataSource dataSource;
//
//    public JPAUserRepository(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    @Override
//    public List<UserVO> findAll() {}
}
