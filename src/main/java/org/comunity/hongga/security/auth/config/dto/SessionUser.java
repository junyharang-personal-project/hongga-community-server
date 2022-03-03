package org.comunity.hongga.security.auth.config.dto;

import lombok.Getter;
import org.comunity.hongga.model.entity.member.Member;

import java.io.Serializable;

/**
 * 소셜 로그인을 통해 인증된 이용자 정보 저장 Class
 * 참고 사항
 * User클래스 내에 세션 정보를 가질 수 없는 이유
 * SessionUser 클래스의 이유
 *
 * User 클래스에 Session을 저장하려는 경우, User 클래스에 직렬화를 하지 않았기 때문에 오류가 발생한다.
 * User 클래스에 직렬화 코드를 넣는다면 ?
 * User 클래스는 Entity 클래스이기 때문에 문제가 발생할 가능성이 높다.
 * Entity 클래스는 언제 다른 엔티티와 관계가 형성될 지 모르기 때문이다.
 * \@OneToMany, \@ManyToMany 등 자식 엔티티를 갖고 있다면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다.
 * 직렬화 기능을 가진 세션 DTO를 하나 추가로 만드는 것이 이후 운영 및 유지보수에 도움이 된다.
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.03.02 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.03.02 최초 작성
 * @See "https://seokr.tistory.com/811"
 * @see <a href=""></a>
 */

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {

        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();

    } // SessionUser(Member member) 끝

} // class 끝
