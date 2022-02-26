package org.comunity.hongga.service.manual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.Pagination;
import org.comunity.hongga.model.dto.request.manual.ManualImageDTO;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDeleteResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.repository.manual.ManualImageRepository;
import org.comunity.hongga.repository.manual.ManualRepository;
import org.comunity.hongga.repository.manual.ManualTagRepository;
import org.comunity.hongga.repository.manual.querydsl.ManualImageQuerydslRepository;
import org.comunity.hongga.repository.manual.querydsl.ManualQuerydslRepository;
import org.comunity.hongga.repository.manual.querydsl.ManualTagQuerydslRespository;
import org.comunity.hongga.repository.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 사용 설명서 관련 비즈니스 로직
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.0.1, 2022.02.16 Tag 등록 추가
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.1, 2022.02.19 수정 기능 구현
 *    주니하랑, 1.2.2, 2022.02.19 수정 기능 Tag로 인한 Refactoring
 *    주니하랑, 1.3.0, 2022.02.21 사진 등록 처리로 인한 Refactoring
 *    주니하랑, 1.3.1, 2022.02.25 상세 조회 기능 구현을 위한 Refactoring
 *    주니하랑, 1.3.2, 2022.02.25 삭제 기능 구현을 위한 Refactoring (Image 삭제 처리)
 * </pre>
 *
 * @author 주니하랑
 * @version 1.3.2, 2022.02.25 삭제 기능 구현을 위한 Refactoring (Image 삭제 처리)
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j
@Service public class ManualServiceImpl implements ManualService{

    private final ManualRepository manualRepository;
    private final ManualImageRepository manualImageRepository;
    private final ManualTagRepository manualTagRepository;
    private final MemberRepository memberRepository;

    private final ManualQuerydslRepository manualQuerydslRepository;
    private final ManualImageQuerydslRepository manualImageQuerydslRepository;
    private final ManualTagQuerydslRespository manualTagQuerydslRespository;

    /**
     * 글 등록
     * @param manualWriteRequestDTO - Client에서 입력한 값을 담은 DTO
     * @param memberNo - 글 작성 이용자 고유 번호
     * @return DefaultResponse<Long> - 응답 관련 정리 해둔 Class를 통해 작성된 게시글의 Manual 고유 번호를 반환(DB에 PK값)
     */

