package info.lofei.app.tuchong.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author info.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-17 14:23
 */
public class TCAuthor implements Serializable {

    @SerializedName("site_id")
    private long siteId;

    private String type;

    private String name;

    private String domain;

    private String url;

    @SerializedName("icon")
    private String iconUrl;

    private String description;

    @SerializedName("followers")
    private int follwerCount;

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(final long siteId) {
        this.siteId = siteId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getFollwerCount() {
        return follwerCount;
    }

    public void setFollwerCount(final int follwerCount) {
        this.follwerCount = follwerCount;
    }
}
