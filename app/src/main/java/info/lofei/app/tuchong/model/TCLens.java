package info.lofei.app.tuchong.model;

/**
 * TClens.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-10-23 16:56
 */
public class TCLens {

    private String name;

    private String slug;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TCLens{" +
                "name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
