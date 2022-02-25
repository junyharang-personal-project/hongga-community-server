package org.comunity.hongga.controller.manual;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.manual.comment.ManualCommentWriteRequestDTO;
import org.comunity.hongga.model.dto.response.manual.comment.ManualCommentWriterResponseDTO;
import org.comunity.hongga.service.manual.comment.ManualCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 메뉴얼 댓글 Router
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.26 최초 작성
 *    * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.26 최초 작성
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

} // class 끝
