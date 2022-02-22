package org.comunity.hongga.constant;

/**
 * Service별 Swagger내용 관련 인터페이스
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See "코드로 배우는 스프링 부트 웹 프로젝트"
 * @see <a href=""></a>
 */

public interface SwaggerApiInfo {

    String POSTING = "게시판 서비스";
    String GET_POSTS_LIST = "게시글 목록 조회";
    String GET_POSTS_ONE_THING = "게시글 단건 조회";
    String WRITE_POSTS = "게시글 등록";
    String DELETE_POSTS = "게시글 삭제";
    String MODIFY_POSTS = "게시글 수정";
    String POST_LIKE = "게시글 좋아요";

    String COMMENT = "댓글 서비스";
    String WRITE_COMMENT = "댓글 등록";
    String GET_COMMENT_LIST = "댓글 목록 조회";
    String DELETE_COMMENT = "댓글 삭제";
    String MODIFY_COMMENT = "댓글 수정";
    String COMMENT_LIKE = "댓글 좋아요";

    String AUTHORIZE = "인증 서비스";
    String SIGN_IN = "로그인 서비스";
    String SIGN_UP = "회원 가입 서비스";

    String REPLACE_TOKEN = "JWT 재발행 서비스";

} // interface 끝
