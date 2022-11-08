package com.rest.edu.module.member.repository;

import com.rest.edu.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
