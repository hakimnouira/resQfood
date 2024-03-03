package models.hakim;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class Post {
    private int postId;
    private int userId;
    private int categoryId;
    private String title;
    private String content;

    private  String image;

    public Post(int postId, int userId, int categoryId, String title, String content, String image, Date created_at, Date updated_at) {
        this.postId = postId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private  Date  created_at;
    private Date updated_at;

    public Post(int postId, int userId, int categoryId, String title, String content,  Date  created_at,  Date  updated_at) {
        this.postId = postId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Post(int postId, int userId, int categoryId, String title, String content, String image) {
        this.postId = postId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public  Date  getCreated_at() {
        return created_at;
    }

    public void setCreated_at( Date  created_at) {
        this.created_at = created_at;
    }

    public  Date  getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at( Date  updated_at) {
        this.updated_at = updated_at;
    }
    // Default Constructor
    public Post(int i) {
this.created_at=new Date(1,1,1);
this.updated_at=new Date(1,1,1);

       this.userId = i;
        this.categoryId = 0;
        this.title = "";
        this.content = "";
        this.image = "https://assets-global.website-files.com/64949e4863d96e26a1da8386/64b94c7e02162f5cc666b317_633604c562868a10ab4c7163_uMk2yhmH04IjjAHWzOeM_tATsEn6kaJHIXikeFABZPv7G2VpYt7NdACThY1yQcBUw7KQWXpOiDBqumo3FVIKMpqBNPWO_U-5gGreQMZ23EFrqbhAXMRWtPU-zY7XdyZ0HfsgQV7FcafaziA6lddfUYoOERc3k2_UJ9M90FcOpdC_iIDDZfAZqzWDmg.png";
    }
    public Post() {

    }


    public Post(int postId , int userId , int categoryId, String title, String content) {
        this.postId = postId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
    }

    // Getters and setters

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
