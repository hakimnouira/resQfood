package services;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Post;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostService implements IService<Post> {
    private static final String INSERT_QUERY = "INSERT INTO Posts (user_id, category_id, title, content,image) VALUES (?, ?, ?, ?,?)";
    private static final String UPDATE_QUERY = "UPDATE Posts SET user_id = ?, category_id = ?, title = ?, content = ? , image = ? WHERE post_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Posts WHERE post_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Posts";

    private Connection connection = MyDatabase.getInstance().getConnection();


    public void create(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
        statement.setInt(1, post.getUserId());
        statement.setInt(2, post.getCategoryId());
        statement.setString(3, post.getTitle());
        statement.setString(4, post.getContent());
        statement.setString(5, post.getImage());
        statement.executeUpdate();
    }



    public void update(Post post) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setInt(1, post.getUserId());
        statement.setInt(2, post.getCategoryId());
        statement.setString(3, post.getTitle());
        statement.setString(4, post.getContent());
        statement.setInt(6, post.getPostId());
        statement.setString(5, post.getImage());
        System.out.println(statement.toString());
        statement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public List<Post> read() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
        List<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            Post post = new Post();
            post.setPostId(resultSet.getInt("post_id"));
            post.setUserId(resultSet.getInt("user_id"));
            post.setCategoryId(resultSet.getInt("category_id"));
            post.setTitle(resultSet.getString("title"));
            post.setContent(resultSet.getString("content"));
           post.setCreated_at(resultSet.getDate("created_at"));
           post.setUpdated_at(resultSet.getDate("updated_at"));
           post.setImage(resultSet.getString("image"));
            posts.add(post);
        }
        return posts;
    }

    @Override
    public List<Post> read(int id) throws SQLException {
        return null;
    }


    public Post readOne(int postId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Posts WHERE post_id = ?");
        statement.setInt(1, postId); // Set the post_id parameter value

        ResultSet resultSet = statement.executeQuery(); // Execute the SQL query





        Post post = null;
        if (resultSet.next()) { // Check if there are any results
            post = new Post();
            post.setPostId(resultSet.getInt("post_id"));
            post.setUserId(resultSet.getInt("user_id"));
            post.setCategoryId(resultSet.getInt("category_id"));
            post.setTitle(resultSet.getString("title"));
            post.setContent(resultSet.getString("content"));
            post.setCreated_at(resultSet.getDate("created_at"));
            post.setUpdated_at(resultSet.getDate("updated_at"));
            post.setImage(resultSet.getString("image"));
        }

        return post;
    }





@FXML
    Label comment;
    public Label getcomment2(){return comment;}







    public int  TITLETEST(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement( "SELECT COUNT(*) AS post_count FROM posts WHERE title =  ?" );
        statement.setString(1, id);
        statement.toString();
        ResultSet resultSet = statement.executeQuery();


        int commentCount = 0;
        // Retrieve the result
        if (resultSet.next()) {
            commentCount = resultSet.getInt("post_count");
        }

        // Close resources
        resultSet.close();
        statement.close();

        return commentCount;



    }






}


