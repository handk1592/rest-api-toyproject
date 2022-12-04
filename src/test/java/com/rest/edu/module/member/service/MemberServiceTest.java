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
    private MemberRepository mockMemberRepository;

    @InjectMocks
    private MemberService mockMemberService;

    @Test
    @DisplayName("사용자 조회 테스트")
    void get_member_test() {
        // given
        Long id = 1l;
        Optional<Member> member = Optional.of(Member.builder()
                                                .name("개발자_한")
                                                .age(32L)
                                                .hp("000-0000-0000")
                                                .email("test001@test.com")
                                                .build());
        
        doReturn(member).when(mockMemberRepository).findById(any(Long.class));

        // when
        Member memberById = mockMemberService.getMemberById(id);

        // then
        assertThat(memberById.getName()).isEqualTo(member.orElseGet(() -> Member.builder().build()).getName());
        assertThat(memberById.getAge()).isEqualTo(member.orElseGet(() -> Member.builder().build()).getAge());
        assertThat(memberById.getEmail()).isEqualTo(member.orElseGet(() -> Member.builder().build()).getEmail());

        verify(mockMemberRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void save_member_test() {
        // given
        RequestMemberDto requestMemberDto = RequestMemberDto.builder()
                .name("개발자_한")
                .age(32L)
                .hp("000-0000-0000")
                .email("test001@test.com")
                .build();

        Member member = requestMemberDto.toEntity();
        doReturn(member).when(mockMemberRepository).save(any(Member.class));

        // when
        Member savedMember = mockMemberService.join(requestMemberDto);

        // then
        assertThat(savedMember.getName()).isEqualTo(member.getName());

        verify(mockMemberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("사용자 등록시 중복 테스트")
    void save_member_duplicate_test() {
        // given
        RequestMemberDto requestMemberDto = RequestMemberDto.builder()
                .name("개발자_한")
                .age(32L)
                .hp("000-0000-0000")
                .email("test001@test.com")
                .build();

        Member member = requestMemberDto.toEntity();
        doReturn(member).when(mockMemberRepository).findByName(requestMemberDto.getName());

        // then
        assertThrows(BusinessException.class, () -> {
            mockMemberService.join(requestMemberDto);
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
                                                        .hp("000-0000-0000")
                                                        .email("test001@test.com")
                                                        .build());

        RequestMemberDto requestMemberDto = RequestMemberDto.builder()
                                                        .name("개발자_두덕")
                                                        .age(33L)
                                                        .hp("000-0000-0001")
                                                        .build();

        doReturn(member).when(mockMemberRepository).findById(any(Long.class));

        // when
        Member updatedMember = mockMemberService.updateMember(id, requestMemberDto);

        // then
        assertThat(updatedMember.getName()).isEqualTo(requestMemberDto.getName());
        assertThat(updatedMember.getHp()).isEqualTo(requestMemberDto.getHp());
        assertThat(updatedMember.getAge()).isEqualTo(requestMemberDto.getAge());

        verify(mockMemberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("존재하지 않은 회원 수정 테스트")
    void update_non_member_test() {
        // given
        Long id = 1L;

        doReturn(Optional.empty()).when(mockMemberRepository).findById(any(Long.class));

        // when & then
        assertThrows(BusinessException.class, () -> {
            mockMemberService.updateMember(
                    1L, RequestMemberDto.builder()
                                .name("개발자_두덕")
                                .age(33L)
                                .hp("000-0000-0001")
                                .build()
            );
        });

        verify(mockMemberRepository, times(1)).findById(id);
    }


    @Test
    @DisplayName("사용자 정보 삭제 테스트")
    void delete_member_test() {
        // given
        Long id = 1L;
        Optional<Member> member = Optional.of(Member.builder()
                .name("개발자_한")
                .age(32L)
                .hp("000-0000-0000")
                .email("test001@test.com")
                .build());

        doReturn(member).when(mockMemberRepository).findById(any(Long.class));

        // when
        Member deleteMember = mockMemberService.deleteMember(id);
        assertThat(deleteMember.getName()).isEqualTo(member.get().getName());


        verify(mockMemberRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("사용자 정보 삭제 테스트")
    void delete_none_member_test() {
        // given
        doReturn(Optional.empty()).when(mockMemberRepository).findById(any(Long.class));

        // when & then
        assertThrows(BusinessException.class, () -> {
            mockMemberService.deleteMember(1l);
        });

        verify(mockMemberRepository, times(1)).findById(any(Long.class));
    }
}