package com.qnaboard.member.mapper;

import com.qnaboard.member.dto.MemberDto;
import com.qnaboard.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member postDtoToMember(MemberDto.PostDto requestBody);
    Member patchDtoToMember(MemberDto.PatchDto requestBody);
    @Mapping(source = "memberStatus.status", target = "memberStatus")
    MemberDto.ResponseDto memberToResponseDto(Member member);
    List<MemberDto.ResponseDto> membersToResponseDtos(List<Member> members);
}
