package org.comunity.hongga.repository.manual;

import org.comunity.hongga.model.dto.response.manual.ManualDetailResponseDTO;
import org.comunity.hongga.model.entity.manual.Manual;
import org.comunity.hongga.model.entity.manual.ManualImage;
import org.comunity.hongga.model.entity.manual.ManualTag;
import org.comunity.hongga.model.entity.member.Member;
import org.comunity.hongga.model.entity.member.MemberGrade;
import org.comunity.hongga.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Manual Repository Test Code
 * <pre>
 * <b>History:/b>
 *    주니하랑, 1.0.0, 2022.02.16 최초 작성
 *    주니하랑, 1.0.2, 2022.02.18 게시물 목록 조회, 상세 조회 코드 구현
 *    주니하랑, 1.0.3, 2022.02.21 사진 관련 테스트 코드 구현
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.3, 2022.02.21 사진 관련 테스트 코드 구현
 * @See ""
 * @see <a href=""></a>
 */

@SpringBootTest
public class ManualRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired ManualRepository manualRepository;
    @Autowired ManualTagRepository manualTagRepository;
    @Autowired ManualImageRepository manualImageRepository;

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

        ManualTag tags = ManualTag.builder()
                .manual(saveManual)
                .tagContent("시놀로지")
                .build();

        ManualTag saveManualTag = manualTagRepository.save(tags);

        // then
        assertThat(tags).isEqualTo(saveManualTag);

    } // 메뉴얼_등록() 끝

    @Commit @Transactional
    @Test public void 여러_게시물_등록() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            String picture = "sdoijgoij.jpg";
            String aboutMe = "안녕하세요! 우리 가족에게 언제나 좋은 일만 가득하길 바랍니다!";

            Member testMember = Member.builder()
                    .email("test@hongga.com"+i)
                    .password("hong123456"+i)
                    .name("홍주니"+i)
                    .nickname("주니하랑"+i)
                    .phoneNumber("010-3939-4848")
                    .picture(picture)
                    .aboutMe(aboutMe)
                    .grade(MemberGrade.FAMILY)
                    .activated(true)
                    .build();

            Member familyMember = memberRepository.save(testMember);

            Manual manual = Manual.builder()

                    .writer(familyMember)
                    .title("Synology NAS 사용법"+i)
                    .content("이 제품은 이렇게 사용 하시면 삶의 질이 향상 되요!"+i)
                    .build();

            Manual saveManual = manualRepository.save(manual);

            ManualTag tags = ManualTag.builder()
                    .manual(saveManual)
                    .tagContent("시놀로지"+i)
                    .build();

            manualTagRepository.save(tags);

            // 사진 추가 코드
            // 1, 2, 3, 4 ..
            int cnt = (int) (Math.random() * 5) + 1;

            for(int count = 0; count < cnt; count++) {

                ManualImage manualImage = ManualImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .manual(manual)
                        .imgName("사진등록TEST"+count+".jpg")
                        .build();

                manualImageRepository.save(manualImage);

            } // for(int count = 0; count < cnt; count++) 끝

        });

    } // 여러_게시물_등록() 끝

    @Test public void 전체_게시물_조회() {

        System.out.println(" 1페이지의 10개 DATA를 조회 하겠습니다!");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("manualNo").descending());

        List<Object> arr = new ArrayList<>();

        Page<Manual> result = manualRepository.findAllWithFetchJoin(pageable);

        System.out.println(result);

        System.out.println("----------------------------------------------------");

        System.out.println("총 Page 수 : " + result.getTotalPages());

        System.out.println("전체 개수 : " + result.getTotalElements());

        System.out.println("현재 Page 번호 : " + result.getNumber());

        System.out.println("Page 당 Data 개 수 : " + result.getSize());

        System.out.println("다음 Page 존재 여부 : " + result.hasNext());

        System.out.println("현재 Page가 시작 Page인지 여부 : " + result.isFirst());

    } // 전체_게시물_조회()

@Transactional
@Test public void 게시물_상세_조회() {

        Long manualNo = 3L;

    List<Object[]> manual = manualRepository.findByManualDetail(manualNo);

        System.out.println("====================================================");

//        if (manual.isPresent()) {
//            ManualDetailResponseDTO result = manual.get();
//            System.out.println(result);
//        }
    } // 게시물_상세_조회() 끝

    @Test public void 게시물_수정() {

        // given
        Optional<Manual> result = manualRepository.findByManualNo(103L);

        if (result.isEmpty()) {
            System.out.println("DB에서 해당 자료를 찾아봤지만, 존재 하지 않습니다! 200 Code와 함께 \"내용 없음\" 반환 하겠습니다!");
        } // if (result.isEmpty()) 끝

        Manual manual = result.get();

        manual.changeTitle("Manual Reposiroty 수정 Test");
        manual.changeContent("수정 Test Code Test 중 입니다!");

        // when
        Manual saveManual = manualRepository.save(manual);


        // then
        assertThat(saveManual.toString()).isEqualTo(manual.toString());

    } // 게시물_수정() 끝

    @Test public void 게시글_삭제() {

        // given
        Optional<Manual> result = manualRepository.findByManualNo(98L);

        if (result.isEmpty()) {
            System.out.println("DB에서 해당 자료를 찾아봤지만, 존재 하지 않습니다! 200 Code와 함께 \"내용 없음\" 반환 하겠습니다!");
        } // if (result.isEmpty()) 끝

        // when
        manualRepository.deleteById(98L);


    } // 게시글_삭제() 끝

} // class 끝끝