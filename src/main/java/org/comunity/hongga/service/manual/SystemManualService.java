package org.comunity.hongga.service.manual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.model.dto.request.manual.SystemManualWriteRequestDTO;
import org.comunity.hongga.model.entity.manual.SystemManual;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.member.MemberRepository;
import org.comunity.hongga.repository.manual.SystemManualRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor @Slf4j
@Service public class SystemManualService {

    private SystemManualRepository systemManualRepository;
    private MemberRepository memberRepository;

    public DefaultResponse writeSystemManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO, Long memberId) {

        Optional<Member> writer = memberRepository.findById(memberId);

        log.info("SystemManualService가 동작 하였습니다!");
        log.info("writeManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO)이 호출 되었습니다!");

        if (systemManualWriteRequestDTO == null) {  /* 메뉴얼에 입력 값이 없다면? */

            log.info("시스템 메뉴얼 등록에 입력 되지 않은 내용이 있습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "입력 되어야 할 값이 누락 되었습니다!");

        } // if문 끝

        log.info("SystemManualRepository의 save()를 호출하여 systemManualWriteRequestDTO에 담긴 게시글을 저장 하겠습니다!");

        Optional<SystemManual> writeManual = Optional.of(systemManualRepository.save(systemManualWriteRequestDTO.toEntity(systemManualWriteRequestDTO, writer)));

        // TODO - Tag 관련 내용 추가

        return DefaultResponse.response(HttpStatus.OK.value(), "메뉴얼 게시글 등록이 성공하셨습니다!");

    } // writeManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO) 끝

} // class 끝
