package com.qnaboard.question.service;

import com.qnaboard.exception.BusinessLogicException;
import com.qnaboard.exception.ExceptionCode;
import com.qnaboard.question.entity.Question;
import com.qnaboard.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        // todo: 질문을 등록한 회원이 존재하는지 확인 (member 구현 이후 적용)
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question, long memberId) {
        // todo: 질문을 등록한 회원인지 확인 (member 구현 이후 적용)
        // 존재하는 질문인지 확인
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());

        // 현재 질문 상태 확인 (로직 고민)
        if (findQuestion.getQuestionStatus() == Question.QuestionStatus.QUESTION_DELETE)
            throw new BusinessLogicException(ExceptionCode.ALREADY_DELETED);

        // 작성자가 수정하는 부분
        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.setContent(content));
        Optional.ofNullable(question.getDisclosureStatus())
                        .ifPresent(disclosureStatus ->
                                findQuestion.setDisclosureStatus(disclosureStatus));


        // todo: 관리자인지 일반 회원인지 이 메서드에서 확인해야 하는지 확인
        Optional.ofNullable(question.getQuestionStatus())
                .ifPresent(questionStatus -> findQuestion.setQuestionStatus(questionStatus));

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(long questionId) {
        // todo: 비밀글인 경우 작성자 본인과 관리자만 조회할 수 있다. (member 구현 후)
        Question question = findVerifiedQuestion(questionId);

        // 이미 삭제된 질문은 조회할 수 없다
        if (question.getQuestionStatus() == Question.QuestionStatus.QUESTION_DELETE)
            throw new BusinessLogicException(ExceptionCode.ALREADY_DELETED);

        return question;
    }

    public void deleteQuestion(long questionId, long memberId) {
        // 질문이 존재하는지 확인
        Question question = findVerifiedQuestion(questionId);

        // 이미 삭제된 질문은 삭제할 수 없다
        if (question.getQuestionStatus() == Question.QuestionStatus.QUESTION_DELETE)
            throw new BusinessLogicException(ExceptionCode.ALREADY_DELETED);

        // Todo: 질문 작성자 memberId와 삭제를 요청한 회원 memberId 비교 (member 연관 관계 매핑 후)

        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETE);
        questionRepository.save(question);
    }

    public List<Question> findQuestions() {
        List<Question> questions = questionRepository.findAll().stream()
                //.map(question -> findVerifiedQuestion(question.getQuestionId()))    // 필요한가?
                .filter(question ->
                        question.getQuestionStatus() != Question.QuestionStatus.QUESTION_DELETE)
                .collect(Collectors.toList());

        return questions;
    }

    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        return optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }


}
