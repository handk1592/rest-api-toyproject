package com.rest.edu.module.member.controller;

import com.rest.edu.global.valid.ValidationSequence;
import com.rest.edu.module.member.dto.MemberVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @RequestMapping("/add")
    public String test(@Validated(ValidationSequence.class) @RequestBody MemberVO memberVO) {
        return "success";
    }
}
