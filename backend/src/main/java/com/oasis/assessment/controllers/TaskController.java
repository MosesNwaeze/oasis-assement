package com.oasis.assessment.controllers;


import com.oasis.assessment.dtos.TaskCategoryRequestDto;
import com.oasis.assessment.dtos.TaskCategoryResponseDto;
import com.oasis.assessment.dtos.TaskRequestDto;
import com.oasis.assessment.dtos.TaskResponseDto;
import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.errors.NotFoundException;
import com.oasis.assessment.models.Task;
import com.oasis.assessment.models.TaskCategory;
import com.oasis.assessment.services.AppUserService;
import com.oasis.assessment.services.TaskService;
import com.oasis.assessment.services.UserService;
import com.oasis.assessment.utils.JWTUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TaskController {

    private final JWTUtility jwtUtility;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TaskService taskService;
    private final AppUserService appUserService;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("task")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto){

        Task task = taskService.createTask(taskRequestDto);

        TaskResponseDto taskResponseDto = TaskResponseDto
                .builder()
                .taskId(task.getId())
                .completionStatus(task.getCompletionStatus())
                .title(task.getTitle())
                .status(task.getStatus())
                .description(task.getDescription())
                .dueDate(task.getDueDate().toLocalDateTime())
                .priority(task.getPriority())
                .build();

        return ResponseEntity.ok().body(taskResponseDto);
    }

    @GetMapping("tasks")
    public ResponseEntity<List<TaskResponseDto>> fetchAllTask(){

        List<TaskResponseDto> list = taskService
                .fetchAllTask()
                .stream()
                .map(task -> {
                    TaskResponseDto taskResponseDto = TaskResponseDto
                            .builder()
                            .taskId(task.getId())
                            .completionStatus(task.getCompletionStatus())
                            .description(task.getDescription())
                            .dueDate(task.getDueDate().toLocalDateTime())
                            .priority(task.getPriority())
                            .status(task.getStatus())
                            .title(task.getTitle())
                            .build();
                    return taskResponseDto;
                })
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("task/{taskId}/{userId}")
    public ResponseEntity<TaskResponseDto> findByIdAndUser(@PathVariable Long taskId, @PathVariable Long userId){
        try {

            Task task = taskService.fetchByTaskIdAndUserId(taskId, userId);

            TaskResponseDto  taskResponseDto = TaskResponseDto
                    .builder()
                    .taskId(task.getId())
                    .priority(task.getPriority())
                    .description(task.getDescription())
                    .completionStatus(task.getCompletionStatus())
                    .status(task.getStatus())
                    .dueDate(task.getDueDate().toLocalDateTime())
                    .title(task.getTitle())
                    .appUser(task.getAppUserModel())
                    .build();
            logger.info("Task found successfully===>" + task);
            return ResponseEntity.ok().body(taskResponseDto);


        }catch (IllegalStateException e){
            logger.error(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }

    }

    @GetMapping("tasks-by-user/{userId}")
    public ResponseEntity<List<TaskResponseDto>> findTaskByUser(@PathVariable Long userId){
        List<Task> tasks = taskService.fetchAllTaskByUser(userId);

        List<TaskResponseDto> taskResponseDto = tasks
                .stream()
                .map(task ->{
                    return TaskResponseDto
                            .builder()
                            .dueDate(task.getDueDate().toLocalDateTime())
                            .taskId(task.getId())
                            .title(task.getTitle())
                            .status(task.getStatus())
                            .completionStatus(task.getCompletionStatus())
                            .description(task.getDescription())
                            .priority(task.getPriority())
                            .build();
                })
                .toList();

        return ResponseEntity.ok().body(taskResponseDto);

    }


    @GetMapping("tasks/{taskId}")
    public ResponseEntity<TaskResponseDto> fetchSingleTask(@PathVariable Long taskId){
        Task task = taskService
                .fetchSingleTask(taskId);

        TaskResponseDto taskResponseDto = TaskResponseDto
                .builder()
                .completionStatus(task.getCompletionStatus())
                .taskId(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate().toLocalDateTime())
                .description(task.getDescription())
                .build();
        return ResponseEntity.ok().body(taskResponseDto);
    }

    @DeleteMapping("delete/{taskId}/{userId}")
    public Boolean deleteTask(@PathVariable Long taskId,@PathVariable Long userId){
        return taskService.deleteTask(taskId,userId);
    }


    @PutMapping("update/{taskId}/{userId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long taskId,@PathVariable Long userId, @RequestBody TaskRequestDto taskRequestDto){
        Task task1 = taskService.updateTask(taskId, userId,taskRequestDto);

        if(task1 == null){
            throw new NotFoundException("Task not found");
        }
        TaskResponseDto taskResponseDto = TaskResponseDto
                .builder()
                .description(task1.getDescription())
                .dueDate(task1.getDueDate().toLocalDateTime())
                .completionStatus(task1.getCompletionStatus())
                .taskId(task1.getId())
                .priority(task1.getPriority())
                .status(task1.getStatus())
                .title(task1.getTitle())
                .build();

        return ResponseEntity.ok().body(taskResponseDto);
    }

    @GetMapping("search")
    public ResponseEntity<List<TaskResponseDto>> search(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "dueDate", required = false) String dueDate,
            @RequestParam(name = "completionStatus", required = false) String completionStatus
            ){

        PriorityEnum formatedPriority = StringUtils.isNotBlank(priority) ? PriorityEnum.valueOf(priority) : null;
        CompletionStatusEnum formatedCompletionStatus = StringUtils.isNotBlank(completionStatus)
                ? CompletionStatusEnum.valueOf(completionStatus) : null;
        Timestamp formatedDueDate = StringUtils.isNotBlank(dueDate)
                ? Timestamp.valueOf(LocalDateTime.parse(dueDate)) : null;
        String formattedDescription = StringUtils.isNotBlank(description)
                ? description : null;
        String formattedTitle = StringUtils.isNotBlank(title)
                ? title : null;

        List<Task> taskList = taskService
                .searchTask(
                        formattedTitle,
                        formattedDescription,
                        formatedPriority,
                        formatedDueDate,
                        formatedCompletionStatus
                );

        logger.info("Filtered task list ===> {}", taskList);

        if (Objects.nonNull(taskList)) {
            List<TaskResponseDto> list = taskList.stream().map(task -> {
                        return TaskResponseDto.builder().taskId(task.getId()).status(task.getStatus()).dueDate(task.getDueDate().toLocalDateTime()).priority(task.getPriority()).description(task.getDescription()).appUser(task.getAppUserModel()).completionStatus(task.getCompletionStatus()).title(task.getTitle()).taskCategory(task.getTaskCategory()).build();
                    }

            ).toList();

            return ResponseEntity.ok().body(list);
        }

        throw new NotFoundException("Resource cannot be found");

    }

    @PostMapping("task-category")
    public ResponseEntity<TaskCategoryResponseDto> createCategory(@RequestBody TaskCategoryRequestDto taskCategoryRequestDto){

        TaskCategory taskCategory = taskService.createTaskCategory(taskCategoryRequestDto);
        TaskCategoryResponseDto taskCategoryResponseDto = TaskCategoryResponseDto
                .builder()
                .categoryId(taskCategory.getId())
                .tasks(taskCategory.getTasks())
                .cateName(taskCategory.getCateName())
                .build();

        return ResponseEntity.ok().body(taskCategoryResponseDto);
    }

    @PostMapping("add-task-to-category/{categoryName}")
    public ResponseEntity<TaskCategoryResponseDto> addTaskToCategory(@PathVariable String categoryName, @RequestBody TaskResponseDto taskResponseDto){

        TaskCategory taskCategory = taskService.addTaskToCategory(categoryName, taskResponseDto);

        TaskCategoryResponseDto taskCategoryResponseDto  = TaskCategoryResponseDto
                .builder()
                .tasks(taskCategory.getTasks())
                .categoryId(taskCategory.getId())
                .cateName(taskCategory.getCateName())
                .build();

        return ResponseEntity.ok().body(taskCategoryResponseDto);
    }

}
