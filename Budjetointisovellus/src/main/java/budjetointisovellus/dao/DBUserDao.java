package budjetointisovellus.dao;

import budjetointisovellus.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUserDao implements UserDao {

    public DBUserDao() throws Exception {
        createUserTable();
    }

    /**
     * Tallentaa käyttäjän tietokantaan.
     *
     * @param user käyttäjä-olio.
     */
    @Override
    public void create(User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (name) VALUES (?)");
        stmt.setString(1, user.getUsername());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();

    }

    /**
     * Tarkistaa löytyykö nimimerkillä käyttäjää tietokannasta.
     *
     * @param username nimimerkki.
     * @return käyttäjä-olio sen löytyessä tai muuten null.
     */
    @Override
    public User findByUsername(String username) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
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

    /**
     * Palauttaa listan käyttäjistä.
     *
     * @return lista käyttäjä-olioista.
     */
    @Override
    public List<User> getAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
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

    /**
     * Palauttaa käyttäjään liittyvän pääavaimen.
     *
     * @param user käyttäjä.
     * @return pääavain.
     */
    @Override
    public Integer read(User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
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
    
    private void createUserTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS User "
                + "(id INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15));").executeUpdate();
        conn.close();
    }

}
