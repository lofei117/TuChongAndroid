package info.lofei.app.tuchong.vendor;

/**
 * Api url.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:43
 */
public class TuChongApi {

    public static final String PUBLIC_MODULE = "D8CC0180AFCC72C9F5981BDB90A27928672F1D6EA8A57AF44EFFA7DAF6EFB17DAD9F643B9F9F7A1F05ACC2FEA8DE19F023200EFEE9224104627F1E680CE8F025AF44824A45EA4DDC321672D2DEAA91DB27418CFDD776848F27A76E747D53966683EFB00F7485F3ECF68365F5C10C69969AE3D665162D2EE3A5BA109D7DF6C7A5";

    public static final String PUBLIC_EXPONENT = "10001";

    //region apis
    private static final String BASE_URL = "http://tuchong.com/rest/";

    private static final String V1_BASE_URL = "http://tuchong.com/api/";

    public static final String RESET_PWD = BASE_URL + "password";

    public static final String REGIESTER_USER = BASE_URL + "accounts/register";

    public static final String ACTIVITY_LIST_URL = BASE_URL + "users/self/activities?count=%1$d&offset=%2$d";

    public static final String SITE_URL = V1_BASE_URL + "site/get/?site_id=%1$s";

    public static final String CAPTCHA_URL = BASE_URL +  "captcha/image";

    public static final String SMS_CAPTCHA = BASE_URL + "captcha/sms";

    public static final String LOGIN_URL = BASE_URL + "accounts/login";

    public static final String LOGOUT_URL = V1_BASE_URL + "account/logout";

    public static final String ACCOUNTS_CHECK_EMAIL = BASE_URL + "accounts/check-email/";

    public static final String ACCOUNTS_CHECK_PHONE_NO = BASE_URL + "accounts/check-mobile/0086/";

    public static final String ACCOUNTS_CHECK_UNAME = BASE_URL + "accounts/check-name/";

    public static final String NOTIFICATION_URL = BASE_URL + "users/%1$d/notifications";

    public static final String COMMENT_URL = BASE_URL + "posts/%1$d/comments?type=comment";

    public static final String CATEGORY_URL = BASE_URL + "recommend/%1$s?type=%2$s&limit=%3$d";

    public static final String POST_DETAIL_URL = BASE_URL + "posts/%1$d"; // post id

    public static final String EXIF_DETAIL_URL = BASE_URL + "images/%1$d/exif";  // image id

    public static final String EXIF_URL = V1_BASE_URL + "post/get-post/?img_id=%1$d&post_id=%2$d";

    public static final String FAVORITE_POST_URL =  BASE_URL + "users/self/favorites/%1$d";
    //endregion

    //region photo url
    public static final String PHOTO_URL_LARGE = "http://photos.tuchong.com/%1$d/l/%2$d.jpg";

    public static final String PHOTO_URL_MEDIUM = "http://photos.tuchong.com/%1$d/m/%2$d.jpg";

    public static final String PHOTO_URL_SMALL = "http://photos.tuchong.com/%1$d/s/%2$d.jpg";
    //endregion
}
