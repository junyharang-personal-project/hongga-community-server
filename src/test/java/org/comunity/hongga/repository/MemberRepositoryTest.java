package org.comunity.hongga.repository;

import org.comunity.hongga.model.entity.member.Authority;
import org.comunity.hongga.model.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test public void 회원_가입() {

        // given
        // 회원 등급
        Authority authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        Member memberOne = Member.builder()

                .email("hongga1@hongga.com")
                .password("1234")
                .name("주니")
                .nickname("주니하랑")
                .phoneNumber("010-3944-3893")
                .authorities(Collections.singleton(authority))
                .build();

        // when
        Member saveMember = memberRepository.save(memberOne);

        // then
        System.out.println(assertThat(memberOne).isEqualTo(saveMember));
    } // 회원_가입() 끝

} // class 끝