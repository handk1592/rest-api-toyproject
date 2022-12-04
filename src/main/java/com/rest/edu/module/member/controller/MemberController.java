package com.rest.edu.module.member.controller;

import com.rest.edu.global.valid.ValidationSequence;
import com.rest.edu.module.member.dto.RequestMemberDto;
import com.rest.edu.module.member.dto.ResponseMemberDto;
import com.rest.edu.module.member.entity.Member;
import com.rest.edu.module.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    @GetMapping("/users/{id}")
    public Member getMember(@PathVariable("id") Long id) {
        Member member = memberService.findMemberById(id);
        return member;

    }

    @PostMapping("/users")
    public ResponseMemberDto addMember(@Validated(ValidationSequence.class) @RequestBody RequestMemberDto requestMemberDto) {
        Member savedMember = memberService.join(requestMemberDto);


        return ResponseMemberDto.builder()
                .name(savedMember.getName())
                .build();

    }

    @PutMapping("/users/{id}")
    public ResponseMemberDto updateMember(
            @PathVariable("id") Long id,
            @Validated(ValidationSequence.class) @RequestBody RequestMemberDto requestMemberDto
    ) {
        Member updatedMember = memberService.updateMember(id, requestMemberDto);

        return ResponseMemberDto.builder()
                .name(updatedMember.getName())
                .build();

    }

    @DeleteMapping("/users/{id}")
    public ResponseMemberDto deleteMember(@PathVariable("id") Long id) {
        Member deletedMember = memberService.deleteMember(id);


        return ResponseMemberDto.builder()
                .name(deletedMember.getName())
                .build();

    }

}
