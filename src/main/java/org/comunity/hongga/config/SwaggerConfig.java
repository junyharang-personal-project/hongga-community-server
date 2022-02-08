package org.comunity.hongga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * API 문서 관리 Swagger 설정
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See "코드로 배우는 스프링 부트 웹 프로젝트"
 * @see <a href=""></a>
 */

@EnableSwagger2 @EnableWebMvc @Configuration public class SwaggerConfig {

    @Bean public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(this.apiInfo());

    } // api() 끝

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("홍가 Community Project API Document")
                .version("1.0.0")
                .description(
                        "<h1> 우리 가족 커뮤니티 웹 서비스!! </h1> \t\n\t\n " +
                                "<h2> 회원 등급 안내 </h2>" +
                                "/guest ==> 비회원, 가족, 관리자\n" +
                                "/family ==> 가족, 관리자\n" +
                                "/admin ==> 관리자"
                ).build();

    } // apiInfo() 끝

} // class 끝
