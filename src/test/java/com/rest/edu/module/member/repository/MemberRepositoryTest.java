package com.rest.edu.module.member.repository;

import com.rest.edu.module.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member();
        member.setUsername("handg");

        Member saveMember = memberRepository.save(member);

        Member findById = memberRepository.findById(saveMember.getId()).get();

        assertThat(findById.getId()).isEqualTo(member.getId());
    }

}