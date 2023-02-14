package com.qnaboard.question.mapper;

import com.qnaboard.question.dto.QuestionDto;
import com.qnaboard.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {
    @Mapping(source = "memberId", target = "member.memberId")
    Question postDtoToQuestion(QuestionDto.PostDto requestBody);
    @Mapping(source = "memberId", target = "member.memberId")
    Question patchDtoToQuestion(QuestionDto.PatchDto requestBody);
    @Mapping(source = "questionStatus.status", target = "questionStatus")
    @Mapping(source = "member.memberId", target = "memberId")
    QuestionDto.ResponseDto questionToResponseDto(Question question);   // mapping?
    List<QuestionDto.ResponseDto> questionsToResponseDtos(List<Question> questions);
}
