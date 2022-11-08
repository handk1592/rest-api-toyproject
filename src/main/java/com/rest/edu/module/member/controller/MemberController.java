package com.rest.edu.module.member.controller;

import com.rest.edu.global.error.RestResponseExceptionHandler;
import com.rest.edu.global.valid.ValidationSequence;
import com.rest.edu.module.member.dto.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping("/add")
    public String test(@Validated(ValidationSequence.class) @RequestBody MemberVO memberVO) {
        logger.debug("test");
        return "success";
    }
}
