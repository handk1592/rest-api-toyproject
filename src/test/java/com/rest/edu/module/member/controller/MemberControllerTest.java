package com.rest.edu.module.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.edu.module.member.dto.RequestMemberDto;
import com.rest.edu.module.member.entity.Member;
import com.rest.edu.module.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
@WebMvcTest
class MemberControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("사용자 정보 조회 테스트")
    void user_get_test() throws Exception {
        Long id = 1L;
        // given
        Member member = Member.builder()
                            .name("개발자_한")
                            .age(32L)
                            .build();

        doReturn(member).when(memberService).findMemberById(eq(id));

        // when & then
        mockMvc.perform
                (
                        get("/member/users/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer test123")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("name").value("개발자_한"))
                .andDo(print());

        verify(memberService, atLeastOnce()).findMemberById(any(Long.class));
        verify(memberService, times(1)).findMemberById(any(Long.class));
    }

    @Test
    @DisplayName("사용자 정보 저장 테스트")
    void user_save_test() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        RequestMemberDto memberDto = RequestMemberDto.builder()
                                                .name("개발자_한")
                                                .age(32L)
                                                .build();

        Member member = memberDto.toEntity();
        doReturn(member).when(memberService).join(refEq(memberDto, memberDto.getName(), Long.toString(memberDto.getAge())));

        // when & then
        mockMvc.perform
                (
                     post("/member/users")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer test123")
                    .contentType(MediaType.APPLICATION_JSON)//본문 요청에 json을 담아서 보내고 있다고 알려줌.
                    .content(objectMapper.writeValueAsString(memberDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("name").value("개발자_한"))
                .andDo(print());


        verify(memberService, atLeastOnce()).join(any(RequestMemberDto.class));
        verify(memberService, times(1)).join(any(RequestMemberDto.class));
    }
}