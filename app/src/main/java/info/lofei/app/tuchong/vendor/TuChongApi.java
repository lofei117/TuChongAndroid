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

    public static final String ACTIVITY_LIST_URL = BASE_URL + "users/self/activities?count=%1$d&offset=%2$d";

    public static final String SITE_URL = BASE_URL + "site/get/?site_id=%1$d";

    public static final String LOGIN_URL = BASE_URL + "accounts/login";

    public static final String NOTIFICATION_URL = BASE_URL + "users/%1$d/notifications";

//    public static final String COMMENT_URL = BASE_URL + "comment/get/?post_id=%1$d&type=comment";
    //endregion

    //region photo url
    public static final String PHOTO_URL_LARGE = "http://photos.tuchong.com/%1$d/l/%2$d.jpg";
    public static final String PHOTO_URL_MEDIUM = "http://photos.tuchong.com/%1$d/m/%2$d.jpg";
    public static final String PHOTO_URL_SMALL = "http://photos.tuchong.com/%1$d/s/%2$d.jpg";
    //endregion
}
