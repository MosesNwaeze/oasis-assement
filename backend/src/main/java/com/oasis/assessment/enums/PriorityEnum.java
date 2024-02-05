package com.oasis.assessment.enums;

import org.springframework.data.domain.Sort;

public enum PriorityEnum {
    HIGH(2),
    MEDIUM(1),
    LOW(0);

    private final int priority;

    PriorityEnum(int priority){
        this.priority = priority;
    }

    public int getPriority(){
        return this.priority;

    }
}
