package org.comunity.hongga.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.constant.ServiceURIVersion;
import org.comunity.hongga.constant.SwaggerApiInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * File upload Router
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.22 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.22 최초 작성
 * @See "코드로 배우는 스프링 부트 웹 프로젝트 P.401"
 * @see <a href=""></a>
 */

@Slf4j @RequiredArgsConstructor @Api(tags = {"파일 업로드 관련 API"}) @ApiOperation(value = SwaggerApiInfo.FILE_UPLODER)
@RestController  @RequestMapping(ServiceURIVersion.NOW_VERSION_GUEST) public class UploadController {

    @Value("${org.comunity.hongga.upload.path") private String uploadPath;

    @ApiOperation(value = SwaggerApiInfo.FILE_UPLODER, notes = "이미지, 동영상 등 파일 업로드를 처리하는 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "각 서비스에 파일을 업로드 할 수 있는 서비스 입니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.등록 성공 \n 2. 등록 실패 \n 3.Token Error")})

    @PostMapping("/upload") public void uploadFile(MultipartFile[] multipartFiles) {

        log.info("파일 업로드 시도가 발생 하였습니다! \n UploadController가 동작 하였습니다! \n uploadFile(MultipartFile[] multipartFiles)가 호출 되었습니다!");

        log.info("요청으로 들어온 파일들을 반복하여 처리 하겠습니다!");

        for (MultipartFile uploadFile : multipartFiles) {

            // 일단 이미지 파일만 처리

            log.info("요청 업로드 파일이 Image 형식 인지 검사 하겠습니다!");

            if (uploadFile.getContentType().startsWith("image") == false) {

                log.info("요청된 업로드 파일이 Image 형식이 아닙니다! \n 현재 Version에서는 Image 처리만 가능 합니다!");

                return;

            } // if (uploadFile.getContentType().startsWith("image") == false) 끝

            log.info("이용자의 브라우저가 IE나, Edge일 경우 업로드 파일에 전체 경로가 전달 되기 때문에 해당 내용 처리 하겠습니다!");

            String originalFilename = uploadFile.getOriginalFilename();
            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);

            log.info("요청 자료의 파일 이름은 " + fileName + "입니다!");

            log.info("날짜 별 정리를 위해 날짜 폴더 생성 작업을 처리 하겠습니다!");

            String folderPath = makeFolder();

            log.info("파일 별 유니크한 이름으로 저장을 위해 UUID 생성 하겠습니다!");

            String uuid = UUID.randomUUID().toString();

            log.info("서버에 파일을 저장 할 당시에 이름을 생성하겠습니다. 구분은 언더바(_)로 처리 합니다!");

            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {

                uploadFile.transferTo(savePath);

            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            } // try-catch 끝
        } // for (MultipartFile uploadFile : multipartFiles) 끝
    } // uploadFile(MultipartFile[] multipartFiles) 끝

    @ApiOperation(value = SwaggerApiInfo.FILE_UPLODER, notes = "이미지, 동영상 등 파일 업로드된 파일을 보여주는 서비스 입니다.")
    @ApiParam(name = "MemberSignUpDTO", value = "각 서비스에 업로드 된 파일을 볼 수 서비스 입니다.", readOnly = true)
    @ApiResponses(value = { @ApiResponse(code=200, message = "1.출력 성공 \n 2.출력 실패 \n 3.Token Error")})

    @GetMapping("/display") public ResponseEntity<byte[]> getFile(String fileName) {

        log.info("UploadController의 getFile(String fileName) 호출 되었습니다!");

        ResponseEntity<byte[]> result = null;

        try {

            log.info("요청으로 들어온 파일 이름을 디코딩 하겠습니다!");

            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            log.info("요청 파일 이름 : " + srcFileName + " 입니다!");

            File file = new File(uploadPath + File.separator + srcFileName);

            log.info("파일 : " + file + " 입니다!");

            HttpHeaders httpHeaders = new HttpHeaders();

            log.info("요청 Header에 MIME Type 처리 하겠습니다!");

            httpHeaders.add("Content-Type", Files.probeContentType(file.toPath()));

            log.info("File Data 처리 하겠습니다!");

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), httpHeaders, HttpStatus.OK);

        } catch (IOException e) {

            e.printStackTrace();
            log.error(e.getMessage());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } // try-catch 끝

        return result;

    } // class 끝

    private String makeFolder() {

        log.info("UploadController의 makeFolder()가 호출 되었습니다!");

        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = format.replace("/", File.separator);

        log.info("날짜 별로 생성될 폴더 생성 하겠습니다!");

        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) { uploadPathFolder.mkdirs(); }

        return folderPath;
    } // makeFolder() 끝
} // class 끝
