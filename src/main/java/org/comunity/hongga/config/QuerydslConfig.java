package org.comunity.hongga.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration public class QuerydslConfig {

    @PersistenceContext private EntityManager entityManager;

    @Bean public JPAQueryFactory jpaQueryFactory() {

        log.info("QuerydslConfig가 동작 하였습니다!");
        log.info("Bean으로 등록 될 jpaQueryFactory(EntityManager entityManager)가 호출 되었습니다!");

        return new JPAQueryFactory(entityManager);
    } // jpaQueryFactory(EntityManager entityManager) 끝

} // class 끝
