package com.oasis.assessment.daos;

import com.oasis.assessment.models.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory,Long> {
     Optional<TaskCategory> findTaskCategoryByCateName(String cateName);
}
