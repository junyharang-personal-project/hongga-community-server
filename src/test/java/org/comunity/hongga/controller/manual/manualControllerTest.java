package org.comunity.hongga.controller.manual;

import org.comunity.hongga.config.WebMvcConfig;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.service.manual.ManualServiceImpl;
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

    @MockBean private ManualServiceImpl manualService;
    @MockBean private WebMvcConfig webMvcConfig;

    @Test public void 메뉴얼_등록() throws Exception{
        String memberNo = "1";

        DefaultResponse defaultResponse = new DefaultResponse(HttpStatus.SEE_OTHER.value(), "게시물 등록 성공", null, null);
        given(manualService.writeManual(any(), anyLong())).willReturn(defaultResponse);

        mockMvc.perform(post(ServiceURIVersion.NOW_VERSION+"/paternal/manual/?memberNo={memberNo}", memberNo)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        " \"content\": \"이 제품은 이렇게 사용 하시면 삶의 질이 향상 되요!\",\n" +
                        " \"imageDTOLIST\": [\n" +
                        "   {\n" +
                        "       \"imageName\": \"abc.jsp\",\n" +
                        "       \"path\": \"/home/junyharang/upload\",\n" +
                        "       \"uuid\": \"c13ba02e-9a93-453d-a6b6-b4d75621fe43\"\n" +
                        "   }\n" +
                        " ],\n" +
                        " \"imageName\": \"abcsdgw.jsp\",\n" +
                        " \"manualNo\": 5,\n" +
                        " \"path\": \"/home/junyharang/upload\",\n" +
                        " \"tagContent\": \"메뉴얼\",\n" +
                        " \"tagDTOLIST\": [\n" +
                        "   {\n" +
                        "       \"tagContent\": \"시놀로지\"\n" +
                        "   }\n" +
                        " ],\n" +
                        " \"title\": \"시놀로지NAS 사용법\",\n" +
                        " \"uuid\": \"c13ba02e-9a93-453d-a6b6-b4d75621fe43\",\n" +
                        " \"writer\": \"주니하랑\"\n" +
                        "}")).andDo(print()).andExpect(status().isOk());

    } // 메뉴얼_등록() 끝

    @Test public void 게시글_목록_조회() throws Exception {

        mockMvc.perform(get(ServiceURIVersion.NOW_VERSION+"/paternal/manual")
                .param("page", "0")).andExpect(status().isOk());

    } // 게시글_전체조회() 끝


    @Test public void 게시글_상세조회() throws Exception {

        String manualNo = "10";
        String memberNo = "12";

        mockMvc.perform(get(ServiceURIVersion.NOW_VERSION+"/paternal/manual/{manualNo}", manualNo)
                .param("memberNo", memberNo)).andExpect(status().isOk());

    } // 게시글_상세조회() 끝


//    @Test public void 게시글_수정() throws Exception {
//
//        String manualNo = "103";
//        String memberNo = "1";
//
//        DefaultResponse defaultResponse = new DefaultResponse(HttpStatus.SEE_OTHER.value(), "수정 성공", null, null);
//
//        given(manualService.updateManual(any(), anyLong(), anyLong())).willReturn(defaultResponse);
//
//        mockMvc.perform(put(ServiceURIVersion.NOW_VERSION+"/family/manual/update/{manualNo}", manualNo).param("memberNo", memberNo)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        " \"title\" : \"게시글 제목 Controller Test Code Test 중!\",\n" +
//                        " \"content\" : \"게시글 내용 Controller Test Code Test 중!\"\n" +
//                        "}")).andExpect(status().isOk());
//    } // 게시글_수정() 끝
//
//    @Test public void 게시글_삭제() throws Exception {
//
//        String manualNo = "99";
//        String memberNo = "99";
//
//        DefaultResponse defaultResponse = new DefaultResponse(HttpStatus.SEE_OTHER.value(), "삭제 성공", null, null);
//
//        given(manualService.deleteManaul(any(), anyLong())).willReturn(defaultResponse);
//
//        mockMvc.perform(delete(ServiceURIVersion.NOW_VERSION+"/family/manual/delete/{manualNo}", manualNo).param("memberNo", memberNo)).andExpect(status().isOk());
//
//    } // 게시글_삭제() 끝

} // class 끝