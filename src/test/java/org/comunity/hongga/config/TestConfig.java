package org.comunity.hongga.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.comunity.hongga.repository.manual.querydsl.ManualQuerydslRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Querydsl Test를 위한 설정
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

@TestConfiguration public class TestConfig {

    @PersistenceContext private EntityManager entityManager;

    @Bean public JPAQueryFactory jpaQueryFactory() { return new JPAQueryFactory(entityManager); } // jpaQueryFactory() 끝
    @Bean public ManualQuerydslRepository manualQuerydslRepository(){ return new ManualQuerydslRepository(jpaQueryFactory(), entityManager); } // manualQuerydslRepository() 끝

} // class 끝
