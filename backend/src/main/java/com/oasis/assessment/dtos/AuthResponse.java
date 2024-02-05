package com.oasis.assessment.dtos;


import com.oasis.assessment.models.AppUser;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthResponse {
    private final String token;
    private final AppUser appUser;
}

