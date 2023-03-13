package clone_project.stagram.service;

import clone_project.stagram.DTO.FollowDTO;
import clone_project.stagram.DTO.UserDTO;
import clone_project.stagram.Entity.FollowEntity;
import clone_project.stagram.Entity.UserEntity;
import clone_project.stagram.Mapper;
import clone_project.stagram.repository.follow.JpaFollowRepository;
import clone_project.stagram.repository.follow.JpaFollowRepositoryCustom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    private final JpaFollowRepository jpaFollowRepository;
    private final JpaFollowRepositoryCustom jpaFollowRepositoryCustom;

    public FollowService(JpaFollowRepository jpaFollowRepository, JpaFollowRepositoryCustom jpaFollowRepositoryCustom) {
        this.jpaFollowRepository = jpaFollowRepository;
        this.jpaFollowRepositoryCustom = jpaFollowRepositoryCustom;
    }

/** 팔로우 저장 **/
    public void follow(FollowDTO followDTO, UserDTO followFrom, UserDTO followTo) {

        UserEntity followFromEntity = Mapper.mapToUserEntity(followFrom);
        UserEntity followToEntity = Mapper.mapToUserEntity(followTo);

        FollowEntity followEntity = Mapper.mapToFollowEntity(followDTO, followFromEntity, followToEntity);

        jpaFollowRepository.save(followEntity);
    }

/** 팔로우 취소 **/
    public void unfollow(FollowDTO unfollowDTO, UserDTO unfollowFrom, UserDTO unfollowTo) {
        UserEntity unfollowFromEntity = Mapper.mapToUserEntity(unfollowFrom);
        UserEntity unfollowToEntity = Mapper.mapToUserEntity(unfollowTo);

        FollowEntity unfollowEntity = Mapper.mapToFollowEntity(unfollowDTO, unfollowFromEntity, unfollowToEntity);

        jpaFollowRepository.delete(unfollowEntity);
    }

/** 팔로우 관계인지 확인 후 팔로우 관계면, FollowDTO 반환 **/
    public FollowDTO isFollow(Long loginUserNo, Long otherUserNo) {
        Optional<FollowEntity> followEntity = jpaFollowRepositoryCustom.findByLoginUserNoAndOtherUserNo(loginUserNo, otherUserNo);

        if (followEntity.isPresent()) {
            FollowDTO followDTO = Mapper.mapToFollowDTO(followEntity);
            return followDTO;
        }

        return null;
    }


    public List<FollowDTO> followingCount(Long user_no) {
        List<FollowEntity> followEntityList = jpaFollowRepositoryCustom.findFollowingUsers(user_no);
        List<FollowDTO> followDTOS = Mapper.ListMapToFollowDTO(followEntityList);

        return followDTOS;
    }
}
