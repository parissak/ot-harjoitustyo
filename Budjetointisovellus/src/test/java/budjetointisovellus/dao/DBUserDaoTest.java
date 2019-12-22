package budjetointisovellus.dao;

import budjetointisovellus.domain.FakeUserDao;
import budjetointisovellus.domain.User;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DBUserDaoTest {

    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new DBUserDao();
    }

    @After
    public void tearDown() throws SQLException {
        File org = new File("database.mv.db");
        File orgTrace = new File("database.trace.db");
        org.delete();
        org.delete();
    }

    @Test
    public void addUser() throws SQLException {
        User test = new User("User");
        userDao.create(test);

        assertTrue(userDao.getAll().contains(test));
    }

    @Test
    public void returnCorrectKey() throws SQLException {
        User test = new User("User");
        userDao.create(test);
        int num = userDao.read(test);

        assertEquals(1, num);
    }
    
    @Test
    public void findCorrectUser() throws SQLException {
        User test = new User("Correct");
        userDao.create(test);

        assertEquals(test, userDao.findByUsername("Correct"));
    }

}
