package com.oasis.assessment.dtos;

import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.enums.StatusEnum;
import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.models.TaskCategory;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class TaskResponseDto {
    private final String title;
    private final String description;
    private final PriorityEnum priority;
    private final LocalDateTime dueDate;
    private final CompletionStatusEnum completionStatus;
    private final StatusEnum status;
    private final Long taskId;
    private final AppUser appUser;
    private final TaskCategory taskCategory;
}
