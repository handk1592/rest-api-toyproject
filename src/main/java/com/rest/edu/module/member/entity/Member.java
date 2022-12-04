package com.rest.edu.module.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Long age;

    @Column(name = "hp")
    private String hp;

    @Column(name = "email")
    private String email;

    @Builder
    public Member(String name, Long age, String hp, String email) {
        this.name = name;
        this.age = age;
        this.hp = hp;
        this.email = email;
    }

    public void updateMember(String name, Long age, String hp) {
        this.name = name;
        this.age = age;
        this.hp = hp;
    }
}
