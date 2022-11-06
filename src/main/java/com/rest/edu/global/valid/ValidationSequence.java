package com.rest.edu.global.valid;

import javax.validation.GroupSequence;

// 유효성 검사 체크 순서
@GroupSequence({
        ValidationGroups.NotEmptyGroup.class,
        ValidationGroups.SizeCheckGroup.class,
        ValidationGroups.PatternCheckGroup.class,
        ValidationGroups.EmailCheckGroup.class})
public interface ValidationSequence {
}