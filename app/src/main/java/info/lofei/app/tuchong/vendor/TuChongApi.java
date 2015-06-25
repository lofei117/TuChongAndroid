package info.lofei.app.tuchong.vendor;

/**
 * Api url.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:43
 */
public class TuChongApi {

    private static final String BASE_URL = "http://tuchong.com/api/";

    public static final String ACTIVITY_LIST_URL = BASE_URL + "activity/get/?count=%1$d&offset=%1$d";

    public static final String SITE_URL = BASE_URL + "site/get/?site_id=%1$d";

    public static final String LOGIN_URL = BASE_URL + "account/login/";

    public static final String NOTIFICATION_URL = BASE_URL + "notification/get/?user_id=%1$d";

    public static final String COMMENT_URL = BASE_URL + "comment/get/?post_id=%1$d&type=comment";
}
