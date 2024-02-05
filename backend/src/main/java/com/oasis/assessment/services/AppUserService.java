package com.oasis.assessment.services;

import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.dtos.UserRequestDto;

import java.util.List;

public interface AppUserService {

    AppUser createUser(UserRequestDto userRequestDto);
    Boolean deleteUser(Long userId);
    AppUser updateUser(Long userId, UserRequestDto userRequestDto);
    List<AppUser> fetchAllUsers();
    AppUser fetchSingleUser(Long userId);
    AppUser fetchSingleUser(String username);
}
