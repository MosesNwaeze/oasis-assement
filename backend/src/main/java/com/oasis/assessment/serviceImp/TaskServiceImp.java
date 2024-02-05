package com.oasis.assessment.serviceImp;

import com.oasis.assessment.daos.TaskCategoryRepository;
import com.oasis.assessment.daos.TaskRepository;
import com.oasis.assessment.daos.TaskSpecification;
import com.oasis.assessment.dtos.TaskCategoryRequestDto;
import com.oasis.assessment.dtos.TaskRequestDto;
import com.oasis.assessment.dtos.TaskResponseDto;
import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.enums.StatusEnum;
import com.oasis.assessment.errors.NotFoundException;
import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.models.Task;
import com.oasis.assessment.models.TaskCategory;
import com.oasis.assessment.services.AppUserService;
import com.oasis.assessment.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final AppUserService appUserService;

    @Override
    public Task createTask(TaskRequestDto taskRequestDto) {

        Task task = Task.builder()
                .completionStatus(taskRequestDto.getCompletionStatus())
                .description(taskRequestDto.getDescription())
                .dueDate(Timestamp.valueOf(taskRequestDto.getDueDate()))
                .status(StatusEnum.ACTIVE)
                .priority(taskRequestDto.getPriority())
                .title(taskRequestDto.getTitle())
                .appUserModel(taskRequestDto.getAppUser())
                .build();

        taskRepository.save(task);
        return task;
    }

    @Override
    public Boolean deleteTask(Long taskId, Long userId) {

        AppUser app = appUserService.fetchSingleUser(userId);

        if (app == null) {
            throw new NotFoundException("User with id " + userId + " cannot be found");
        }

        Task task = taskRepository.findByIdAndAppUserModel(taskId, app);
        if (task == null) {
            throw new NotFoundException("Task with id " + taskId + " cannot be found");
        }

        taskRepository.deleteById(task.getId());
        return true;
    }

    @Override
    public Task updateTask(Long taskId, Long userId, TaskRequestDto taskRequestDto) {

        AppUser user = appUserService.fetchSingleUser(userId);

        Task task = taskRepository.findByIdAndAppUserModel(taskId, user);

        if (task != null) {

            if (Objects.nonNull(taskRequestDto.getTitle())) {
                task.setTitle(taskRequestDto.getTitle());
            }
            if (Objects.nonNull(taskRequestDto.getPriority())) {
                task.setPriority(taskRequestDto.getPriority());
            }
            if (Objects.nonNull(taskRequestDto.getDueDate())) {
                task.setDueDate(Timestamp.valueOf(taskRequestDto.getDueDate()));
            }
            if (Objects.nonNull(taskRequestDto.getDescription())) {
                task.setDescription(taskRequestDto.getDescription());
            }
            if (Objects.nonNull(taskRequestDto.getCompletionStatus())) {
                task.setCompletionStatus(taskRequestDto.getCompletionStatus());
            }

            return taskRepository.save(task);
        }

        throw new NotFoundException("Resources requesting for cannot be found");
    }

    @Override
    public List<Task> fetchAllTask() {
        Sort sort = Sort.by("priority").descending();
        sort = sort.and(Sort.by("dueDate")).descending();
        return taskRepository.findAll(sort);
    }

    @Override
    public List<Task> fetchAllTaskByUser(Long userId) {
        AppUser appUser = appUserService.fetchSingleUser(userId);
        System.out.println(appUser + "========>");
        return taskRepository
                .findAllByAppUserModel(appUser);

    }

    @Override
    public Task fetchSingleTask(Long taskId) throws NotFoundException {
        return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task with id" + taskId + " cannot be found"));
    }

    @Override
    public Task fetchByTaskIdAndUserId(long taskId, Long userId) {

        return taskRepository.findBytaskIdAndUserIdNative(taskId, userId)
                .orElseThrow(() -> new NotFoundException("The resource with task id " + taskId + " and user id " + userId + " cannot be found"));


    }

    @Override
    public List<Task> searchTask(String title, String description, PriorityEnum priority, Timestamp dueDate, CompletionStatusEnum completionStatus) {
        Specification<Task> taskSpecification = TaskSpecification.searchTask(title, description, priority, dueDate, completionStatus);
        return taskRepository.findAll(taskSpecification);
    }

    @Override
    public TaskCategory addTaskToCategory(String cateName, TaskResponseDto taskResponseDto) {

        TaskCategory taskCategory = taskCategoryRepository
                .findTaskCategoryByCateName(cateName)
                .orElseThrow(() -> new NotFoundException("Task category not found"));

        List<Task> taskList = taskCategory.getTasks();

        Task task = Task
                .builder()
                .dueDate(Timestamp.valueOf(taskResponseDto.getDueDate()))
                .title(taskResponseDto.getTitle())
                .description(taskResponseDto.getDescription())
                .completionStatus(taskResponseDto.getCompletionStatus())
                .priority(taskResponseDto.getPriority())
                .status(taskResponseDto.getStatus())
                .taskCategory(taskCategory)
                .build();

        if (Objects.isNull(taskList)) {
            taskList = new ArrayList<>();
            taskList.add(task);
        } else {
            taskList.add(task);
        }

        taskCategory.setTasks(taskList);

        return taskCategoryRepository.save(taskCategory);
    }

    @Override
    public TaskCategory createTaskCategory(TaskCategoryRequestDto taskCategoryRequestDto) {
        return TaskCategory.builder()
                .cateName(taskCategoryRequestDto.getCateName())
                .build();
    }


}
