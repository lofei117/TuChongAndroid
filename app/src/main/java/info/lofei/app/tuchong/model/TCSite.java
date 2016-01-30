package info.lofei.app.tuchong.model;

import android.support.annotation.StringDef;
import android.support.annotation.StringRes;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Site... 用户或者小组信息.
 * http://tuchong.com/api/site/get/?site_id=1001424
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-27 22:52
 */
public class TCSite implements Serializable {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({USER, COLLECTION})
    public @interface Type{}

    public static final String USER = "user";
    public static final String COLLECTION = "collection";

    private int site_id;

    /**
     * 类型
     */
    private String type;

    @StringRes
    private String name;

    /**
     * 主页域名
     * @see #url
     */
    private String domain;

    /**
     * 粉丝数目
     */
    private int followers;

    private String following;

    private String description;

    /**
     * 用户或者小组主页
     */
    private String url;

    /**
     * 用户或者小组头像
     */
    private String icon;

    private boolean isFollowing;

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(final int site_id) {
        this.site_id = site_id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(final int followers) {
        this.followers = followers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    @Override
    public String toString() {
        return "TCSite{" +
                "site_id=" + site_id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", followers=" + followers +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
