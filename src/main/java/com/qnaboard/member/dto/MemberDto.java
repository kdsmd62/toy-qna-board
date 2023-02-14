package com.qnaboard.member.dto;

import com.qnaboard.member.entity.Member;
import com.qnaboard.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {
    @Getter
    public static class PostDto {
        @Email
        @NotBlank
        private String email;

        @NotBlank(message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;
    }

    @AllArgsConstructor
    @Getter
    public static class PatchDto {
        private long memberId;

        private String email;

        @NotSpace(message = "이름은 공백이 아니어야 합니다.")
        private String name;

        @NotSpace(message = "휴대폰 번호는 공백이 아니어야 합니다.")
        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        private Member.MemberStatus memberStatus;

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class ResponseDto {
        private long memberId;
        private String email;
        private String name;
        private String phone;
        private String memberStatus;
    }
}
