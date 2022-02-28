package org.comunity.hongga.controller.manual;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.ManualTitleSearchRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDeleteResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.dto.response.manual.ManualListSearchResponseDTO;
import org.comunity.hongga.service.manual.ManualServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 사용 설명서 관련 Router Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 *    주니하랑, 1.1.0, 2022.02.18 목록 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.0, 2022.02.18 목록 조회, 상세 조회 동적 Query용 Query dsl 대신 JPQL로 변경으로 인한 manualListSearch() 반환 Type 변경
 *    주니하랑, 1.2.1, 2022.02.18 글 등록 부분 매개 변수 부분 수정, URI 수정
 *    주니하랑, 1.2.2, 2022.02.19 게시글 수정 기능 구현
 *    주니하랑, 1.2.3, 2022.02.20 게시글 삭제 기능 구현
 *    주니하랑, 1.2.4, 2022.02.20 회원 등급 추가 및 RESTFul API에 맞춘 URI 수정
 *    주니하랑, 1.3.0, 2022.02.21 사진 등록으로 인한 Refactoring
 *    주니하랑, 1.3.1, 2022.02.28 제목 검색 기능 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 주니하랑, 1.3.1, 2022.02.28 제목 검색 기능 구현
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j @Api(tags = {"사용 설명서 관련 API"}) @ApiOperation(value = SwaggerApiInfo.POSTING)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION_PATERNAL) public class ManualController {

    private final ManualServiceImpl manualService;


    @ApiOperation(value = SwaggerApiInfo.WRITE_POSTS, notes = "사용 설명서 등록 서비스 입니다.")
    @ApiParam(name = "memberNo", value = "작성자의 고유 번호를 통해 게시글을 등록 합니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.등록 성공 \n 2. 등록 실패 \n 3.Token Error")})

    @PostMapping("manual") public ResponseEntity<DefaultResponse<Long>> writeManual(
            @Valid @RequestBody ManualWriteRequestDTO systemManualWriteDTO, @RequestParam("memberNo") Long memberNo) {

        log.info("ManualController가 동작 하였습니다!");
        log.info("writeManual(@Valid @ResponseBody SystemManualWriteDTO systemManualWriteDTO)가 호출 되었습니다!");

        log.info("systemManualService.writeSystemManual(systemManualWriteDTO)를 호출하겠습니다!");

        return new ResponseEntity<>(manualService.writeManual(systemManualWriteDTO, memberNo), HttpStatus.OK);
    } // writeManual(@Valid @RequestBody SystemManualWriteRequestDTO systemManualWriteDTO, @RequestParam ("memberId") Long memberId) 끝



    @ApiOperation(value = SwaggerApiInfo.GET_POSTS_LIST, notes = "사용 설명서 전체 조회(목록) 서비스 입니다. \t\n 가족 간에 사용하는 물건에 대해 사용 설명서 모두 목록으로 조회합니다. \n 필수 : 작성자(닉네임), 제목, 작성일, 수정일")
    @ApiParam(name = "pageable", value = "페이징 처리를 위한 객체를 매개 변수로 줍니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.조회 성공 \n 2.데이터 없음 \n 3.Token Error")})

    @GetMapping("manual") public ResponseEntity<DefaultResponse<Page<ManualListSearchResponseDTO>>> manualListSearch (
            @PageableDefault Pageable pageable) {

        log.info("ManualController가 동작 하였습니다!");
        log.info("manualListSearch (@PageableDefault Pageable pageable)가 호출 되었습니다!");

        log.info("manualService.manualListSearch(pageable, memberNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(manualService.manualListSearch(pageable), HttpStatus.OK);

    } // manualListSearch (@PageableDefault Pageable pageable, @PathVariable("memberNo") Long memberNo) 끝


    @ApiOperation(value = SwaggerApiInfo.GET_POSTS_ONE_THING, notes = "사용 설명서 상세 조회 서비스 입니다. \t\n 가족 간에 사용하는 물건에 대해 사용 설명서 한 건에 대해 상세 조회합니다. \n 필수 : 작성자(닉네임), 메뉴얼 게시글 모든 내용")
    @ApiParam(name = "manualNo, memberNo", value = "상세 조회 하고자 하는 게시글 번호와 작성자와 다른 이용자가 조회를 했을 때 조회수 증가를 위한 이용자 고유 번호를 매개 변수로 주입 합니다.", readOnly = true)
        @ApiResponses(value = { @ApiResponse(code=200, message = "1.조회 성공 \n 2.데이터 없음 \n 3.Token Error")})

     // TODO - 상세 조회 시 회원 정보가 모두 나오지 않게 하고, 닉네임만 나오게 처리 필요

    @GetMapping("manual/{manualNo}") public ResponseEntity<DefaultResponse<List<ManualDetailResponseDTO>>> manualDetailSearch (
            @PathVariable("manualNo") Long manualNo) {

        log.info("ManualController가 동작 하였습니다!");
        log.info("manualDetailSearch (@PathVariable(\"manualNo\") Long manualNo)가 호출 되었습니다!");

        log.info("manualService.manualDetailSearch(manualNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(manualService.manualDetailSearch(manualNo), HttpStatus.OK);

    } // manualDetailSearch (@PathVariable("manualNo") Long manualNo) 끝


    @ApiOperation(value = SwaggerApiInfo.MODIFY_POSTS, notes = "사용 설명서 수정 서비스 입니다.")
    @ApiParam(name = "manualNo, memberNo", value = "수정 하고자 하는 게시글의 고유 번호와 수정 요청한 이용자 고유 번호 입니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.수정 성공 \n 2.데이터 없음 \n 3.Token Error")})

    @PatchMapping("manual/{manualNo}") public ResponseEntity<DefaultResponse<Long>> updateManual (
            @Valid @RequestBody ManualUpdateRequestDTO manualUpdateRequestDTO,
            @PathVariable("manualNo") Long manualNo,
            @RequestParam("memberNo") Long memberNo) {

        log.info("ManualController가 동작 하였습니다!");
        log.info("updateManual ( @Valid @RequestBody ManualUpdateRequestDTO manualUpdateRequestDTO, @PathVariable(\"manualNo\") Long manualNo,@RequestParam(\"memberNo\") Long memberNo)가 호출 되었습니다!");

        log.info("요청으로 들어온 값 \n 수정 내용 : " + manualUpdateRequestDTO.toString() + "\n 수정할 게시글 고유 번호 : " + manualNo.toString() + "\n 수정을 요청한 회원 고유 번호 : " + memberNo.toString());

        log.info("manualService.updateManual(manualUpdateRequestDTO, manualNo, manualNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(manualService.updateManual(manualUpdateRequestDTO, manualNo, memberNo), HttpStatus.OK);

    } // updateManual ( @Valid @RequestBody ManualUpdateRequestDTO manualUpdateRequestDTO, @PathVariable("manualNo") Long manualNo,@RequestParam("memberNo") Long memberNo) 끝


    @ApiOperation(value = SwaggerApiInfo.DELETE_POSTS, notes = "사용 설명서 삭제 서비스 입니다.")
    @ApiParam(name = "manualNo, memberNo", value = "삭제 대상 게시물 고유 번호와 요청 이용자 고유 번호 입니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.삭제 성공 \n 2.삭제 실패\n 1) 해당 게시글 작성자 삭제 요청자 불일치 \n 2) 삭제 요청한 게시글 DB 미 존재 \n 3.데이터 없음 \n 4.Token Error")})

    @DeleteMapping("/manual/{manualNo}") public ResponseEntity<DefaultResponse<ManualDeleteResponseDTO>> deleteManual (
            @PathVariable("manualNo") Long manualNo,
            @RequestParam("memberNo") Long memberNo
    ) {

        log.info("ManualController가 동작 하였습니다!");
        log.info("deleteManual (@PathVariable(\"manualNo\") Long manualNo, @RequestParam(\"memberNo\") Long memberNo) 가 호출 되었습니다!");

        log.info("요청으로 들어온 값 삭제 게시글 번호" + manualNo.toString() + "\n 삭제를 요청한 회원 고유 번호 : " + memberNo.toString());

        return new ResponseEntity<>(manualService.deleteManual(manualNo, memberNo), HttpStatus.OK);

    } // deleteManual (@PathVariable("manualNo") Long manualNo, @RequestParam("memberNo") Long memberNo) 끝


    @ApiOperation(value = SwaggerApiInfo.TITLE_SEARCH, notes = "사용 설명서 제목 검색 서비스 입니다.")
    @ApiParam(name = "manualSearchRequestDTO, pageable", value = "이용자가 검색을 위해 입력한 게시글 제목과 Paging 처리를 위한 객체 입니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.검색 성공 \n 2.데이터 없음 \n \n 3.Token Error")})

    @GetMapping("/manual/search") public ResponseEntity<DefaultResponse<Page<ManualListSearchResponseDTO>>> manualTitleSearch (
            @RequestParam("query") String title,
            @PageableDefault (sort = "manualNo", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        log.info("ManualController가 동작 하였습니다!");
        log.info("manualTitleSearch (@RequestParam(\"query\") String title, @PageableDefault (sort = \"manualNo\", direction = Sort.Direction.DESC, size = 10) Pageable pageable) 가 호출 되었습니다!");

        log.info("요청으로 들어온 검색 요청 제목 " + title + "\n 요청 페이징 처리 : " + pageable.toString());

        return new ResponseEntity<>(manualService.titleSearch(title, pageable), HttpStatus.OK);

    } // manualTitleSearch (@RequestParam("query") String title, @PageableDefault (sort = "manualNo", direction = Sort.Direction.DESC, size = 10) Pageable pageable) 끝
} // class 끝
