package com.rest.edu.module.member.service;

import com.rest.edu.global.error.BusinessException;
import com.rest.edu.global.error.ErrorStatus;
import com.rest.edu.module.member.dto.RequestMemberDto;
import com.rest.edu.module.member.entity.Member;
import com.rest.edu.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseGet(() -> Member.builder().build());
    }

    @Transactional
    public Member join(RequestMemberDto requestMemberDto) {
        Member member = memberRepository.findByName(requestMemberDto.getName());

        if(!ObjectUtils.isEmpty(member))
            throw new BusinessException(ErrorStatus.NON_EXISTS_MEMBER);

        return memberRepository.save(requestMemberDto.toEntity());
    }

    @Transactional
    public Member updateMember(Long id, RequestMemberDto requestMemberDto) {
        Member member =
                memberRepository.findById(id)
                                .orElseThrow(
                                        () -> new BusinessException("존재하지 않은 회원입니다", ErrorStatus.NON_EXISTS_MEMBER)
                                );

        member.updateMember(
                requestMemberDto.getName(),
                requestMemberDto.getAge(),
                requestMemberDto.getHp()
        );

        return member;
    }

    @Transactional
    public Member deleteMember(Long id) {
        Member member =
                memberRepository.findById(id)
                                .orElseThrow(
                                        () -> new BusinessException("존재하지 않은 회원입니다", ErrorStatus.NON_EXISTS_MEMBER)
                                );

        memberRepository.delete(member);
        return member;
    }

}
