package info.lofei.app.tuchong.utils;

/**
 * Constants.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-03 14:14
 */
public interface Constant {

    public static final String COOKIE_KEY = "Cookie";
    public static final String SET_COOKIE_KEY = "Set-Cookie";
    public static final String EMAIL_KEY = "email";
    public static final String SESSION_KEY = "PHPSESSID";
    public static final String TOKEN_KEY = "token";

    public static final String RESULT_KEY = "result";
    public static final String RESULT_SUCCESS = "SUCCESS";
    public static final String RESULT_FAILED = "ERROR";

    public static final String RESULT_CODE_KEY = "code";


    interface ResultCode{
        int SUCCESS = 0;
        int NEED_LOGIN = 1;
        int HAD_FAVRIATED = 9;
    }

    public static final int PAGE_COUNT = 20;
}
