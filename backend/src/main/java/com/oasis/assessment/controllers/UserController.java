package com.oasis.assessment.controllers;

import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.dtos.AuthRequest;
import com.oasis.assessment.dtos.AuthResponse;
import com.oasis.assessment.dtos.UserRequestDto;
import com.oasis.assessment.dtos.UserResponseDto;
import com.oasis.assessment.services.AppUserService;
import com.oasis.assessment.services.UserService;
import com.oasis.assessment.utils.JWTUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final JWTUtility jwtUtility;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final PasswordEncoder  passwordEncoder;


    @PostMapping("authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) throws Exception {

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
           authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        }catch (BadCredentialsException ex){

            throw new Exception("Credential entered is not correct");

        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        String token = jwtUtility.generateToken(userDetails);
        AppUser appUser = appUserService.fetchSingleUser(authRequest.getUsername());

        return ResponseEntity.ok().body(new AuthResponse(token,appUser));
    }

    @PostMapping("register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        UserRequestDto dto = UserRequestDto
                .builder()
                .roles(userRequestDto.getRoles())
                .password(encodedPassword)
                .email(userRequestDto.getEmail())
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .username(userRequestDto.getUsername())
                .build();


        AppUser appUser = appUserService.createUser(dto);

        UserResponseDto userResponseDto = UserResponseDto

                .builder()
                .userId(appUser.getUserId())
                .email(appUser.getEmail())
                .roles(appUser.getRoles())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .status(appUser.getStatus())
                .username(appUser.getUsername())
                .build();

        UserDetails userDetails = userService.loadUserByUsername(userRequestDto.getUsername());

        String token = jwtUtility.generateToken(userDetails);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("token", token);
        responseMap.put("payload", userResponseDto);

        return ResponseEntity.ok().body(responseMap);

    }

    @DeleteMapping("delete/user/{userId}")
    public Boolean deleteUser(@PathVariable Long userId){

        return appUserService.deleteUser(userId);
    }


    @GetMapping("users/{userId}")
    public ResponseEntity<UserResponseDto> fetchUser(@PathVariable Long userId){

        AppUser user = appUserService.fetchSingleUser(userId);

        UserResponseDto userResponseDto = UserResponseDto
                .builder()
                .email(user.getEmail())
                .userId(user.getUserId())
                .status(user.getStatus())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .roles(user.getRoles())
                .username(user.getUsername())
                .build();

        return ResponseEntity.ok().body(userResponseDto);

    }

    @GetMapping("users")
    public ResponseEntity<List<UserResponseDto>> fetchAllUsers(){

        List<UserResponseDto> list = appUserService
                .fetchAllUsers()
                .stream()
                .map(user -> {
                    UserResponseDto userResponseDto = UserResponseDto
                            .builder()
                            .roles(user.getRoles())
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .userId(user.getUserId())
                            .lastName(user.getLastName())
                            .status(user.getStatus())
                            .username(user.getUsername())
                            .build();
                    return userResponseDto;
                })
                .toList();

        return ResponseEntity.ok().body(list);

    }

    @PutMapping("update/user/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,@RequestBody UserRequestDto userRequestDto){

        AppUser user = appUserService
                .updateUser(userId,userRequestDto);

        UserResponseDto userResponseDto = UserResponseDto
                .builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles())
                .status(user.getStatus())
                .build();

        return ResponseEntity.ok().body(userResponseDto);
    }

}
