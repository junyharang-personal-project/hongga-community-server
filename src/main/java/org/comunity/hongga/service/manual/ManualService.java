package org.comunity.hongga.service.manual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용 설명서 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class ManualService {

    private ManualRepository systemManualRepository;
    private MemberRepository memberRepository;

    public DefaultResponse writeManual(ManualWriteRequestDTO systemManualWriteRequestDTO, Long memberNo) {

        Optional<Member> writer = memberRepository.findById(memberNo);

        log.info("SystemManualService가 동작 하였습니다!");
        log.info("writeManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO)이 호출 되었습니다!");

        if (systemManualWriteRequestDTO == null) {  /* 메뉴얼에 입력 값이 없다면? */

            log.info("시스템 메뉴얼 등록에 입력 되지 않은 내용이 있습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "게시물 등록 실패");

        } // if문 끝

        log.info("SystemManualRepository의 save()를 호출하여 systemManualWriteRequestDTO에 담긴 게시글을 저장 하겠습니다!");

        Optional<Manual> writeManual = Optional.ofNullable(systemManualRepository.save(systemManualWriteRequestDTO.toEntity(systemManualWriteRequestDTO, writer)));

        // TODO - Tag 관련 내용 추가

        return DefaultResponse.response(HttpStatus.OK.value(), "게시물 등록 성공");

    } // writeManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO) 끝

} // class 끝
