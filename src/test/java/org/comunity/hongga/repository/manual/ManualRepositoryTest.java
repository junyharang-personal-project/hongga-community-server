package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;
import org.comunity.hongga.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Manual Repository Test Code
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.16 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

@SpringBootTest
public class ManualRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired ManualRepository manualRepository;

    @Test public void 메뉴얼_등록() {

        //given
        String picture = "sdoijgoij.jpg";
        String aboutMe = "안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!";

            Member testMember = Member.builder()
                    .email("test@hongga.com")
                    .password("hong123456")
                    .name("홍주니")
                    .nickname("주니하랑")
                    .phoneNumber("010-3939-4848")
                    .picture(picture)
                    .aboutMe(aboutMe)
                    .grade(MemberGrade.ADMIN)
                    .activated(true)
                    .build();

            Member adminMember = memberRepository.save(testMember);

            Manual manual = Manual.builder()
                    .writer(adminMember)
                    .title("Synology NAS 사용법")
                    .content("이 제품은 이렇게 사용 하시면 삶의 질이 향상 되요!")
                    .build();

        // when
        Manual saveManual = manualRepository.save(manual);

        // then
        assertThat(manual).isEqualTo(saveManual);

    } // 메뉴얼_등록() 끝

} // class 끝