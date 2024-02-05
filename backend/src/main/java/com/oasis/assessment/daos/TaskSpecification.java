package com.oasis.assessment.daos;

import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.models.Task;
import jakarta.persistence.criteria.Expression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.Objects;

public class TaskSpecification {

    public static Specification<Task> searchTask(
            String title,
            String description,
            PriorityEnum priority,
            Timestamp dueDate,
            CompletionStatusEnum completionStatus
    ){

        return (root,query,criteriaBuilder) ->{

            Predicate titlePredicate =  criteriaBuilder.like(root.get("title"), StringUtils.isBlank(title)
                    ? likePattern()    : title
            );

            Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), StringUtils.isBlank(description)
                    ? likePattern()    : description
            );

            Predicate priorityPredicate = criteriaBuilder.equal(root.get("priority"),priority);

            Predicate dueDatePredicate = criteriaBuilder.equal(root.get("dueDate"), dueDate);

            Predicate complettionStatusPredicate = criteriaBuilder.equal(root.get("completionStatus"),completionStatus);

            return  criteriaBuilder.or(titlePredicate,descriptionPredicate,priorityPredicate,dueDatePredicate,complettionStatusPredicate);

        };

    }


    private static String likePattern(){
        return "%" + "" + "%";
    }
}
