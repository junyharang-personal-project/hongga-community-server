package org.comunity.hongga.controller.member;

import org.comunity.hongga.config.WebMvcConfig;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.service.member.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 회원 관련 Controller Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.15 로그인 서비스 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.15 로그인 서비스 구현
 * @See ""
 * @see <a href=""></a>
 */

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class memberControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private MemberService memberService;
    @MockBean private WebMvcConfig webMvcConfig;

    @Test public void 회원가입() throws Exception {

        mockMvc.perform(post(ServiceURIVersion.NOW_VERSION+"/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"email\": \"test@hongga.com\", \n" +
                        " \"password\": \"hong123456\", \n" +
                        " \"name\": \"홍주니\", \n" +
                        " \"nickname\": \"주니하랑\", \n" +
                        " \"phoneNumber\": \"010-3939-4848\", \n" +
                        " \"aboutMe\": \"안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!\" \n" +
                        "}")).andExpect(status().isOk());

    } // 회원가입() 끝

    @Test public void 로그인() throws Exception {

        mockMvc.perform(post(ServiceURIVersion.NOW_VERSION+"/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"email\": \"test@hongga.com\", \n" +
                        " \"password\": \"hong123456\"\n" +
                        "}")).andExpect(status().isOk());

    } // 로그인() 끝

} // class 끝