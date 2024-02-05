package com.oasis.assessment.daos;

import com.oasis.assessment.models.AppUser;
import com.oasis.assessment.models.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {
    List<Task> findAll(Sort sort);
    List<Task> findAllByAppUserModel(AppUser appUser);

    Task findByIdAndAppUserModel(Long taskId,AppUser appUser);

    @Query(
            value = "select * from tasks where task_id =?1 AND user_id = ?2",
            nativeQuery = true
    )
    Optional<Task> findBytaskIdAndUserIdNative(Long taskId, Long userid);

}
