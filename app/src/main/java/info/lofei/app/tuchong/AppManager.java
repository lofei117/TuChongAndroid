package info.lofei.app.tuchong;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by jerrysher on 11/17/15.
 */
public class AppManager {

    private final static AppManager mAppManager = new AppManager();

    private AppManager() {
        mActivities = new Stack<>();
    }

    public static AppManager getInstance() {
        return mAppManager;
    }

    private Stack<Activity> mActivities;

    public void add(Activity activity) {
        mActivities.add(activity);
    }

    public void remove(Activity activity) {
        mActivities.remove(activity);
    }

    public void finishAllActivities() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }
}
