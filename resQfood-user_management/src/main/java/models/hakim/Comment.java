package models.hakim;

public class Comment {
    private int commentId;
    private int postId;
    private int userId;
    private String content;

    // Default Constructor
    public Comment() {
    }

    public Comment(int commentId, int postId, int userId, String content) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    // Getters and setters

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "controllers.Comment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}
