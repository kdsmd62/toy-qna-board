package com.qnaboard.question.entity;

import com.qnaboard.answer.entity.Answer;
import com.qnaboard.audit.Auditable;
import com.qnaboard.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "QUESTIONS")
public class Question extends Auditable {
    // todo: 생성일, 수정일, 생성자 추가 (audible)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToOne(mappedBy = "question")
    private Answer answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_REGISTRATION;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DisclosureStatus disclosureStatus = DisclosureStatus.DISCLOSURE_PUBLIC;

    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getQuestions().contains(this)) {
            this.member.getQuestions().add(this);
        }
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
        if (answer.getQuestion() != this) {
            answer.setQuestion(this);
        }
    }

    public Question(String title, String content) {      // for Test
        this.title = title;
        this.content = content;
    }

    public enum QuestionStatus {
        QUESTION_REGISTRATION("질문 등록"),
        QUESTION_ANSWERED("답변 완료"),
        QUESTION_DELETE("질문 삭제");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }

    public enum DisclosureStatus {
        DISCLOSURE_PUBLIC("공개글"),
        DISCLOSURE_SECRET("비밀글");

        @Getter
        private String status;

        DisclosureStatus(String status) {
            this.status = status;
        }
    }



}
