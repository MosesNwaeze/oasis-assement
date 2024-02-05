package com.oasis.assessment.services;

import com.oasis.assessment.dtos.TaskResponseDto;
import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.errors.NotFoundException;
import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.models.Task;
import com.oasis.assessment.models.TaskCategory;
import com.oasis.assessment.dtos.TaskCategoryRequestDto;
import com.oasis.assessment.dtos.TaskRequestDto;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {

    Task createTask(TaskRequestDto taskRequestDto);
    Boolean deleteTask(Long taskId, Long userId);

    Task updateTask(Long taskId, Long userId, TaskRequestDto taskRequestDto);

    List<Task> fetchAllTask();

    List<Task> fetchAllTaskByUser(Long userId);

    Task fetchSingleTask(Long taskId) throws NotFoundException;

    Task fetchByTaskIdAndUserId(long taskId, Long userId);

    List<Task> searchTask(
            String title,
            String description,
            PriorityEnum priority,
            Timestamp dueDate,
            CompletionStatusEnum completionStatus
    );

    TaskCategory addTaskToCategory(String cateName, TaskResponseDto taskResponseDto);
    TaskCategory createTaskCategory(TaskCategoryRequestDto taskCategoryRequestDto);
}
