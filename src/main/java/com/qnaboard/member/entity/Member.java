package com.qnaboard.member.entity;

import com.qnaboard.audit.Auditable;
import com.qnaboard.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "members")
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberId;
    @Column(nullable = false, unique = true, updatable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, unique = true, length = 13)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();

    public void setQuestion(Question question) {
        questions.add(question);
        if (question.getMember() != this) {
            question.setMember(this);
        }
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

}
