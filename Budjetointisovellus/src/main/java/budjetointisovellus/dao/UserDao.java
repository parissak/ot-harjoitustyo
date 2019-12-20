package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User create(User user) throws SQLException;
    
    User findByUsername(String username) throws SQLException;
    
    Integer read(User user) throws SQLException;

    List<User> getAll() throws SQLException;

}
