package org.comunity.hongga.repository;

import org.comunity.hongga.model.entity.member.Authority;
import org.comunity.hongga.model.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test public void 회원_가입() {

        // given
        // 회원 등급
        Authority authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        Member memberOne = Member.builder()

                .email("hongga1@hongga.com")
                .password(passwordEncoder.encode("1234"))
                .name("주니")
                .nickname("주니하랑")
                .phoneNumber("010-3944-3893")
                .authorities(Collections.singleton(authority))
                .activated(true)
                .aboutMe("저는 이 웹 서비스를 구축한 주니하랑 입니다! 우리 가족에게 항상 행복이 가득하길 바랍니다!")
                .build();

        // when
        Member saveMember = memberRepository.save(memberOne);

        // then
        System.out.println(assertThat(memberOne).isEqualTo(saveMember));
    } // 회원_가입() 끝

    @Test public void 회원_100명_생성_상세_조회() {

        // given
        System.out.println("다수 회원을 생성 합니다!");

        // 50번까지 FAMILY
        // 100번까지 GUEST

        IntStream.rangeClosed(1, 100).forEach(i -> {

            if (i <= 50) {
                Authority authority = Authority.builder()
                        .authorityName("ROLE_FAMILY")
                        .build();

                Member memberMany = Member.builder()

                        .email("whoami"+i+"@hongga.com")
                        .password(passwordEncoder.encode("1234"))
                        .name("손님" + i)
                        .nickname("손님이에영" + i)
                        .phoneNumber("010-3948-4934")
                        .activated(true)
                        .authorities(Collections.singleton(authority))
                        .activated(true)
                        .aboutMe("안녕하세요! 여기 참 멋진 웹 서비스네요!")
                        .build();

                // when
                Member saveManyFamily = memberRepository.save(memberMany);

                // then
                System.out.println(assertThat(memberMany).isEqualTo(saveManyFamily));
            } else {
                Authority authority = Authority.builder()
                        .authorityName("ROLE_GUEST")
                        .build();

                Member memberMany = Member.builder()

                        .email("whoami"+i+"@hongga.com")
                        .password(passwordEncoder.encode("1234"))
                        .name("손님" + i)
                        .nickname("손님이에영" + i)
                        .phoneNumber("010-3948-4934")
                        .activated(true)
                        .aboutMe("안녕하세요! 여기 참 멋진 웹 서비스네요!")
                        .authorities(Collections.singleton(authority))
                        .build();

                // when
                Member saveManyGuest = memberRepository.save(memberMany);


                // then
                System.out.println(assertThat(memberMany).isEqualTo(saveManyGuest));
            } // if-else 끝

        });

        System.out.println("===============================================================");
        System.out.println("회원 조회를 시작 합니다!");

        //given
        IntStream.rangeClosed(1, 100).forEach(i -> {

            String email = "whoami"+i+"@hongga.com";

            // when
            Member byEmail = memberRepository.findByEmail(email);

            // then
            System.out.println(assertThat(email).isEqualTo(byEmail));

        });

    } // 회원_상세_조회() 끝

} // class 끝