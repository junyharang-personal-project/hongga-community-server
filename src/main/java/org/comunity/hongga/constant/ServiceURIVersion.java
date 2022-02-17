package org.comunity.hongga.constant;

/**
 * Hard Cogin 방지용 URI 관리 Class
 * <pre>
 * <b>History:</b>
 *    주니하랑, 1.0.0, 2022.02.08 최초 작성
 * </pre>
 *
 * @author 주니하랑
 * @version 1.0.0, 2022.02.08 최초 작성
 * @See ""
 * @see <a href=""></a>
 */

public interface ServiceURIVersion {

    String NOW_VERSION = "/api/v1";
    //String NOW_VERSION = "/api";

    String NOW_VERSION_GUEST = NOW_VERSION+"guest";
    String NOW_VERSION_FAMILY = NOW_VERSION+"family";
    String NOW_VERSION_ADMIN = NOW_VERSION+"admin";

} // interface 끝
