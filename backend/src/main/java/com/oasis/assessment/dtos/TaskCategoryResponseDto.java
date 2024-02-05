package com.oasis.assessment.dtos;

import com.oasis.assessment.models.Task;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class TaskCategoryResponseDto {
    private final String cateName;
    private final List<Task> tasks;
    private final Long categoryId;
}
