package com.qnaboard.question.dto;

import com.qnaboard.answer.entity.Answer;
import com.qnaboard.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class QuestionDto {
    @AllArgsConstructor
    @Getter
    public static class PostDto {
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        private String title;

        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        private String content;

        @Positive
        private long memberId;

    }

    @AllArgsConstructor
    @Getter
    public static class PatchDto {
        @NotSpace(message = "제목은 공백이 아니어야 합니다.")
        private String title;

        @NotSpace(message = "내용은 공백이 아니어야 합니다.")
        private String content;

        @NotBlank(message = "작성한 회원만 질문을 수정할 수 있습니다.")
        private long memberId;

    }

    @AllArgsConstructor
    @Getter
    public static class ResponseDto {
        private long questionId;
        private long memberId;
        private String questionStatus;
        private LocalDateTime createdAt;
        private String title;
        private String content;
        private Answer answer;
    }
}
