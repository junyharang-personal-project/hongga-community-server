package org.comunity.hongga.repository.manual.querydsl;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.config.JpaAuditingConfig;
import org.comunity.hongga.config.TestConfig;
import org.comunity.hongga.model.dto.response.manual.ManualListResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 사용 설명서 관련 Querydsl Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.17 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.1, 2022.02.17 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@Slf4j
@RunWith(SpringRunner.class) @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) @Import(TestConfig.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaAuditingConfig.class))
public class ManualQuerydslRepositoryTest {

    @Autowired private ManualQuerydslRepository manualQuerydslRepository;

//    @Test public void 전체_목록_조회() {
//
//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("manualNo").descending());
//
//        Page<ManualListResponseDTO> result = manualQuerydslRepository.findAllWithFetchJoin(pageRequest);
//
//        result.stream()
//                .forEach(manualValue ->
//                        log.info("메뉴얼 번호 : {}, 글 제목 : {}, 등록일 : {}, 수정일 : {}, 작성자 : {}", manualValue.getManualNo(), manualValue.getTitle(), manualValue.getRegisterDate(), manualValue.getModifyDate(), manualValue.getNickname()));
//
//        assertThat(result.get().count()).isEqualTo(10);
//
//    } // 전체_목록_조회() 끝

} // class 끝