package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberRole;
import org.comunity.hongga.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Manual Repository Test Code
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.15 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.15 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@SpringBootTest
public class MemberRepositoryTest {

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
                .grade(MemberRole.ROLE_GUEST)
                .build();

        //when
        Member saveMember = memberRepository.save(testMember);

        //then
        assertThat(testMember).isEqualTo(saveMember);

    } // 회원가입() 끝

} // clas끝 끝