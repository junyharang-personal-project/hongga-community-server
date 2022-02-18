package org.comunity.hongga.controller.manual;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.service.manual.ManualService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용 설명서 관련 Router Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.1, 2022.02.18 글 등록 부분 매개 변수 부분 수정, URI 수정
 * </pre>
 *
 * @author 주니하랑
 * @version 1.2.1, 2022.02.18 글 등록 부분 매개 변수 부분 수정, URI 수정
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j @Api(tags = {"사용 설명서 관련 API"}) @ApiOperation(value = SwaggerApiInfo.POSTING)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION) public class ManualController {

    private final ManualService manualService;



    @ApiOperation(value = SwaggerApiInfo.WRITE_POSTS, notes = "사용 설명서 등록 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "가족 간에 사용하는 물건에 대해 사용 설명서를 등록합니다. \n 필수 : Tag를 제외한 모든 항목", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.등록 성공 \n 2. 등록 실패 \n 3.Token Error")})

    @PostMapping("/family/manual") public ResponseEntity<DefaultResponse<ManualWriteRequestDTO>> writeManual(
            @Valid @RequestBody ManualWriteRequestDTO systemManualWriteDTO, @RequestParam ("memberNo") Long memberNo) {

        log.info("SystemManualController가 동작 하였습니다!");
        log.info("writeManual(@Valid @ResponseBody SystemManualWriteDTO systemManualWriteDTO)가 호출 되었습니다!");

        log.info("systemManualService.writeSystemManual(systemManualWriteDTO)를 호출하겠습니다!");

        return new ResponseEntity<>(manualService.writeManual(systemManualWriteDTO, memberNo), HttpStatus.OK);
    } // writeManual(@Valid @RequestBody SystemManualWriteRequestDTO systemManualWriteDTO, @RequestParam ("memberId") Long memberId) 끝



    @ApiOperation(value = SwaggerApiInfo.GET_POSTS_LIST, notes = "사용 설명서 전체 조회(목록) 서비스 입니다. \t\n 가족 간에 사용하는 물건에 대해 사용 설명서 모두 목록으로 조회합니다. \n 필수 : 작성자(닉네임), 제목, 작성일, 수정일")
    @ApiParam(name = "Manual", value = "Manual 인스턴스 Type으로 반환합니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1. 조회 성공 \n 2. 데이터 없음 \n 3.Token Error")})

    @GetMapping("/family/manual") public ResponseEntity<DefaultResponse<Page<Manual>>> manualListSearch (
            @PageableDefault Pageable pageable) {

        log.info("SystemManualController가 동작 하였습니다!");
        log.info("manualListSearch (@PageableDefault Pageable pageable, @PathVariable(\"memberNo\") Long memberNo)가 호출 되었습니다!");

        log.info("manualService.manualListSearch(pageable, memberNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(manualService.manualListSearch(pageable), HttpStatus.OK);

    } // manualListSearch (@PageableDefault Pageable pageable, @PathVariable("memberNo") Long memberNo) 끝



    @ApiOperation(value = SwaggerApiInfo.GET_POSTS_ONE_THING, notes = "사용 설명서 상세 조회 서비스 입니다. \t\n 가족 간에 사용하는 물건에 대해 사용 설명서 한 건에 대해 상세 조회합니다. \n 필수 : 작성자(닉네임), 메뉴얼 게시글 모든 내용")
    @ApiParam(name = "Manual", value = "Manual 인스턴스 Type으로 반환합니다.", readOnly = true)
        @ApiResponses(value = { @ApiResponse(code=200, message = "1. 조회 성공 \n 2. 데이터 없음 \n 3.Token Error")})

    // TODO - 상세 조회 시 회원 정보가 모두 나오지 않게 하고, 닉네임만 나오게 처리 필요

    @GetMapping("/family/manual/{manualNo}") public ResponseEntity<DefaultResponse<Manual>> manualDetailSearch (@PathVariable("manualNo") Long manualNo) {

        log.info("SystemManualController가 동작 하였습니다!");
        log.info("manualDetailSearch (@PathVariable(\"manualNo\") Long manualNo)가 호출 되었습니다!");

        log.info("manualService.manualDetailSearch(manualNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(manualService.manualDetailSearch(manualNo), HttpStatus.OK);

    } // manualDetailSearch (@PathVariable("manualNo") Long manualNo) 끝



    @ApiOperation(value = SwaggerApiInfo.MODIFY_POSTS, notes = "사용 설명서 수정 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "가족 간에 사용하는 물건에 대해 사용 설명서 한 건에 대해 상세 조회합니다. \n 필수 : 작성자(닉네임), 메뉴얼 게시글 모든 내용", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1. 조회 성공 \n 2. 데이터 없음 \n 3.Token Error")})

    @PutMapping("/family/manual/{manualNo}") public ResponseEntity<DefaultResponse> updateManual (
            @Valid @RequestBody ManualUpdateRequestDTO manualUpdateRequestDTO,
            @PathVariable("manualNo") Long manualNo,
            @RequestParam("memberNo") Long memberNo) {

        log.info("SystemManualController가 동작 하였습니다!");
        log.info("updateManual ( @Valid @RequestBody ManualUpdateRequestDTO manualUpdateRequestDTO, @PathVariable(\"manualNo\") Long manualNo,@RequestParam(\"memberNo\") Long memberNo)가 호출 되었습니다!");

        log.info("manualService.updateManual(manualUpdateRequestDTO, manualNo, manualNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(manualService.updateManual(manualUpdateRequestDTO, manualNo, memberNo), HttpStatus.OK);

    } // updateManual ( @Valid @RequestBody ManualUpdateRequestDTO manualUpdateRequestDTO, @PathVariable("manualNo") Long manualNo,@RequestParam("memberNo") Long memberNo) 끝

} // class 끝
