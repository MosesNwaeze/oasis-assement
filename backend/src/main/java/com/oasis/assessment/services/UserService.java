package com.oasis.assessment.services;

import com.oasis.assessment.errors.NotFoundException;
import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.models.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws NotFoundException {

        //Logic to get the user form the Database

        AppUser appUser = appUserService.fetchSingleUser(userName);

        return new AppUserDetails(appUser);

    }
}
