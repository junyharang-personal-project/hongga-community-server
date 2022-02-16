package org.comunity.hongga.controller.manual;

import org.comunity.hongga.config.WebMvcConfig;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.service.manual.ManualService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 사용 설명서 관련 Controller Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RunWith(SpringRunner.class)
@WebMvcTest(ManualController.class)
public class manualControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private ManualService manualService;
    @MockBean private WebMvcConfig webMvcConfig;

    @Test public void 메뉴얼_등록() throws Exception{
        String memberNo = "1";

        DefaultResponse defaultResponse = new DefaultResponse(HttpStatus.SEE_OTHER.value(), "게시물 등록 성공", null, null);
        given(manualService.writeManual(any(), anyLong())).willReturn(defaultResponse);

        mockMvc.perform(post(ServiceURIVersion.NOW_VERSION+"/family/manual/{memberNo}", memberNo)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"title\": \"Synology NAS 사용법\",\n" +
                        " \"content\": \"이 제품은 이렇게 사용 하시면 삶의 질이 향상 되요!\"\n" +
                        "}")).andDo(print()).andExpect(status().isOk());

    } // 메뉴얼_등록() 끝

    @Test public void 게시글_전체조회() throws Exception {

        mockMvc.perform(get(ServiceURIVersion.NOW_VERSION+"/family/manual")
                .param("page", "0")).andExpect(status().isOk());

    } // 게시글_전체조회() 끝

} // class 끝