package com.qnaboard.exception;

import lombok.Getter;

public enum ExceptionCode {
    QUESTION_NOT_FOUND(404, "Question Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    ALREADY_MEMBER_EXIST(400, "Already Member Exist"),
    ALREADY_DELETED(400, "Already Deleted"),
    INVALID_QUESTION_STATUS(400, "Invalid Question Status"),
    INVALID_MEMBER_ID(400, "Invalid Member Id");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
