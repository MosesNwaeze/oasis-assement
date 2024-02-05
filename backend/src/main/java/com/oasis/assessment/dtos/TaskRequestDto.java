package com.oasis.assessment.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.enums.StatusEnum;
import com.oasis.assessment.models.AppUser;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class TaskRequestDto {
    private final String title;
    private final String description;
    private final PriorityEnum priority;
    private final LocalDateTime dueDate;
    private final CompletionStatusEnum completionStatus;
    private final AppUser  appUser;
}
