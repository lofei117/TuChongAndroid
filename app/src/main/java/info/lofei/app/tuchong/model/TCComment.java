package info.lofei.app.tuchong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Comment.
 *
 * @author lofei lofei@lofei.info
 * @version 1.0.0
 *          created at: 2015-07-16 17:57
 */
public class TCComment {

    @SerializedName("note_id")
    private long noteId;

    @SerializedName("post_id")
    private long postId;

    private String type;

    private String content;

    @SerializedName("created_at")
    private String createdAt;

    private boolean delete;

    private boolean reply;

    @SerializedName("author_id")
    private long authorId;

    private int anonymous;

    @SerializedName("likes")
    private int likeCount;

    private boolean liked;

    @SerializedName("reply_to")
    private List<TCAuthor> replyTo;

    private TCImage image;

    private TCAuthor author;

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(final long noteId) {
        this.noteId = noteId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(final long postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(final boolean delete) {
        this.delete = delete;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(final boolean reply) {
        this.reply = reply;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final long authorId) {
        this.authorId = authorId;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(final int anonymous) {
        this.anonymous = anonymous;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(final int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(final boolean liked) {
        this.liked = liked;
    }

    public List<TCAuthor> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(final List<TCAuthor> replyTo) {
        this.replyTo = replyTo;
    }

    public TCAuthor getAuthor() {
        return author;
    }

    public void setAuthor(final TCAuthor author) {
        this.author = author;
    }

    public TCImage getImage() {
        return image;
    }

    public void setImage(TCImage image) {
        this.image = image;
    }
}
