package org.comunity.hongga.service.manual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.Pagination;
import org.comunity.hongga.constant.ResponseCode;
import org.comunity.hongga.model.dto.request.manual.ManualWriteAndUpdateRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListContentSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListTagContentSearchResponseDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
 *    주니하랑, 1.4.0, 2022.02.28 검색 기능(제목, 제목+내용, TAG) 구현
 *    주니하랑, 1.4.1, 2022.03.02 ResponseCode 상세 구현을 위해 수정
 *    주니하랑, 1.4.2  2022.03.04 글 등록, 수정 RequestDTO 결합으로 인한 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.4.2  2022.03.04 글 등록, 수정 RequestDTO 결합으로 인한 수정
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
     * @param manualWriteAndUpdateRequestDTO - Client에서 입력한 값을 담은 DTO
     * @param memberNo - 글 작성 이용자 고유 번호
     * @return DefaultResponse<Long> - 응답 관련 정리 해둔 Class를 통해 작성된 게시글의 Manual 고유 번호를 반환(DB에 PK값)
     */

    @Transactional
    @Override
    public DefaultResponse<Long> writeManual(ManualWriteAndUpdateRequestDTO manualWriteAndUpdateRequestDTO, Long memberNo) {

        Optional<Member> writer = memberRepository.findById(memberNo);

        log.info("ManualServiceImpl 동작 하였습니다!");
        log.info("writeManual(ManualWriteRequestDTO manualWriteRequestDTO, Long memberNo) 호출 되었습니다!");

        if (manualWriteAndUpdateRequestDTO == null) {  /* 메뉴얼에 입력 값이 없다면? */

            log.info("시스템 메뉴얼 등록에 입력 되지 않은 내용이 있습니다!");

            return DefaultResponse.response(ResponseCode.NotFoundFile.getCode(), ResponseCode.NotFoundFile.getMessageKo(), ResponseCode.NotFoundFile.getMessageEn());

        } // if (manualWriteRequestDTO == null)

        log.info("manualRepository.save(manual)를 호출하여 ManualWriteRequestDTO에 담긴 게시글을 저장 하겠습니다!");

        Optional<Manual> saveManual = Optional.ofNullable(manualRepository.save(manualWriteAndUpdateRequestDTO.toEntity(manualWriteAndUpdateRequestDTO, writer)));


        manualImageRepository.save(ManualImage.builder()
                .manual(saveManual.get()).uuid(manualWriteAndUpdateRequestDTO.getUuid())
                .manual(saveManual.get()).imgName(manualWriteAndUpdateRequestDTO.getImgName())
                .manual(saveManual.get()).path(manualWriteAndUpdateRequestDTO.getPath())
                .build());

        manualTagRepository.save(ManualTag.builder()
                .manual(saveManual.get()).tagContent(manualWriteAndUpdateRequestDTO.getTagContent())
                .build());

        return DefaultResponse.response(ResponseCode.CREATE.getCode(), ResponseCode.CREATE.getMessageKo(), ResponseCode.CREATE.getMessageEn(), memberNo);

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

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } else {

            log.info("조회 된 데이터가 있습니다! 200 Code와 함께 message로 \"조회 성공\"과 조회된 데이터를 페이징 처리 하여 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), manualList, new Pagination(manualList));

        } // if - else (manualList.getTotalElements() == 0) 끝
    } // manualListSearch(Pageable pageable, Long memberNo) 끝

    /**
     * 상세 조회
     * @param manualNo - 검색을 위한 게시글 고유 번호
     * @return DefaultResponse<ManualDetailResponseDTO> - DB에서 조회된 게시글 상세 정보 반환
     * @see ""
     */

    @Override
    public DefaultResponse<ManualDetailResponseDTO> manualDetailSearch(Long manualNo) {

        log.info("DB를 통해 이용자가 조회 요청한 게시글의 상세 정보를 조회 하겠습니다!");
        Optional<Manual> searchManual = manualRepository.findByManualNo(manualNo);

        log.info("DB를 통해 이용자가 조회 요청한 게시글 이미지의 상세 정보를 조회 하겠습니다!");
        List<ManualImage> searchManualImage = manualImageRepository.findByManualNo(manualNo);

        log.info("DB를 통해 이용자가 조회 요청한 게시글 TAG의 상세 정보를 조회 하겠습니다!");
        List<ManualTag> searchManualTag = manualTagRepository.findByManualNo(manualNo);

        // Image와 TAG는 Null이 들어올 수 있기 때문에 Null Check를 하지 않음.
        if (searchManual.isEmpty()) {

            log.info("DB에서 조회된 결과가 없습니다! 404 Code와 함께 \"데이터 없음\" 반환 하겠습니다!");
            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (result.isEmpty()) 끝

        log.info("DB에서 찾은 Manual 정보를 Manual 객체로 변환하겠습니다!");
        Manual changeEntityManual = searchManual.get();


        log.info("DB에서 조회된 Entity 객체 Type의 결과값을 모두 DTO 객체 형태로 변환 하겠습니다! \n entitiesToDTO(changeEntityManual, searchManualImage, searchManualTag)를 호출 하겠습니다!");
        ManualDetailResponseDTO manualDetailResponseDTO = entitiesToDTO(changeEntityManual, searchManualImage, searchManualTag);

        log.info("Null 방지를 위해 변환된 DTO 객체를 Optional로 감싸주겠습니다!");
        Optional<ManualDetailResponseDTO> resultManual = Optional.of(manualDetailResponseDTO);

        return resultManual.map(manualSearch -> DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), manualSearch))
                .orElseGet(() -> DefaultResponse.response(ResponseCode.ServerError.getCode(), ResponseCode.ServerError.getMessageKo(), ResponseCode.ServerError.getMessageEn()));

    } // manualDetailSearch(Long manualNo) 끝

    @Override
    public DefaultResponse<Long> updateManual(ManualWriteAndUpdateRequestDTO manualWriteAndUpdateRequestDTO, Long manualNo, Long memberNo) {

        log.info("ManualService가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + manualWriteAndUpdateRequestDTO.toString() + "," + "수정 대상 게시물 고유 번호 : " + manualNo.toString()  + "," + "수정 요청 이용자 고유 번호 : " + memberNo.toString());
        log.info("updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo)이 호출 되었습니다!");

        log.info("memberRepository.findByManualNo(manualNo, memberNo)를 호출하여 요청으로 들어온 설명서 고유 번호와 해당 글의 작성자가 맞는지 여부를 찾겠습니다!");
//        Optional<List<ManualDetailResponseDTO>> findManual = manualQuerydslRepository.findByManualNo(manualNo);
        Optional<Manual> findManual = manualQuerydslRepository.findByManualNo(manualNo, memberNo);

        log.info("DB에서 찾은 값이 Null인지 검증 하겠습니다! \n DB에서 찾은 값 : " + findManual.get().toString());

        if (findManual.isEmpty()) {

            log.info("DB에서 해당 자료를 찾아봤지만, 존재 하지 않습니다! 204 Code와 함께 \"검색 결과가 없습니다.\" 반환 하겠습니다!");
            DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (findManual.isEmpty()) 문 끝

        log.info("요청으로 들어온 이용자의 고유 번호가 DB에 저장된 해당 글의 작성자 고유 번호와 일치한지 검증 하겠습니다! \n 그런 뒤 요청으로 들어온 게시물 고유 번호와 DB에서 찾은 게시물 고유 번호가 일치한지 검증 하겠습니다!");

        return findManual.filter(manual -> manual.getWriter().getMemberNo().equals(memberNo))
                .filter(manual -> manual.getManualNo().equals(manualNo))
                .map(manual -> {

                    log.info("요청으로 들어온 회원 고유 번호와 게시글 고유 번호가 DB에서 찾은 자료의 값과 일치 합니다!");
                    log.info("manualQuerydslRepository.updateManual(manualUpdateRequestDTO, manualNo, memberNo)를 호출하여 게시글 수정 건에 대한 DB 값을 변경 하겠습니다!");

                    manualQuerydslRepository.updateManual(manualWriteAndUpdateRequestDTO, manualNo, memberNo);

                    log.info("manualImageRepository.updateManualImage(manualUpdateRequestDTO, manual.getManualNo())를 호출하여 Image 수정 건에 대한 DB 값을 변경 하겠습니다!");
                    manualImageQuerydslRepository.updateManualImage(manualWriteAndUpdateRequestDTO, manual.getManualNo());

                    log.info("manualTagQuerydslRepository.updateManualTag(manualUpdateRequestDTO, manual.getManualNo())를 호출하여 Tag 수정 건에 대한 DB 값을 변경 하겠습니다!");
                    manualTagQuerydslRespository.updateManualTag(manualWriteAndUpdateRequestDTO, manual.getManualNo());

                    log.info("DB에 해당 게시물 수정이 완료 되었습니다! 200 Code와 함께 \"수정 성공\" 반환 하겠습니다!");

                    return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), manual.getManualNo());
                }).orElseGet(() -> DefaultResponse.response(ResponseCode.InvalidValueType.getCode(), ResponseCode.InvalidValueType.getMessageKo(), ResponseCode.InvalidValueType.getMessageEn()));

    } // updateManual(ManualUpdateRequestDTO manualUpdateRequestDTO, Long manualNo, Long memberNo) 끝

    /**
     * 게시글 삭제
     * @param manualNo - 검색을 위한 게시글 고유 번호
     * @return DefaultResponse - 삭제 관련 처리에 대한 HTTP 응답에 맞는 코드와 메시지 전달
     * @see ""
     */

    @Override
    public DefaultResponse deleteManual(Long manualNo, Long memberNo) {

        log.info("ManualService의 deleteManaul(Long manualNo, Long memberNo)가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + "메뉴얼 고유 번호 : " + manualNo.toString()  + "," + "작성자 고유 번호 : " + memberNo.toString());

        log.info("DB를 통해 이용자가 요청한 게시글 존재 여부와 해당 게시글의 작성자가 이용자인지 찾아 보겠습니다!");
        Optional<Manual> dbInManual = manualRepository.findById(manualNo);

        log.info("DB를 통해 찾은 해당 게시글이 존재하는 지 검증 하겠습니다!");

        if (dbInManual.isEmpty()) {

            log.info("DB를 통해 찾아 본 결과 해당 게시글이 존재 하지 않습니다! 200 Code와 함께 \"내용 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());
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
                    return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), manualNo);

                }).orElseGet(() -> DefaultResponse.response(ResponseCode.InvalidValueType.getCode(), ResponseCode.InvalidValueType.getMessageKo(), ResponseCode.InvalidValueType.getMessageEn()));

    } // deleteManaul(Long manualNo, Long memberNo) 끝


    /**
     * 제목으로 게시물 검색
     * @param title - 이용자가 검색 요청한 제목 검색
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Page<ManualListSearchResponseDTO>> titleSearch(String title, Pageable pageable) {

        log.info("ManualService의 titleSearch(String title, Pageable pageable)가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + "검색 요청 글 제목 : " + title  + "," + "페이징 요청 값 : " + pageable.toString());
        log.info("DB를 통해 이용자가 요청한 게시글 존재 여부를 찾아 보겠습니다!");

        Page<ManualListSearchResponseDTO> listSearchResponseDTOS = manualQuerydslRepository.findByTitle(title, pageable);

        if (listSearchResponseDTOS.isEmpty()) {

            log.info("이용자가 검색한 제목의 일치하는 게시글이 존재하지 않습니다! 204 Code와 함께 \"검색 결과 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (listSearchResponseDTOS.isEmpty()) 끝

        log.info("이용자가 검색한 내용의 게시글을 찾았습니다! 200 Code와 함께 \"검색 성공\" 반환 하겠습니다!");

        return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), listSearchResponseDTOS);

    } // titleSearch(String title, Pageable pageable) 끝


    /**
     * 내용으로 게시물 검색
     * @param content - 이용자가 검색 요청한 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Page<ManualListContentSearchResponseDTO>> contentSearch(String content, Pageable pageable) {
        log.info("ManualService의 contentSearch(String content, Pageable pageable)가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + "검색 요청 글 제목 : " + content  + "," + "페이징 요청 값 : " + pageable.toString());
        log.info("DB를 통해 이용자가 요청한 게시글 존재 여부를 찾아 보겠습니다!");

        Page<ManualListContentSearchResponseDTO> listSearchResponseDTOS = manualQuerydslRepository.findByContent(content, pageable);

        if (listSearchResponseDTOS.isEmpty()) {

            log.info("이용자가 검색한 내용의 일치하는 게시글이 존재하지 않습니다! 204 Code와 함께 \"검색 결과 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (listSearchResponseDTOS.isEmpty()) 끝

        log.info("이용자가 검색한 내용의 게시글을 찾았습니다! 200 Code와 함께 \"검색 성공\" 반환 하겠습니다!");

        return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), listSearchResponseDTOS);

    } // contentSearch(String content, Pageable pageable) 끝


    /**
     * 제목 + 내용으로 게시물 검색
     * @param query - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Page<ManualListContentSearchResponseDTO>> contentTitleSearch(String query, Pageable pageable) {
        log.info("ManualService의 contentTitleSearch(String query, Pageable pageable)가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + "검색 요청 글 제목 : " + query  + "," + "페이징 요청 값 : " + pageable.toString());
        log.info("DB를 통해 이용자가 요청한 게시글 존재 여부를 찾아 보겠습니다!");

        Page<ManualListContentSearchResponseDTO> listSearchResponseDTOS = manualQuerydslRepository.findByTitleOrContent(query, pageable);

        if (listSearchResponseDTOS.isEmpty()) {

            log.info("이용자가 검색한 제목 혹은 내용의 일치하는 게시글이 존재하지 않습니다! 204 Code와 함께 \"검색 결과 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (listSearchResponseDTOS.isEmpty()) 끝

        log.info("이용자가 검색한 내용의 제목 혹은 게시글을 찾았습니다! 200 Code와 함께 \"검색 성공\" 반환 하겠습니다!");

        return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), listSearchResponseDTOS);

    } // contentTitleSearch(String query, Pageable pageable) 끝


    /**
     * TAG로 게시물 검색
     * @param tagContent - 이용자가 검색 요청한 제목 혹은 내용 일부분 검색어
     * @return DefaultResponse<Page<ManualListSearchResponseDTO>> - 조회 된 결과를 DTO에 맞게 값을 넣어 Paging 처리를 한 뒤 반환
     * @see ""
     */

    @Override
    public DefaultResponse<Page<ManualListTagContentSearchResponseDTO>> contentTagSearch(String tagContent, Pageable pageable) {

        log.info("ManualService의 contentTagSearch(String tagContent, Pageable pageable)가 동작 하였습니다!");
        log.info("ManualController에서 넘겨 받은 요청 값 확인 : " + "검색 요청 글 제목 : " + tagContent  + "," + "페이징 요청 값 : " + pageable.toString());
        log.info("DB를 통해 이용자가 요청한 게시글 존재 여부를 찾아 보겠습니다!");

        Page<ManualListTagContentSearchResponseDTO> listTagContentSearchResponseDTOS = manualQuerydslRepository.findByTag(tagContent, pageable);

        if (listTagContentSearchResponseDTOS.isEmpty()) {

            log.info("이용자가 검색한 TAG의 일치하는 게시글이 존재하지 않습니다! 204 Code와 함께 \"검색 결과 없음\" 반환 하겠습니다!");

            return DefaultResponse.response(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessageKo(), ResponseCode.NO_CONTENT.getMessageEn());

        } // if (listTagContentSearchResponseDTOS.isEmpty()) 끝

        log.info("이용자가 검색한 내용의 제목 혹은 게시글을 찾았습니다! 200 Code와 함께 \"검색 성공\" 반환 하겠습니다!");

        return DefaultResponse.response(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessageKo(), ResponseCode.SUCCESS.getMessageEn(), listTagContentSearchResponseDTOS);

    } // contentTagSearch(String tagContent, Pageable pageable) 끝
} // class 끝

