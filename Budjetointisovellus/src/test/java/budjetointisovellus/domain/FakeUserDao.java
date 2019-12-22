package budjetointisovellus.domain;

import budjetointisovellus.dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeUserDao implements UserDao {

    public FakeUserDao() {
    }

    @Override
    public void create(User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (name) VALUES (?)");
        stmt.setString(1, user.getUsername());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE name = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        User user = new User(rs.getString("name"));

        rs.close();
        stmt.close();
        conn.close();
        return user;
    }

    @Override
    public Integer read(User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE name = ?");
        stmt.setString(1, user.getUsername());
        ResultSet rs = stmt.executeQuery();

        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        stmt.close();
        conn.close();

        return id;
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User");
        ResultSet rs = stmt.executeQuery();

        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User(rs.getString("name"));
            users.add(user);
        }

        rs.close();
        stmt.close();
        conn.close();

        return users;

    }

}
