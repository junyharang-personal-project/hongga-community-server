package org.comunity.hongga.service.manual;

import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.Pagination;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
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
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 * </pre>
 *
 * @author 주니하랑
 * @version 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class ManualService {

    private final ManualRepository manualRepository;
    private final ManualTagRepository manualTagRepository;
    private final MemberRepository memberRepository;

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

    public DefaultResponse<Page<Manual>> manualListSearch(Pageable pageable) {

        log.info("SystemManualService가 동작 하였습니다!");
 //       log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + pageable.toString());
        log.info("manualListSearch(Pageable pageable, Long memberNo)가 호출 되었습니다!");
        log.info("manualRepository.findAllWithFetchJoin(pageable)를 호출하여 데이터를 조회 하겠습니다!");

//        Page<ManualListResponseDTO> manualList = manualQuerydslRepository.findAllWithFetchJoin(pageable);
        Page<Manual> manualList = manualRepository.findAllWithFetchJoin(pageable);

        log.info("manualRepository.findAllWithFetchJoin(pageable)에서 조회된 DATA : " + manualList.toString());

        log.info("manualRepository.findAllWithFetchJoin(pageable)를 통해 조회된 데이터가 없는지 검증 하겠습니다!");
        if (manualList.getTotalElements() == 0) {

            log.info("조회 된 데이터가 없습니다! 200 Code와 함께 message로 \"데이터 없음\"을 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "데이터 없음");

        } else {

            log.info("조회 된 데이터가 있습니다! 200 Code와 함께 message로 \"조회 성공\"과 조회된 데이터를 페이징 처리 하여 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "조회 성공", manualList, new Pagination(manualList));

        } // if - else (manualList.getTotalElements() == 0) 끝
    } // manualListSearch(Pageable pageable, Long memberNo) 끝

    public DefaultResponse<Manual> manualDetailSearch(Long manualNo) {

        // TODO - 상세 조회 시 회원 정보가 모두 나오지 않게 하고, 닉네임만 나오게 처리 필요

        log.info("SystemManualService가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + manualNo.toString());
        log.info("manualDetailSearch(Long manualNo)이 호출 되었습니다!");

        log.info("manualQuerydslRepository.findByManualId(manualNo)를 호출하여 요청으로 들어온 설명서 고유 번호를 찾겠습니다!");
//        Optional<ManualDetailResponseDTO> optionalManualDetailResponseDTO = manualQuerydslRepository.findByManualId(manualNo);
        Optional<Manual> manualSearch = manualRepository.findByManualAndWriter(manualNo);

        log.info("DB에서 찾은 자료가 존재 하는지 검증 하겠습니다!");
        if (manualSearch.isEmpty()) {

            log.info("이용자가 요청한 자료의 고유 번호가 DB에 존재하지 않습니다! 200 Code와 함께 \"데이터 없음\"을 반환 하겠습니다!");
            return DefaultResponse.response(HttpStatus.OK.value(), "데이터 없음");

        } else {

            log.info("이용자가 요청한 자료의 고유 번호가 존재 함으로, 200 Code와 함께 \"조회 성공\"을 반환 하겠습니다!");

            return manualSearch.map(Manual -> DefaultResponse.response(HttpStatus.OK.value(), "조회 성공", Manual))
                    .orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "조회 성공"));

        } // if - else (optionalManualDetailResponseDTO.isEmpty()) 끝

    } // manualDetailSearch(Long manualNo) 끝

    public DefaultResponse updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) {

        log.info("SystemManualService가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + manualUpdateRequestDTO.toString() + "," + manualNo.toString()  + "," + manualNo.toString());
        log.info("updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo)이 호출 되었습니다!");

        log.info("memberRepository.findByManualNo(manualNo, memberNo)를 호출하여 요청으로 들어온 설명서 고유 번호와 해당 글의 작성자가 맞는지 여부를 찾겠습니다!");
        Optional<Manual> findManual = manualRepository.findByManualNo(manualNo);

        log.info("DB에서 찾은 값이 Null인지 검증 하겠습니다!");
        if (findManual.isEmpty()) {

            log.info("DB에서 해당 자료를 찾아봤지만, 존재 하지 않습니다! 200 Code와 함께 \"내용 없음\" 반환 하겠습니다!");
            DefaultResponse.response(HttpStatus.OK.value(), "내용 없음");

        } // if (findManual.isEmpty()) 문 끝

        log.info("요청으로 들어온 이용자의 고유 번호가 DB에 저장된 해당 글의 작성자 고유 번호와 일치한지 검증 하겠습니다! \n 그런 뒤 요청으로 들어온 게시물 고유 번호와 DB에서 찾은 게시물 고유 번호가 일치한지 검증 하겠습니다!");
        return findManual.filter(manual -> manual.getWriter().getMemberNo().equals(memberNo))
                .filter(manual -> manual.getManualNo().equals(manualNo)).map(manual -> {

                    log.info("요청으로 들어온 회원 고유 번호와 게시글 고유 번호가 DB에서 찾은 자료의 값과 일치 합니다!");
                    log.info("manualRepository.updateManual(manualUpdateRequestDTO, manualNo, memberNo)를 호출하여 DB 값을 변경 하겠습니다!");
                    manualRepository.updateManual(manualUpdateRequestDTO, manualNo);

                    log.info("DB에 해당 게시물 수정이 완료 되었습니다! 200 Code와 함께 \"수정 성공\" 반환 하겠습니다!");

                    return DefaultResponse.response(HttpStatus.OK.value(), "수정 성공");
                }).orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "수정 실패"));

    } // updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) 끝
} // class 끝
