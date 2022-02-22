package org.comunity.hongga.model.dto.request.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 메뉴얼 사진 관련 DTO
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.21 최초 작성
 *    주니하랑, 1.0.1, 2022.02.21 이미지 처리를 위한 Method 추가
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.21 이미지 처리를 위한 Method 추가
 * @See ""
 * @see <a href="코드로 배우는 스프링 부트 웹 프로젝트 P.419"></a>
 */


@Data @NoArgsConstructor @AllArgsConstructor @Builder @Slf4j
public class ManualImageDTO {

    private String uuid;

    private String imgName;

    private String path;


    public String getImageURL() {

        log.info("ManualImageDTO가 동작 하였습니다!");
        log.info("getImageURL()가 호출 되었습니다!");

        try {

            log.info("Manual Image 저장 위치를 encode하여 반환 시도 하겠습니다!");

            return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"UTF-8");

        } catch (UnsupportedEncodingException e) {

            log.info("Manual Image 저장 위치를 가져오지 못하여 catch절이 실행 되었습니다!");

            e.printStackTrace();
            log.error(e.getMessage());

        } // try-catch 끝

        return "";

    } // getImageURL() 끝

    public String getThumbnailURL() {

        log.info("ManualImageDTO가 동작 하였습니다!");
        log.info("getThumbnailURL()가 호출 되었습니다!");

        try{

            return URLEncoder.encode(path+"/s_"+uuid+"_"+imgName,"UTF-8");

        } catch (UnsupportedEncodingException e) {

            log.info("Manual Thumnail Image 저장 위치를 가져오지 못하여 catch절이 실행 되었습니다!");

            e.printStackTrace();
            log.error(e.getMessage());

        } // try-catch문 끝

        return "";

    } // getThumbnailURL() 끝
} // class 끝
