package com.oasis.assessment.dtos;

import com.oasis.assessment.enums.StatusEnum;
import lombok.*;


@RequiredArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class UserRequestDto {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String roles;
    private final String username;

}
