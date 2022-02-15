package org.comunity.hongga.repository.manual;

import lombok.extern.slf4j.Slf4j;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;
import org.comunity.hongga.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class manualRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test void 회원가입() {

        String email = "test@hongga.com";
        String password = "hong123456";
        String name = "홍주니";
        String nickName = "주니하랑";
        String phoneNumber = "010-3939-4848";
        String picture = "sdoijgoij.jpg";
        String aboutMe = "안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!";

        //given
        Member testMember = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickName)
                .phoneNumber(phoneNumber)
                .picture(picture)
                .aboutMe(aboutMe)
                .activated(true)
                .grade(MemberGrade.GUEST)
                .build();

        //when
        Member saveMember = memberRepository.save(testMember);

        //then
        assertThat(testMember).isEqualTo(saveMember);

    } // 회원가입() 끝

} // clas끝 끝