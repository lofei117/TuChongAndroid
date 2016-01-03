package info.lofei.app.tuchong.model;

import java.io.Serializable;

/**
 * Created by jerrysher on 11/17/15.
 */
public class TCNotification implements Serializable {
    /*
    "unreadComments": [],
  "unreadReposts": [],
  "unreadMentions": [],
  "pendingRequests": [],
  "unread_messages": 0,
  "unread_requests": 0,
  "unread_notifications": 0,
  "unread_followers": 0,
  "unread_favorites": 0,
  "unread_recommend_weibo_users": 0,
     */

    private int unread_messages;
    private int unread_requests;
    private int unread_notifications;
    private int unread_followers;
    private int unread_favorites;
    private int unread_recommend_weibo_users;

    public int getUnread_favorites() {
        return unread_favorites;
    }

    public void setUnread_favorites(int unread_favorites) {
        this.unread_favorites = unread_favorites;
    }

    public int getUnread_followers() {
        return unread_followers;
    }

    public void setUnread_followers(int unread_followers) {
        this.unread_followers = unread_followers;
    }

    public int getUnread_messages() {
        return unread_messages;
    }

    public void setUnread_messages(int unread_messages) {
        this.unread_messages = unread_messages;
    }

    public int getUnread_notifications() {
        return unread_notifications;
    }

    public void setUnread_notifications(int unread_notifications) {
        this.unread_notifications = unread_notifications;
    }

    public int getUnread_recommend_weibo_users() {
        return unread_recommend_weibo_users;
    }

    public void setUnread_recommend_weibo_users(int unread_recommend_weibo_users) {
        this.unread_recommend_weibo_users = unread_recommend_weibo_users;
    }

    public int getUnread_requests() {
        return unread_requests;
    }

    public void setUnread_requests(int unread_requests) {
        this.unread_requests = unread_requests;
    }
}

