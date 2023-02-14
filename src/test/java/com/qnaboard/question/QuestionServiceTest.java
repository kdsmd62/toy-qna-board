package com.qnaboard.question;

import com.qnaboard.exception.BusinessLogicException;
import com.qnaboard.question.entity.Question;
import com.qnaboard.question.repository.QuestionRepository;
import com.qnaboard.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    public void exceptionTest() {
        // given
        Question question = new Question("질문 등록 테스트", "질문 등록 테스트 관련 문의드립니다.");
        question.setQuestionId(1L);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);

        // Stubbing by Mockito
        given(questionRepository.findById(question.getQuestionId())).willReturn(Optional.of(question));

        // when / then
        assertThrows(BusinessLogicException.class,
                () -> questionService.updateQuestion(question, 1L));
    }

    @Test
    public void questionStatusTest() {
        // given
        Question question = new Question();
        question.setQuestionId(1L);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_ANSWERED);
        question.setDisclosureStatus(Question.DisclosureStatus.DISCLOSURE_SECRET);

        // Stubbing by Mockito
        given(questionRepository.findById(question.getQuestionId())).willReturn(Optional.of(question));
        given(questionRepository.save(question)).willReturn(question);

        // when
        Question updQuestion = questionService.updateQuestion(question, 1L);

        // then
        assertTrue(question.getQuestionStatus() == updQuestion.getQuestionStatus());
        assertTrue(question.getDisclosureStatus() == updQuestion.getDisclosureStatus());
    }

    @Test
    public void deleteQuestionTest() {
        // given
        Question question = new Question();
        question.setQuestionId(1L);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);

        // Stubbing by Mockito
        given(questionRepository.findById(question.getQuestionId())).willReturn(Optional.of(question));

        // when / then
        assertThrows(BusinessLogicException.class,
                () -> questionService.deleteQuestion(question.getQuestionId(), 1L));
    }

    @Test
    public void findQuestionsTest() {
        // given
        Question question = new Question("모든 질문 조회 테스트", "등록된 모든 질문이 출력되어야 합니다.");
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);

        List<Question> questions = List.of(
                new Question("filter 테스트", "삭제된 질문을 제외한 리스트가 출력되어야 합니다."),
                question
        );

        // Stubbing by Mockito
        given(questionRepository.findAll()).willReturn(questions);
        List<Question> findQuestions = questionService.findQuestions();

        // when / then
        assertTrue(findQuestions.size() == 1);
    }

    @Test
    public void findQuestionTest() {
        // given
        Question question = new Question("질문 조회 테스트", "존재하지 않는 질문을 조회하면 예외 발생");
        question.setQuestionId(1L);

        // Stubbing by Mockito
        given(questionRepository.findById(question.getQuestionId())).willReturn(Optional.empty());

        // when / then
        assertThrows(BusinessLogicException.class,
                () -> questionService.findVerifiedQuestion(1L));
    }

    @Test
    @DisplayName("삭제된 질문 조회했을 때 BusinessLogicException 발생하는지 확인")
    public void findQuestionAboutStatusTest() {
        // given
        Question question = new Question();
        question.setQuestionId(1L);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);

        // Stubbing by Mockito
        given(questionRepository.findById(question.getQuestionId())).willReturn(Optional.of(question));

        // when / then
        assertThrows(BusinessLogicException.class,
                () -> questionService.findQuestion(question.getQuestionId()));
    }

}
