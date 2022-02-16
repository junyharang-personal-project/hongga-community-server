package org.comunity.hongga.controller.manual;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.model.dto.request.manual.ManualWriteRequestDTO;
import org.comunity.hongga.service.manual.ManualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 사용 설명서 관련 Router Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@RequiredArgsConstructor @Slf4j @Api(tags = {"사용 설명서 관련 API"}) @ApiOperation(value = SwaggerApiInfo.POSTING)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION) public class ManualController {

    private final ManualService systemManualService;

    @ApiOperation(value = SwaggerApiInfo.WRITE_POSTS, notes = "사용 설명서 등록 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "가족 간에 사용하는 물건에 대해 사용 설명서를 등록합니다. \n 필수 : Tag를 제외한 모든 항목", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.등록 성공 \n 2. 등록 실패 \n 3.Token Error")})

    @PostMapping("/family/manual/{memberNo}") public ResponseEntity<DefaultResponse<ManualWriteRequestDTO>> writeManual(
            @Valid @RequestBody ManualWriteRequestDTO systemManualWriteDTO, @PathVariable ("memberNo") Long memberNo) {

        log.info("SystemManualController가 동작 하였습니다!");
        log.info("writeManual(@Valid @ResponseBody SystemManualWriteDTO systemManualWriteDTO)가 동작 하였습니다!");

        log.info("systemManualService.writeSystemManual(systemManualWriteDTO)를 호출하겠습니다!");

        return new ResponseEntity<>(systemManualService.writeManual(systemManualWriteDTO, memberNo), HttpStatus.OK);
    } // writeManual(@Valid @RequestBody SystemManualWriteRequestDTO systemManualWriteDTO, @RequestParam ("memberId") Long memberId) 끝

} // class
