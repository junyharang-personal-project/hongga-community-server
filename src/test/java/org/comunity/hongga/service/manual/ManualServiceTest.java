package org.comunity.hongga.service.manual;

import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.comunity.hongga.repository.manual.querydsl.ManualQuerydslRepository;
import org.comunity.hongga.repository.member.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * 회원 관련 Service Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 *    주니하랑, 1.0.1, 2022.02.20 목록 조회, 상세 조회, 수정 코드 구현 -> NPE 발생
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.20 목록 조회, 상세 조회, 수정 코드 구현 -> NPE 발생
 * @See ""
 * @see <a href=""></a>
 * @TODO 모든 Test Error 확인.
 */

//@RunWith(SpringRunner.class)
public class ManualServiceTest {

    @InjectMocks private ManualServiceImpl manualService;

    @Mock private MemberRepository memberRepository;
    @Mock private ManualRepository manualRepository;
    @Mock private ManualQuerydslRepository manualQuerydslRepository;

//    @Test public void 메뉴얼_등록() throws Exception {

//        // given
//        Long manualNo = 1L;
//        Long memberNo = 3L;
//        String writer = "주니하랑";
//        String title = "Synology NAS 사용법";
//        String content = "이 제품은 이렇게 사용 하시면 삶의 질이 향상 되요!";
//
//        Manual manual = Manual.builder()
//                .title(title)
//                .content(content)
//                .build();
//
//        ManualWriteRequestDTO manualWriteRequestDTO = new ManualWriteRequestDTO(manualNo, title, content, writer,null ,null, null, null);
//
//
//        given(manualRepository.save(any())).willReturn(manual);
//
//        // when
//        DefaultResponse defaultResponse = manualService.writeManual(manualWriteRequestDTO, memberNo);
//
//        // then
//        assertThat(defaultResponse.getMessage()).isEqualTo("게시물 등록 성공");
//
//    } // 메뉴얼_등록() 끝

//        @Test public void 전체_조회 () {
//
//            // gien
//            List<ManualListSearchResponseDTO> manualList = new ArrayList<>();
//
//            String title = "Manual Service Test Code";
//
//            String title1 = "Manual Service Test Code";
//
//            ManualListSearchResponseDTO manual = ManualListSearchResponseDTO.builder()
//                    .title(title)
//                    .build();
//
//            ManualListSearchResponseDTO manual1 = ManualListSearchResponseDTO.builder()
//                    .title(title1)
//                    .build();
//
//            manualList.add(manual);
//            manualList.add(manual1);
//
//            // when
//            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("manualNo").descending());
//
//            PageImpl<ManualListSearchResponseDTO> manualPage = new PageImpl<>(manualList, pageRequest, 2);
//
//            given(manualQuerydslRepository.findAllWithMemberNickname(pageRequest)).willReturn(manualPage);
//
//            DefaultResponse<Page<ManualListSearchResponseDTO>> result = manualService.manualListSearch(pageRequest);
//
//            // then
//            assertThat(result.getMessage()).isEqualTo("조회 성공");
//            assertThat(result.getData().getTotalElements()).isEqualTo(2);
//
//        } // 전체_조회() 끝
//
//        @Test public void 게시물_수정 () {
//
//            // given
//            Long manualNo = 3L;
//            Optional<Member> writerOp = memberRepository.findById(1L);
//            Member writer = writerOp.get();
//            String title = "게시글 제목 Controller Test Code Test 중!";
//            String content = "게시글 내용 Controller Test Code Test 중!";
//
//            Manual manual = new Manual(writer, title, content);
//
//            given(manualRepository.save(any())).willReturn(manual);
//
//            String updateTitle = "게시글 제목 Controller Test Code 수정 Test 중!";
//            String updateContent = "게시글 내용 Controller Test Code 수정 Test 중!";
//
//            ManualUpdateRequestDTO request = new ManualUpdateRequestDTO(updateTitle, null, updateContent, null, null, null, null);
//
//            given(manualRepository.findById(3L)).willReturn(Optional.of(manual));
//
//            // when
//            DefaultResponse successResponse = manualService.updateManual(request, manualNo, 5L);
//            DefaultResponse failedResponse = manualService.updateManual(request, manualNo, 1L);
//
//            // then
//            assertThat(successResponse.getStatusCode()).isEqualTo(200); // 수정 성공시 200Code 반환 비교
//            assertThat(successResponse.getMessage()).isEqualTo("수정 성공");
//
//            assertThat(failedResponse.getStatusCode()).isEqualTo(200);  // 수정 실패시 200Code 반환 비교
//            assertThat(failedResponse.getMessage()).isEqualTo("수정 실패");
//
//        } // 게시물_수정() 끝
//
//        @Test public void 게시글_삭제 () {
//
//            // given
//            Long manualNo = 99L;
//            Optional<Member> writerOp = memberRepository.findById(6L);
//            Member writer = writerOp.get();
//            String title = "메뉴얼 게시글 삭제 테스트 중 입니다!";
//            String content = "Service Test Code";
//
//            Manual manual = new Manual(writer, title, content);
//
//            // when
//            given(manualRepository.findById(manualNo)).willReturn(Optional.of(manual));
//            DefaultResponse result = manualService.deleteManual(manualNo, 99L);
//
//            // then
//            assertThat(result.getMessage()).isEqualTo("삭제 성공");
//
//        } // 게시글_삭제() 끝
} // class 끝