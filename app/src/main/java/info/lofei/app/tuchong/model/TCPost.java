package info.lofei.app.tuchong.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * model of a post.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:12
 */
public class TCPost implements Serializable {

    public static final String TEXT = "text";

    public static final String MULTI_PHOTO = "multi-photo";

    @SerializedName("post_id")
    private long mPostId;

    private String type;

    private String url;

    @SerializedName("site_id")
    private long mSiteId;

    @SerializedName("author_id")
    private long mAuthorId;

    @SerializedName("published_at")
    private String mPublishedTime;

    private String excerpt;

    @SerializedName("favorites")
    private int mFavoriteCount;

    @SerializedName("comments")
    private int mCommentCount;

    private String title;

    @SerializedName("image_count")
    private int mImageCount;

    private List<String> tags;

    private TCSite site;

    private TCAuthor author;

    private String parsedContent;

    private int views;

    private List<String> flags;

    /*
    "privileges": {
        "edit": false,
                "delete": false,
                "admin": false,
                "update": false
    },
     */

    @SerializedName("is_favorite")
    private boolean mIsFavorite;

    private List<TCImage> images;

    public long getPostId() {
        return mPostId;
    }

    public void setPostId(final long postId) {
        this.mPostId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public long getSiteId() {
        return mSiteId;
    }

    public void setSiteId(final long siteId) {
        this.mSiteId = siteId;
    }

    public long getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(final long authorId) {
        this.mAuthorId = authorId;
    }

    public String getPublishedTime() {
        return mPublishedTime;
    }

    public void setPublishedTime(final String publishedTime) {
        this.mPublishedTime = publishedTime;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(final String excerpt) {
        this.excerpt = excerpt;
    }

    public int getFavoriteCount() {
        return mFavoriteCount;
    }

    public void setFavoriteCount(final int favoriteCount) {
        this.mFavoriteCount = favoriteCount;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(final int commentCount) {
        this.mCommentCount = commentCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getImageCount() {
        return mImageCount;
    }

    public void setImageCount(final int imageCount) {
        this.mImageCount = imageCount;
    }

    public List<TCImage> getImages() {
        return images;
    }

    public void setImages(final List<TCImage> images) {
        this.images = images;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(final boolean isFavorite) {
        this.mIsFavorite = isFavorite;
    }

    public TCAuthor getAuthor() {
        return author;
    }

    public void setAuthor(TCAuthor author) {
        this.author = author;
    }

    public TCSite getSite() {
        return site;
    }

    public void setSite(TCSite site) {
        this.site = site;
    }

    public String getParsedContent() {
        return parsedContent;
    }

    public void setParsedContent(String parsedContent) {
        this.parsedContent = parsedContent;
    }

    @Override
    public String toString() {
        return "TCPost{" +
                "mPostId=" + mPostId +
                ", mSiteId=" + mSiteId +
                ", mAuthorId=" + mAuthorId +
                ", mPublishedTime='" + mPublishedTime + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", favorites=" + mFavoriteCount +
                ", title='" + title + '\'' +
                ", mImageCount=" + mImageCount +
                ", author=" + author +
                ", is_favorite=" + mIsFavorite +
                '}';
    }
}
