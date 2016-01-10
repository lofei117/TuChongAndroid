package info.lofei.app.tuchong.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * model of an image.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:17
 */
public class TCImage implements Serializable {

    @SerializedName("img_id")
    private long mImageId;

    @SerializedName("user_id")
    private long mUserId;

    private String title;

    private String excerpt;

    private int width;

    private int height;

    private String description;

    @SerializedName("user")
    private TCAuthor mAuthor;

    @SerializedName("exif")
    private TCExif mExif;

    public long getImageId() {
        return mImageId;
    }

    public void setImageId(final long imageId) {
        this.mImageId = imageId;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(final long userId) {
        this.mUserId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(final String excerpt) {
        this.excerpt = excerpt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TCAuthor getAuthor() {
        return mAuthor;
    }

    public void setAuthor(TCAuthor author) {
        mAuthor = author;
    }

    public TCExif getExif() {
        return mExif;
    }

    public void setExif(TCExif exif) {
        mExif = exif;
    }

    @Override
    public String toString() {
        return "TCImage{" +
                "img_id=" + mImageId +
                ", user_id=" + mUserId +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
