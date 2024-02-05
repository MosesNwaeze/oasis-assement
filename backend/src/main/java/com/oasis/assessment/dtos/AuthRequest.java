package com.oasis.assessment.dtos;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthRequest {

    private final String username;
    private final String password;
}
