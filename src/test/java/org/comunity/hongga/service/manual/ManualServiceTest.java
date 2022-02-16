package org.comunity.hongga.service.manual;

import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.comunity.hongga.repository.member.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * 회원 관련 Service Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 * @TODO 메뉴얼_등록 Error 확인.
 */

//@RunWith(SpringRunner.class)
public class ManualServiceTest {
//
//    @InjectMocks private ManualService manualService;
//
//    @Mock private MemberRepository memberRepository;
//    @Mock private ManualRepository manualRepository;
//
//    @Test public void 메뉴얼_등록() throws Exception{
//
//        // given
//        Long writerNo = 1L;
//        String title = "Synology NAS 사용법";
//        String content = "이 제품은 이렇게 사용 하시면 삶의 질이 향상 되요!";
//
//        Manual manual = Manual.builder()
//                .title(title)
//                .content(content)
//                .build();
//
//        ManualWriteRequestDTO manualWriteRequestDTO = new ManualWriteRequestDTO(title, content);
//
//        given(manualRepository.save(any())).willReturn(manual);
//
//        // when
//        DefaultResponse defaultResponse = manualService.writeManual(manualWriteRequestDTO, writerNo);
//
//        // then
//        assertThat(defaultResponse.getMessage()).isEqualTo("게시물 등록 성공");
//
//    } // 메뉴얼_등록() 끝
//
} // class 끝