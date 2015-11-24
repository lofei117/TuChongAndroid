package info.lofei.app.tuchong.model;

import com.google.gson.annotations.SerializedName;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-10-19 19:17
 */
public class TCExif {

    private TCCamera camera;

    private TCLens lens;

    private String exposure;

    @SerializedName("taken")
    private String takenTime;

    public TCCamera getCamera() {
        return camera;
    }

    public void setCamera(TCCamera camera) {
        this.camera = camera;
    }

    public TCLens getLens() {
        return lens;
    }

    public void setLens(TCLens lens) {
        this.lens = lens;
    }

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(String takenTime) {
        this.takenTime = takenTime;
    }

    @Override
    public String toString() {
        return "TCExif{" +
                "camera=" + camera +
                ", lens=" + lens +
                ", exposure='" + exposure + '\'' +
                ", takenTime='" + takenTime + '\'' +
                '}';
    }
}
