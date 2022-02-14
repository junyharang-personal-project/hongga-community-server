package org.comunity.hongga.controller.manual;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.DefaultResponse;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.comunity.hongga.service.manual.SystemManualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor @Slf4j @Api(tags = {"로그인 관련 서비스"}) @ApiOperation(value = SwaggerApiInfo.POSTING)
@RestController @RequestMapping(ServiceURIVersion.NOW_VERSION) public class SystemManualController {

    private final SystemManualService systemManualService;

    @ApiOperation(value = SwaggerApiInfo.WRITE_POSTS, notes = "시스템 메뉴얼 등록 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "가족 간에 사용하는 물건에 대해 사용 설명서를 등록합니다. \n 필수 : Tag를 제외한 모든 항목", readOnly = true)

    @PostMapping("/system-manual") public ResponseEntity<DefaultResponse<SystemManualWriteDTO>> writeManual(
            @Valid @ResponseBody SystemManualWriteDTO systemManualWriteDTO) {

        log.info("SystemManualController가 동작 하였습니다!");
        log.info("writeManual(@Valid @ResponseBody SystemManualWriteDTO systemManualWriteDTO)가 동작 하였습니다!");

        log.info("systemManualService.writeSystemManual(systemManualWriteDTO)를 호출하겠습니다!");

        return new ResponseEntity<>(systemManualService.writeSystemManual(systemManualWriteDTO), HttpStatus.OK);
    }

} // class
