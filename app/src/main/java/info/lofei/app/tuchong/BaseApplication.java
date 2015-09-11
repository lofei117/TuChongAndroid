package info.lofei.app.tuchong;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.igexin.sdk.PushManager;

import java.util.List;

import butterknife.ButterKnife;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-06-22 20:52
 */
public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isChildrenProcess()) {
            ButterKnife.setDebug(BuildConfig.DEBUG);
            sInstance = this;
            PushManager.getInstance().initialize(this);
        }
    }

    public static BaseApplication getBaseApplication() {
        return sInstance;
    }

    private boolean isChildrenProcess() {
        return !getPackageName().equals(getProcessName(android.os.Process.myPid()));
    }

    private String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null || runningApps.isEmpty()) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
            if (processInfo.pid == pid) {
                Log.d("test", processInfo.processName);
                return processInfo.processName;
            }
        }
        return "";
    }

}
