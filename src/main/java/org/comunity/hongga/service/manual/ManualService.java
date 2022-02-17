package org.comunity.hongga.service.manual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.Pagination;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.comunity.hongga.repository.manual.ManualTagRepository;
import org.comunity.hongga.repository.manual.querydsl.ManualQuerydslRepository;
import org.comunity.hongga.repository.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 사용 설명서 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.16 Tag 등록 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.16 Tag 등록 추가
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class ManualService {

    private ManualRepository manualRepository;
    private ManualTagRepository manualTagRepository;
    private MemberRepository memberRepository;

    private final ManualQuerydslRepository manualQuerydslRepository;

    public DefaultResponse writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo) {

        Optional<Member> writer = memberRepository.findById(memberNo);

        log.info("SystemManualService가 동작 하였습니다!");
        log.info("writeManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO)이 호출 되었습니다!");

        if (manualWriteRequestDTO == null) {  /* 메뉴얼에 입력 값이 없다면? */

            log.info("시스템 메뉴얼 등록에 입력 되지 않은 내용이 있습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "게시물 등록 실패");

        } // if문 끝

        log.info("SystemManualRepository의 save()를 호출하여 systemManualWriteRequestDTO에 담긴 게시글을 저장 하겠습니다!");

        Optional<Manual> writeManual = Optional.ofNullable(manualRepository.save(manualWriteRequestDTO.toEntity(manualWriteRequestDTO, writer)));

        // TODO - Tag 관련 내용 추가
        log.info("tagRepository의 save()를 호출하여 systemManualWriteRequestDTO에 담긴 Tag를 저장 하겠습니다!");

        manualTagRepository.save(ManualTag.builder().manual(writeManual.get()).tagContent(manualWriteRequestDTO.getTagContent()).build());

        return DefaultResponse.response(HttpStatus.OK.value(), "게시물 등록 성공");

    } // writeManual(SystemManualWriteRequestDTO systemManualWriteRequestDTO) 끝

    public DefaultResponse<Page<ManualListResponseDTO>> manualListSearch(Pageable pageable) {

        log.info("SystemManualService가 동작 하였습니다!");
 //       log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + pageable.toString());
        log.info("manualListSearch(Pageable pageable, Long memberNo)가 호출 되었습니다!");
        log.info("manualQuerydslRepository.findAllWithFetchJoin(pageable)를 호출하여 데이터를 조회 하겠습니다!");

        Page<ManualListResponseDTO> manualList = manualQuerydslRepository.findAllWithFetchJoin(pageable);

        log.info("manualQuerydslRepository.findAllWithFetchJoin(pageable)에서 조회된 DATA : " + manualList.toString());

        log.info("manualQuerydslRepository.findAllWithFetchJoin(pageable, memberNo)를 통해 조회된 데이터가 없는지 검증 하겠습니다!");
        if (manualList.getTotalElements() == 0) {

            log.info("조회 된 데이터가 없습니다! 200 Code와 함께 message로 \"데이터 없음\"을 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "데이터 없음");

        } else {

            log.info("조회 된 데이터가 있습니다! 200 Code와 함께 message로 \"조회 성공\"과 조회된 데이터를 페이징 처리 하여 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "조회 성공", manualList, new Pagination(manualList));

        } // if - else (manualList.getTotalElements() == 0) 끝
    } // manualListSearch(Pageable pageable, Long memberNo)

    public DefaultResponse<ManualDetailResponseDTO> manualDetailSearch(Long manualNo) {

        log.info("SystemManualService가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + manualNo.toString());
        log.info("manualDetailSearch(Long manualNo)이 호출 되었습니다!");

        log.info("manualQuerydslRepository.findByManualId(manualNo)를 호출하여 요청으로 들어온 설명서 고유 번호를 찾겠습니다!");
        Optional<ManualDetailResponseDTO> optionalManualDetailResponseDTO = manualQuerydslRepository.findByManualId(manualNo);

        log.info("DB에서 찾은 자료가 존재 하는지 검증 하겠습니다!");
        if (optionalManualDetailResponseDTO.isEmpty()) {

            log.info("이용자가 요청한 자료의 고유 번호가 DB에 존재하지 않습니다! 200 Code와 함께 \"데이터 없음\"을 반환 하겠습니다!");
            return DefaultResponse.response(HttpStatus.OK.value(), "데이터 없음");

        } else {

            log.info("이용자가 요청한 자료의 고유 번호가 존재 함으로, 200 Code와 함께 \"조회 성공\"을 반환 하겠습니다!");

            return optionalManualDetailResponseDTO.map(manualDetailResponseDTO -> DefaultResponse.response(HttpStatus.OK.value(), "조회 성공", manualDetailResponseDTO))
                    .orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "조회 성공"));

        } // if - else (optionalManualDetailResponseDTO.isEmpty()) 끝

    } // manualDetailSearch(Long manualNo) 끝
} // class 끝
