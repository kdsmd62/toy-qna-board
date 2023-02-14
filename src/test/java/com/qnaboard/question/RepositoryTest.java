package com.qnaboard.question;

import com.qnaboard.exception.BusinessLogicException;
import com.qnaboard.question.entity.Question;
import com.qnaboard.question.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void saveQuestionTest() {
        // given
        Question question = new Question();
        question.setTitle("질문 저장 테스트");
        question.setContent("질문 저장 테스트 관련 문의드립니다. 감사합니다.");
        // 비밀글 테스트. 공개글 테스트 시 밑에 한 줄 주석 처리
        question.setDisclosureStatus(Question.DisclosureStatus.DISCLOSURE_SECRET);

        // when
        Question savedQuestion = questionRepository.save(question);

        // then
        assertNotNull(savedQuestion);
        assertTrue(question.getTitle().equals(savedQuestion.getTitle()));
        assertTrue(question.getQuestionStatus().equals(savedQuestion.getQuestionStatus()));
        assertTrue(question.getDisclosureStatus().equals(savedQuestion.getDisclosureStatus()));
    }

}
