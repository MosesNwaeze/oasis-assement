package com.oasis.assessment.enums;

public enum CompletionStatusEnum {
    DONE("done"),
    IN_PROGRESS("in progress"),
    NOT_STARTED("not started"),
    DEFAULT("Not applicable");


    private final String status;

    CompletionStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
