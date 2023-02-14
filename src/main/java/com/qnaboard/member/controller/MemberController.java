package com.qnaboard.member.controller;

import com.qnaboard.member.dto.MemberDto;
import com.qnaboard.member.entity.Member;
import com.qnaboard.member.mapper.MemberMapper;
import com.qnaboard.member.service.MemberService;
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
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.PostDto requestBody) {
        Member member = memberService.createMember(mapper.postDtoToMember(requestBody));
        log.info("# CREATE MEMBER");

        MemberDto.ResponseDto response = mapper.memberToResponseDto(member);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("member-id") Long memberId,
                                      @Valid @RequestBody MemberDto.PatchDto requestBody) {
        requestBody.setMemberId(memberId);
        Member member = memberService.updateMember(mapper.patchDtoToMember(requestBody));
        log.info("# UPDATE MEMBER");

        MemberDto.ResponseDto response = mapper.memberToResponseDto(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") Long memberId) {
        Member member = memberService.findMember(memberId);
        log.info("# FIND MEMBER");

        MemberDto.ResponseDto response = mapper.memberToResponseDto(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllMembers() {
        List<Member> members = memberService.findMembers();
        log.info("# FIND ALL MEMBERS");

        List<MemberDto.ResponseDto> response = mapper.membersToResponseDtos(members);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);
        log.info("# DELETE MEMBER");

        return ResponseEntity.noContent().build();
    }
}
