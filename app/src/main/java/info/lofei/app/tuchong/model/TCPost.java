package info.lofei.app.tuchong.model;

import android.support.annotation.StringDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * model of a post.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:12
 */
public class TCPost implements Serializable {

//    @Retention(RetentionPolicy.SOURCE)
//    @StringDef({TEXT, MULTI_PHOTO})
//    public @interface Type{}

    public static final String TEXT = "text";
    public static final String MULTI_PHOTO = "multi-photo";

    private long post_id;

    private String type;

    private String url;

    private long site_id;

    private long author_id;

    private String published_at;

    private String excerpt;

    private int favorites;

    private int comments;

    private String title;

    private int image_count;

    private List<TCImage> images;

    private boolean is_favorite;

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(final long post_id) {
        this.post_id = post_id;
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

    public long getSite_id() {
        return site_id;
    }

    public void setSite_id(final long site_id) {
        this.site_id = site_id;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(final long author_id) {
        this.author_id = author_id;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(final String published_at) {
        this.published_at = published_at;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(final String excerpt) {
        this.excerpt = excerpt;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(final int favorites) {
        this.favorites = favorites;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(final int comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getImage_count() {
        return image_count;
    }

    public void setImage_count(final int image_count) {
        this.image_count = image_count;
    }

    public List<TCImage> getImages() {
        return images;
    }

    public void setImages(final List<TCImage> images) {
        this.images = images;
    }

    public boolean is_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(final boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    @Override
    public String toString() {
        return "TCPost{" +
                "post_id=" + post_id +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", site_id=" + site_id +
                ", author_id=" + author_id +
                ", published_at='" + published_at + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", favorites=" + favorites +
                ", comments=" + comments +
                ", title='" + title + '\'' +
                ", image_count=" + image_count +
                ", images=" + images +
                ", is_favorite=" + is_favorite +
                '}';
    }
}