    @Transactional
    @Override
    public DefaultResponse<Long> writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo) {

        Optional<Member> writer = memberRepository.findById(memberNo);

        log.info("ManualServiceImpl 동작 하였습니다!");
        log.info("writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo) 호출 되었습니다!");

        if (manualWriteRequestDTO == null) {  /* 메뉴얼에 입력 값이 없다면? */

            log.info("시스템 메뉴얼 등록에 입력 되지 않은 내용이 있습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "게시물 등록 실패");

        } // if (manualWriteRequestDTO == null)

        log.info("manualRepository.save(manual)를 호출하여 ManualWriteRequestDTO에 담긴 게시글을 저장 하겠습니다!");

        Optional<Manual> saveManual = Optional.ofNullable(manualRepository.save(manualWriteRequestDTO.toEntity(manualWriteRequestDTO, writer)));


        manualImageRepository.save(ManualImage.builder()
                .manual(saveManual.get()).uuid(manualWriteRequestDTO.getUuid())
                .manual(saveManual.get()).imgName(manualWriteRequestDTO.getImgName())
                .manual(saveManual.get()).path(manualWriteRequestDTO.getPath())
                .build());

        manualTagRepository.save(ManualTag.builder()
                .manual(saveManual.get()).tagContent(manualWriteRequestDTO.getTagContent())
                .build());

        return DefaultResponse.response(HttpStatus.OK.value(), "등록 성공", memberNo);

    } // writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo) 끝


    /**
     * 전체 조회 (목록 조회)
     * @param pageable - Paging 처리를 위한 객체
     * @return DefaultResponse<Page<Manual>> - DB에서 조회된 게시글 목록을 페이징 처리하여 반환
     * @see "코드로 배우는 스프링 부트 웹 프로젝트 P.437"
     */

    @Override
    public DefaultResponse<Page<ManualListSearchResponseDTO>> manualListSearch(Pageable pageable) {

        log.info("ManualServiceImpl 동작 하였습니다!");
        log.info("manualListSearch(Pageable pageable, Long memberNo)가 호출 되었습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + pageable.toString());
        log.info("manualQuerydslRepository.findAllWithMemberNickname(pageable)를 호출하여 데이터를 조회 하겠습니다!");

        Page<ManualListSearchResponseDTO> manualList = manualQuerydslRepository.findAllWithMemberNickname(pageable);

        log.info("manualQuerydslRepository.findAllWithMemberNickname(pageable)에서 조회된 DATA : " + manualList.toString());

        log.info("manualQuerydslRepository.findAllWithMemberNickname(pageable)를 통해 조회된 데이터가 없는지 검증 하겠습니다!");
        if (manualList.getTotalElements() == 0) {

            log.info("조회 된 데이터가 없습니다! 200 Code와 함께 message로 \"데이터 없음\"을 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "데이터 없음");

        } else {

            log.info("조회 된 데이터가 있습니다! 200 Code와 함께 message로 \"조회 성공\"과 조회된 데이터를 페이징 처리 하여 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "조회 성공", manualList, new Pagination(manualList));

        } // if - else (manualList.getTotalElements() == 0) 끝
    } // manualListSearch(Pageable pageable, Long memberNo) 끝

    /**
     * 상세 조회
     * @param manualNo - 검색을 위한 게시글 고유 번호
     * @return DefaultResponse<ManualDetailResponseDTO> - DB에서 조회된 게시글 상세 정보 반환
     * @see ""
     */

    @Override
    public DefaultResponse<List<ManualDetailResponseDTO>> manualDetailSearch(Long manualNo) {

    Optional<List<ManualDetailResponseDTO>> dbFindDetailManual = manualQuerydslRepository.findByManualNo(manualNo);

    // TODO - 한 개의 게시글에 N개의 사진과 N개의 Tag가 있을 경우 여러번 게시글이 조회 되는 문제 해결 해야 함. 즉, 한 개에 게시글에 N개의 TAG와 Image 정보가 나올 수 있도록


        log.info("DB에서 조회된 값은 다음과 같습니다! \n " +dbFindDetailManual.get().toString());


    if (dbFindDetailManual.get().size() == 0) {

        log.info("DB에서 찾은 메뉴얼 게시글 상세 번호 " + manualNo + "에 대한 게시글이 조회 되지 않았습니다!");

        log.info("403 CODE와 함께 \"내용 없음\" 반환 하겠습니다!");

        return DefaultResponse.response(HttpStatus.NOT_FOUND.value(), "내용 없음");

    } // if (dbFindDetailManual.isEmpty()) 끝

        return dbFindDetailManual.map(manualDetailResponseDTO -> DefaultResponse.response(HttpStatus.OK.value(), "조회 성공", manualDetailResponseDTO))
                .orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "조회 실패"));

    } // manualDetailSearch(Long manualNo) 끝

    @Override
    public DefaultResponse<Long> updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) {

        log.info("ManualService가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + manualUpdateRequestDTO.toString() + "," + "수정 대상 게시물 고유 번호 : " + manualNo.toString()  + "," + "수정 요청 이용자 고유 번호 : " + memberNo.toString());
        log.info("updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo)이 호출 되었습니다!");

        log.info("memberRepository.findByManualNo(manualNo, memberNo)를 호출하여 요청으로 들어온 설명서 고유 번호와 해당 글의 작성자가 맞는지 여부를 찾겠습니다!");
//        Optional<List<ManualDetailResponseDTO>> findManual = manualQuerydslRepository.findByManualNo(manualNo);
        Optional<Manual> findManual = manualQuerydslRepository.findByManualNo(manualNo, memberNo);

        log.info("DB에서 찾은 값이 Null인지 검증 하겠습니다! \n DB에서 찾은 값 : " + findManual.get().toString());

        if (findManual.isEmpty()) {

            log.info("DB에서 해당 자료를 찾아봤지만, 존재 하지 않습니다! 200 Code와 함께 \"내용 없음\" 반환 하겠습니다!");
            DefaultResponse.response(HttpStatus.OK.value(), "내용 없음");

        } // if (findManual.isEmpty()) 문 끝

        log.info("요청으로 들어온 이용자의 고유 번호가 DB에 저장된 해당 글의 작성자 고유 번호와 일치한지 검증 하겠습니다! \n 그런 뒤 요청으로 들어온 게시물 고유 번호와 DB에서 찾은 게시물 고유 번호가 일치한지 검증 하겠습니다!");

        return findManual.filter(manual -> manual.getWriter().getMemberNo().equals(memberNo))
                .filter(manual -> manual.getManualNo().equals(manualNo))
                .map(manual -> {

                    log.info("요청으로 들어온 회원 고유 번호와 게시글 고유 번호가 DB에서 찾은 자료의 값과 일치 합니다!");
                    log.info("manualQuerydslRepository.updateManual(manualUpdateRequestDTO, manualNo, memberNo)를 호출하여 게시글 수정 건에 대한 DB 값을 변경 하겠습니다!");

                    manualQuerydslRepository.updateManual(manualUpdateRequestDTO, manualNo, memberNo);

                    log.info("manualImageRepository.updateManualImage(manualUpdateRequestDTO, manual.getManualNo())를 호출하여 Image 수정 건에 대한 DB 값을 변경 하겠습니다!");
                    manualImageQuerydslRepository.updateManualImage(manualUpdateRequestDTO, manual.getManualNo());

                    log.info("manualTagQuerydslRepository.updateManualTag(manualUpdateRequestDTO, manual.getManualNo())를 호출하여 Tag 수정 건에 대한 DB 값을 변경 하겠습니다!");
                    manualTagQuerydslRespository.updateManualTag(manualUpdateRequestDTO, manual.getManualNo());

                    log.info("DB에 해당 게시물 수정이 완료 되었습니다! 200 Code와 함께 \"수정 성공\" 반환 하겠습니다!");

                    return DefaultResponse.response(HttpStatus.OK.value(), "수정 성공", manual.getManualNo());
                }).orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "수정 실패"));

    } // updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) 끝

    /**
     * 게시글 삭제
     * @param manualNo - 검색을 위한 게시글 고유 번호
     * @return DefaultResponse - 삭제 관련 처리에 대한 HTTP 응답에 맞는 코드와 메시지 전달
     * @see ""
     */

    @Override
    public DefaultResponse<Long> deleteManaul(Long manualNo, Long memberNo) {

        log.info("ManualService의 deleteManaul(Long manualNo, Long memberNo)가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + "메뉴얼 고유 번호 : " + manualNo.toString()  + "," + "작성자 고유 번호 : " + memberNo.toString());

        log.info("DB를 통해 이용자가 요청한 게시글 존재 여부와 해당 게시글의 작성자가 이용자인지 찾아 보겠습니다!");
        Optional<Manual> dbInManual = manualRepository.findById(manualNo);

        log.info("DB를 통해 찾은 해당 게시글이 존재하는 지 검증 하겠습니다!");

        if (dbInManual.isEmpty()) {

            log.info("DB를 통해 찾아 본 결과 해당 게시글이 존재 하지 않습니다! 200 Code와 함께 \"내용 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(HttpStatus.OK.value(), "내용 없음");
        } // if (dbInManualAndWriter.isEmpty()) 끝

        log.info("DB를 통해 찾아 본 결과 해당 게시글이 존재 합니다!");

        return dbInManual.filter(manual -> manual.getManualNo().equals(manualNo))
                .filter(manual -> manual.getWriter().getMemberNo().equals(memberNo)).map(manual -> {

                    log.info("DB를 통해 해당 게시글의 관계 맺어진 사진들을 먼저 모두 삭제 하겠습니다!");
                    manualImageRepository.deleteByManualNo(manual.getManualNo());

                    log.info("DB를 통해 해당 게시글의 관계 맺어진 Tag들을 먼저 모두 삭제 하겠습니다!");
                    manualTagRepository.deleteByManualNo(manual.getManualNo());

                    log.info("DB를 통해 해당 게시글 삭제를 진행 하겠습니다!");
                    manualRepository.deleteById(manualNo);

                    log.info("삭제가 정상 적으로 처리 되었습니다! 200 Code와 함께 \"삭제 성공\" 반환 하겠습니다!");
                    return DefaultResponse.response(HttpStatus.OK.value(), "삭제 성공", manualNo);

                }).orElseGet(() -> DefaultResponse.response(HttpStatus.OK.value(), "삭제 실패"));

    } // deleteManaul(Long manualNo, Long memberNo) 끝
} // class 끝

