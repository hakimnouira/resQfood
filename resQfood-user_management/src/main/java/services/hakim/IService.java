package services.hakim;

import java.sql.SQLException;
import java.util.List;
public interface IService<T> {
    void create(T var1) throws SQLException;

    void update(T var1) throws SQLException;

    void delete(int var1) throws SQLException;

    List<T> read() throws SQLException;
    List<T> read(int id) throws SQLException;



}
