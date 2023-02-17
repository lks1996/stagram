package clone_project.stagram.repository;

import clone_project.stagram.Entity.UserProfileImgEntity;

import java.util.Optional;

public interface JpaUserProfileImgRepositoryCustom {
    Optional<UserProfileImgEntity> findByUserNo(Long user_no);

}
