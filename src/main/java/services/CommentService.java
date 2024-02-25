package services;

import models.Comment;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements IService<Comment> {
    private static final String INSERT_QUERY = "INSERT INTO Comments (post_id, user_id, content) VALUES (?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM Comments WHERE comment_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Comments where post_id = ?";
    private static final String UPDATE_QUERY = "UPDATE Comments SET post_id = ?, user_id = ?, content = ? WHERE comment_id = ?";

    private Connection connection = MyDatabase.getInstance().getConnection();

    public void create(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
        statement.setInt(1, comment.getPostId());
        statement.setInt(2, comment.getUserId());
        statement.setString(3, comment.getContent());
        statement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public List<Comment> read() throws SQLException {
        return null;
    }

    public List<Comment> read(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
        statement.setInt(1, id);
        statement.toString();
        ResultSet resultSet = statement.executeQuery();

        List<Comment> comments = new ArrayList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setCommentId(resultSet.getInt("comment_id"));
            comment.setPostId(resultSet.getInt("post_id"));
            comment.setUserId(resultSet.getInt("user_id"));
            comment.setContent(resultSet.getString("content"));
            comments.add(comment);
        }
        return comments;
    }
    public void update(Comment comment) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setInt(1, comment.getPostId());
        statement.setInt(2, comment.getUserId());
        statement.setString(3, comment.getContent());
        statement.setInt(4, comment.getCommentId());
        statement.executeUpdate();
    }



}
