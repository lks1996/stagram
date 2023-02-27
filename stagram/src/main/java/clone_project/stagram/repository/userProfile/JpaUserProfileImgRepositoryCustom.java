package clone_project.stagram.repository.userProfile;

import clone_project.stagram.Entity.UserProfileImgEntity;

import java.util.Optional;

public interface JpaUserProfileImgRepositoryCustom {
    Optional<UserProfileImgEntity> findByUserNo(Long user_no);

    void deleteByUserNo(Long user_no);
}
