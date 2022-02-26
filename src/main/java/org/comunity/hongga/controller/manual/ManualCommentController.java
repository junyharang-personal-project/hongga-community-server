package org.comunity.hongga.controller.manual;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentUpdateRequestDTO;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentDeleteResponseDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentListSearchResponseDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentWriterResponseDTO;
import org.comunity.hongga.service.manual.comment.ManualCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 메뉴얼 댓글 Router
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 *    주니하랑, 1.0.1, 2022.02.26 댓글 작성, 목록 조회 구현
 *    주니하랑, 1.0.2, 2022.02.26 댓글 수정 기능 구현
 *    주니하랑, 1.0.3, 2022.02.26 댓글 삭제 기능 구현
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.3, 2022.02.26 댓글 삭제 기능 구현
 * @See ""
 * @see <a href=""></a>
 */


@RequiredArgsConstructor @Slf4j @Api(tags = {"사용 설명서 댓글 관련 API"}) @ApiOperation(value = SwaggerApiInfo.COMMENT)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION_PATERNAL) public class ManualCommentController {

    private final ManualCommentService commentService;

    @ApiOperation(value = SwaggerApiInfo.WRITE_COMMENT, notes = "사용 설명서 댓글 등록 서비스 입니다.")
    @ApiParam(name = "writeRequestDTO, manualNo, memberNo", value = "등록 할 내용이 들어 있는 객체, 등록할 댓글이 의존할 게시글 고유 번호, 등록을 요청한 이용자 고유 번호", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.등록 성공 \n 2. 등록 실패 \n 3.Token Error")})

    @PostMapping("/manual/comment/{manualNo}")
    public ResponseEntity<DefaultResponse<ManualCommentWriterResponseDTO>> writeComment (
            @RequestBody ManualCommentWriteRequestDTO writeRequestDTO, @PathVariable("manualNo") Long manualNo, @RequestParam("memberNo") Long memberNo) {

        log.info("ManualCommentController의 writeComment @RequestBody ManualCommentWriteRequestDTO writeRequestDTO, @PathVariable(\"manualNo\") Long manualNo, @RequestParam(\"memberNo\") Long memberNo)가 호출 되었습니다!");

        log.info("commentService.writeManualComment(writeRequestDTO, manualNo, memberNo)를 호출 하겠습니다!");

        return new ResponseEntity<>(commentService.writeManualComment(writeRequestDTO, manualNo, memberNo), HttpStatus.OK);

    } // writeComment @RequestBody ManualCommentWriteRequestDTO writeRequestDTO, @PathVariable("manualNo") Long manualNo, @RequestParam("memberNo") Long memberNo) 끝

    @ApiOperation(value = SwaggerApiInfo.GET_COMMENT_LIST, notes = "사용 설명서 댓글 목록 조회 서비스 입니다.")
    @ApiParam(name = "manualNo, pageable", value = "등록할 댓글이 의존할 게시글 고유 번호, 페이징 처리를 위한 객체", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.조회 성공 \n 2. 조회 실패 \n 3.Token Error")})

    @GetMapping("/manual/comment/{manualNo}") public ResponseEntity<DefaultResponse<Page<ManualCommentListSearchResponseDTO>>> manualCommentListSearch (
            @PathVariable("manualNo") Long manualNo,
            @PageableDefault(sort = "no", direction = Sort.Direction.DESC)Pageable pageable) {     // 최신 댓글 내림차순으로 페이지 당 10개씩 조회

        log.info("ManualCommentController의 manualCommentListSearch (@PathVariable(\"manualNo\") Long manualNo,@PageableDefault(sort = \"no\", direction = Sort.Direction.DESC)Pageable pageable)가 호출 되었습니다!");

        log.info("manualListSearch(manualNo, pageable)를 호출 하겠습니다!");

        return new ResponseEntity<>(commentService.manualListSearch(manualNo, pageable), HttpStatus.OK);

    } // manualCommentListSearch (@PathVariable("manualNo") Long manualNo,@PageableDefault(sort = "no", direction = Sort.Direction.DESC)Pageable pageable)

    @ApiOperation(value = SwaggerApiInfo.MODIFY_POSTS, notes = "사용 설명서 댓글 수정 서비스 입니다.")
    @ApiParam(name = "manualNo, manualCommentNo, memberNo, manualCommentUpdateRequestDTO", value = "수정할 댓글이 의존할 게시글 고유 번호, 수정 대상 댓글 고유 번호, 수정 요청 이용자 고유 번호, 수정 내용이 담긴 DTO Class", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.수정 성공 \n 2.수정 실패 \n 3.Token Error")})

    @PatchMapping("/manual/comment/{manualNo}") public ResponseEntity<DefaultResponse<Long>> updateManualComment (
            @PathVariable("manualNo") Long manualNo,
            @RequestParam("manualCommentNo") Long manualCommentNo,
            @RequestParam("memberNo") Long memberNo,
            @Valid @RequestBody ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO) {

        log.info("ManualCommentController의 updateManualComment (@PathVariable(\"manualNo\") Long ManualNo, @RequestParam(\"manualCommentNo\") Long manualCommentNo,@RequestParam(\"memberNo\") Long memberNo, @Valid @RequestBody ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO)가 호출 되었습니다!");

        log.info("manualListSearch(manualNo, pageable)를 호출 하겠습니다!");

        return new ResponseEntity<>(commentService.updateManualComment(manualCommentUpdateRequestDTO, manualNo, manualCommentNo, memberNo), HttpStatus.OK);

    } // updateManualComment (@PathVariable("manualNo") Long ManualNo, @RequestParam("manualCommentNo") Long manualCommentNo,@RequestParam("memberNo") Long memberNo, @Valid @RequestBody ManualCommentUpdateRequestDTO manualCommentUpdateRequestDTO) 끝

    @ApiOperation(value = SwaggerApiInfo.DELETE_COMMENT, notes = "사용 설명서 댓글 삭제 서비스 입니다.")
    @ApiParam(name = "manualNo, manualCommentNo, memberNo, manualCommentUpdateRequestDTO", value = "수정할 댓글이 의존할 게시글 고유 번호, 수정 대상 댓글 고유 번호, 수정 요청 이용자 고유 번호, 수정 내용이 담긴 DTO Class", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.삭제 성공 \n 2.삭제 실패 \n 3.Token Error")})

    @DeleteMapping("manual/comment/{manualNo}") public ResponseEntity<DefaultResponse<ManualCommentDeleteResponseDTO>> deleteManualComment(
            @PathVariable("manualNo") Long manualNo,
            @RequestParam("manualCommentNo") Long manualCommentNo,
            @RequestParam("memberNo") Long memberNo) {

        log.info("ManualCommentController의 manualCommentDelete(@PathVariable(\"manualNo\") Long manualNo,@RequestParam(\"manualCommentNo\") Long manualCommentNo, @RequestParam(\"memberNo\") Long memberNo)가 호출 되었습니다!");
        log.info("manualListSearch(manualNo, pageable)를 호출 하겠습니다!");

        log.info("요청으로 들어온 값 삭제 대상 댓글 게시글 번호" + manualNo.toString() + "\n 삭제 대상 댓글 고유 번호 : " + manualCommentNo.toString() +"\n 삭제를 요청한 회원 고유 번호 : " + memberNo.toString());

        return new ResponseEntity<>(commentService.deleteManualComment(manualNo, manualCommentNo, memberNo), HttpStatus.OK);

    } // manualCommentDelete(@PathVariable("manualNo") Long manualNo,@RequestParam("manualCommentNo") Long manualCommentNo, @RequestParam("memberNo") Long memberNo) 끝
} // class 끝
