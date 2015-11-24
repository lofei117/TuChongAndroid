package info.lofei.app.tuchong.model;

import java.util.List;

/**
 * model of activity shown in main page.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:07
 */
public class TCActivity {

    private String type;

    private String created_at;

    private List<Long> site_id_array;

    private TCPost post;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(final String created_at) {
        this.created_at = created_at;
    }

    public List<Long> getSite_id_array() {
        return site_id_array;
    }

    public void setSite_id_array(final List<Long> site_id_array) {
        this.site_id_array = site_id_array;
    }

    public TCPost getPost() {
        return post;
    }

    public void setPost(final TCPost post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "TCActivity{" +
                "type='" + type + '\'' +
                ", created_at='" + created_at + '\'' +
                ", site_id_array=" + site_id_array +
                ", post=" + post +
                '}';
    }
}
