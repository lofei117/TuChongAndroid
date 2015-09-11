package info.lofei.app.tuchong.util;

import java.util.HashMap;
import java.util.Map;

import info.lofei.app.tuchong.model.TCSite;

/**
 * TODO comment here.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-07 16:03
 */
public class SitesMapCache {

    private static Map<String, TCSite> sSitesCache;

    public static TCSite getSite(String siteId) {
        if (sSitesCache != null) {
            return sSitesCache.get(siteId);
        }
        return null;
    }

    public static TCSite putSite(String siteId, TCSite site) {
        synchronized (SitesMapCache.class) {
            if (sSitesCache == null) {
                sSitesCache = new HashMap<>(16);
            }
            return sSitesCache.put(siteId, site);
        }
    }

    public static void putAll(Map<String, TCSite> sites) {
        synchronized (SitesMapCache.class) {
            if (sSitesCache == null) {
                sSitesCache = new HashMap<>(16);
            }
            sSitesCache.putAll(sites);
        }
    }

    public static TCSite getSite(final long author_id) {
        return getSite(String.valueOf(author_id));
    }
}
