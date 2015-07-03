package info.lofei.app.tuchong.model;

/**
 * model of an image.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:17
 */
public class TCImage {

    private long img_id;

    private long user_id;

    private String title;

    private String excerpt;

    private int width;

    private int height;

    public long getImg_id() {
        return img_id;
    }

    public void setImg_id(final long img_id) {
        this.img_id = img_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(final long user_id) {
        this.user_id = user_id;
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
}
