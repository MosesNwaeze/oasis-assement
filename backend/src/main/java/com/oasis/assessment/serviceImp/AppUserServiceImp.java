package com.oasis.assessment.serviceImp;

import com.oasis.assessment.daos.UserRepository;
import com.oasis.assessment.dtos.UserRequestDto;
import com.oasis.assessment.enums.StatusEnum;
import com.oasis.assessment.errors.NotFoundException;
import com.oasis.assessment.errors.ResourceAlreadyExistException;
import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AppUserServiceImp implements AppUserService {

    private final UserRepository userRepository;

    @Override
    public AppUser createUser(UserRequestDto userRequestDto) {

       Optional<AppUser> user = this.userRepository.findByUsername(userRequestDto.getUsername());

       if(user.isPresent()){
           throw new ResourceAlreadyExistException("The user with the username exist");
       }


        AppUser appUser = AppUser.builder()
                .email(userRequestDto.getEmail())
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .status(StatusEnum.ACTIVE)
                .password(userRequestDto.getPassword())
                .roles(userRequestDto.getRoles())
                .username(userRequestDto.getUsername())
                .build();

        return userRepository.save(appUser);

    }

    @Override
    public Boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }

    @Override
    public AppUser updateUser(Long userId, UserRequestDto userRequestDto) {
        AppUser appUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id" + userId + " cannot be found"));

        if (Objects.nonNull(userRequestDto.getEmail())) {
            appUser.setEmail(userRequestDto.getEmail());
        }
        if (Objects.nonNull(userRequestDto.getLastName())) {
            appUser.setLastName(appUser.getLastName());
        }
        if (Objects.nonNull(userRequestDto.getPassword())) {
            appUser.setPassword(userRequestDto.getPassword());
        }
        if (Objects.nonNull(userRequestDto.getFirstName())) {
            appUser.setFirstName(userRequestDto.getFirstName());
        }
        if (Objects.nonNull(userRequestDto.getRoles())) {
            appUser.setRoles(userRequestDto.getRoles());
        }
        return this.userRepository.save(appUser);
    }

    @Override
    public List<AppUser> fetchAllUsers() {
        Sort sort = Sort.by("firstName").ascending();
        sort = sort.and(Sort.by("lastName")).ascending();

        return userRepository.findAll(sort);
    }

    @Override
    public AppUser fetchSingleUser(Long userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id" + userId + " cannot be found"));
    }

    @Override
    public AppUser fetchSingleUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("username provided cannot be found"));
    }
}
