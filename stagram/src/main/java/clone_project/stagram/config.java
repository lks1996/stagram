package clone_project.stagram;

import clone_project.stagram.repository.comments.JpaCommentsRepositoryCustom;
import clone_project.stagram.repository.comments.JpaCommentsRepositoryCustomImpl;
import clone_project.stagram.repository.post.JpaPostRepositoryCustom;
import clone_project.stagram.repository.post.JpaPostRepositoryCustomImpl;
import clone_project.stagram.repository.userProfile.JpaUserProfileImgRepositoryCustom;
import clone_project.stagram.repository.userProfile.JpaUserProfileImgRepositoryCustomImpl;
import clone_project.stagram.repository.user.JpaUserRepositoryCustom;
import clone_project.stagram.repository.user.JpaUserRepositoryCustomImpl;
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


//    @Bean
//    public ModelMapper modelMapper(){
//        return new ModelMapper();
////        ModelMapper userMapper = new ModelMapper();
////
////        userMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
////        return userMapper;
//    }

    @Bean
    public WhatTime whatTime() {
        return new WhatTime();
    }

    @Bean
    public Mapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        return new Mapper(modelMapper);
    }


    @Bean
    public JpaUserRepositoryCustom jpaUserRepositoryCustom() {
        return new JpaUserRepositoryCustomImpl(em);
    }

    @Bean
    JpaUserProfileImgRepositoryCustom jpaUserProfileImgRepositoryCustom() {
        return new JpaUserProfileImgRepositoryCustomImpl(em);
    }

    @Bean
    JpaPostRepositoryCustom jpaPostRepositoryCustom(){
        return new JpaPostRepositoryCustomImpl(em);
    }

    @Bean
    JpaCommentsRepositoryCustom jpaCommentsRepositoryCustom(){
        return new JpaCommentsRepositoryCustomImpl(em);
    }

}
