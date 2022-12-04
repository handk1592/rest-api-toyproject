package com.rest.edu.module.member.service;

import com.rest.edu.global.error.BusinessException;
import com.rest.edu.module.member.dto.RequestMemberDto;
import com.rest.edu.module.member.entity.Member;
import com.rest.edu.module.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("사용자 등록 테스트")
    void save_member_test() {
        // given
        RequestMemberDto requestMemberDto = RequestMemberDto.builder()
                .name("개발자_한")
                .age(32L)
                .hp("010-1111-2222")
                .email("test001@test.com")
                .build();

        Member member = requestMemberDto.toEntity();
        doReturn(member).when(memberRepository).save(any(Member.class));

        // when
        Member savedMember = memberService.join(requestMemberDto);

        // then
        assertThat(savedMember.getName()).isEqualTo(member.getName());

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("사용자 등록시 중복 테스트")
    void save_member_duplicate_test() {
        // given
        RequestMemberDto requestMemberDto = RequestMemberDto.builder()
                .name("개발자_한")
                .age(32L)
                .hp("010-1111-2222")
                .email("test001@test.com")
                .build();

        Member member = requestMemberDto.toEntity();
        doReturn(member).when(memberRepository).findByName(requestMemberDto.getName());

        // then
        assertThrows(BusinessException.class, () -> {
            memberService.join(requestMemberDto);
        });
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    void update_member_test() {
        // given
        Long id = 1L;

        Optional<Member> member = Optional.of(Member.builder()
                                                        .name("개발자_한")
                                                        .age(32L)
                                                        .hp("010-1111-2222")
                                                        .email("test001@test.com")
                                                        .build());

        RequestMemberDto requestMemberDto = RequestMemberDto.builder()
                                                        .name("개발자_두덕")
                                                        .age(33L)
                                                        .hp("010-2222-2222")
                                                        .build();

        doReturn(member).when(memberRepository).findById(any(Long.class));

        // when
        Member updatedMember = memberService.updateMember(id, requestMemberDto);

        // then
        assertThat(updatedMember.getName()).isEqualTo(requestMemberDto.getName());
        assertThat(updatedMember.getHp()).isEqualTo(requestMemberDto.getHp());
        assertThat(updatedMember.getAge()).isEqualTo(requestMemberDto.getAge());

        verify(memberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("존재하지 않은 회원 수정 테스트")
    void update_non_member_test() {
        // given
        Long id = 1L;

        doReturn(Optional.empty()).when(memberRepository).findById(any(Long.class));

        // when & then
        assertThrows(BusinessException.class, () -> {
            memberService.updateMember(
                    1L, RequestMemberDto.builder()
                                .name("개발자_두덕")
                                .age(33L)
                                .hp("010-2222-2222")
                                .build()
            );
        });

        verify(memberRepository, times(1)).findById(id);
    }


    @Test
    @DisplayName("사용자 정보 삭제 테스트")
    void delete_member_test() {
        // given
        Long id = 1L;
        Optional<Member> member = Optional.of(Member.builder()
                .name("개발자_한")
                .age(32L)
                .hp("010-1111-2222")
                .email("test001@test.com")
                .build());

        doReturn(member).when(memberRepository).findById(any(Long.class));

        // when
        Member deleteMember = memberService.deleteMember(id);
        assertThat(deleteMember.getName()).isEqualTo(member.get().getName());


        verify(memberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("사용자 정보 삭제 테스트")
    void delete_none_member_test() {
        // given
        doReturn(Optional.empty()).when(memberRepository).findById(any(Long.class));

        // when & then
        assertThrows(BusinessException.class, () -> {
            memberService.deleteMember(1l);
        });

        verify(memberRepository, times(1)).findById(any(Long.class));
    }
}