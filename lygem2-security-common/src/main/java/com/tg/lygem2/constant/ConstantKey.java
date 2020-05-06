package com.tg.lygem2.constant;

public interface ConstantKey {

    /**
     * 签名key
     */
     String SIGNING_KEY = "spring-security-@Jwt!&Secret^#";

     String CONTENTTYPE = "application/json;charset=utf-8";

//     String SUCCESS = "success";

     String ERROR = "error";

     String TOKENHEADER = "Authorization";
     
     String JWT_PREFIX = "Bearer ";

     String ROLELOGIN = "ROLE_LOGIN";

     String ERRORPAGE = "/login_error";

     String LOGINPAGE = "login";

     String LOGINPAGE_URI = "/auth/login";

     String USERNAME = "n";

     String PASSWORD = "p";
     
	String LOGIN_FAILED_COUNT = "fbp_login_failed_count";

	int LOGIN_FAILED_COUNT_REFRESH_MILLSEC = 30 * 60 * 1000;
    String [] NOFILTERURI = {
        "/login",
        "/error"
     };
}
