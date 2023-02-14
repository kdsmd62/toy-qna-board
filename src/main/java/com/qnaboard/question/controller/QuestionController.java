package com.qnaboard.question.controller;

import com.qnaboard.question.dto.QuestionDto;
import com.qnaboard.question.entity.Question;
import com.qnaboard.question.mapper.QuestionMapper;
import com.qnaboard.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/question")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper mapper;

    public QuestionController(QuestionService questionService, QuestionMapper mapper) {
        this.questionService = questionService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.PostDto requestBody) {
        Question question = questionService.createQuestion(
                mapper.postDtoToQuestion(requestBody));

        QuestionDto.ResponseDto responseBody = mapper.questionToResponseDto(question);

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@Positive @PathVariable("question-id") Long questionId) {
        Question question = questionService.findQuestion(questionId);
        QuestionDto.ResponseDto responseBody = mapper.questionToResponseDto(question);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions() {
        // Todo: Pagination 처리
        List<Question> questions = questionService.findQuestions();
        List<QuestionDto.ResponseDto> responseBody = mapper.questionsToResponseDtos(questions);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@RequestHeader("member-id") Long memberId,
                                         @Positive @PathVariable("question-id") Long questionId) {
        questionService.deleteQuestion(questionId, memberId);

        return ResponseEntity.noContent().build();
    }
}
