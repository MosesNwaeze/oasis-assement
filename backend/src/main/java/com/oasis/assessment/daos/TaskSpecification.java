package com.oasis.assessment.daos;

import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.models.Task;
import jakarta.persistence.criteria.Expression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class TaskSpecification {

      public static Specification<Task> hasTitle(String title){
          return (root,query,builder) -> builder.like(root.get("title"), "%" + title + "%");
      }

      public static Specification<Task> hasDescription(String description){
          return (root,query,builder) -> builder.like(root.get("description"), "%" + description + "%");
      }

      public static Specification<Task> hasPriority(PriorityEnum priority){
          return (root,query,builder) -> builder.equal(root.get("priority"), priority.name());
      }

      public static Specification<Task> hasCompletionStatus(CompletionStatusEnum completionStatus){
          return (root,query,builder) -> builder.equal(root.get("completionStatus"), completionStatus.name());
      }

      public static Specification<Task> hasDueDate(LocalDateTime dueDate){
          return (root,query,builder) -> builder.equal(root.get("dueDate"), dueDate);
      }

}
