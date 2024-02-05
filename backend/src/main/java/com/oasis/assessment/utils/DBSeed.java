package com.oasis.assessment.utils;

import com.oasis.assessment.daos.UserRepository;
import com.oasis.assessment.enums.StatusEnum;
import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.services.AppUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DBSeed {

    private final AppUserService appUserService;
    private final UserRepository userRepository;
    private final PasswordEncoder   passwordEncoder;

    @PostConstruct
    public void seedDB(){

        AppUser  appUser = new AppUser();
        appUser.setEmail("mollasmart@gmail.com");
        appUser.setStatus(StatusEnum.valueOf(StatusEnum.ACTIVE.name()));
        appUser.setPassword(passwordEncoder.encode("12345"));
        appUser.setRoles("admin super-admin");
        appUser.setLastName("Moses");
        appUser.setFirstName("Nwaeze");


//        AppUser  appUser2 = new AppUser();
//        appUser.setEmail("mollasmart2@gmail.com");
////        appUser.setStatus(StatusEnum.ACTIVE);
//        appUser.setPassword(passwordEncoder.encode("12345"));
//        appUser.setRoles("user");
//        appUser.setLastName("Mike");
//        appUser.setFirstName("Jude");


        userRepository.save(appUser);


    }
}
