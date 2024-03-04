package services.siwar;

import models.siwar.Category;
import java.sql.SQLException;
import java.util.List;

public interface PService<T> {
    void create(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> read(String User) throws SQLException;
}
