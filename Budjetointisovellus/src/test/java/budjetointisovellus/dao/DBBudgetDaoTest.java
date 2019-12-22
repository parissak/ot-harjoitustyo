package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.io.File;
import java.sql.SQLException;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DBBudgetDaoTest {

    BudgetDao budgetDao;
    UserDao userDao;
    TransactionDao transactionDao;

    @Before
    public void setUp() throws Exception {
        userDao = new DBUserDao();
        budgetDao = new DBBudgetDao(userDao);
        transactionDao = new DBTransactionDao(budgetDao);
    }

    @After
    public void tearDown() throws SQLException {
        File org = new File("database.mv.db");
        File orgTrace = new File("database.trace.db");
        org.delete();
        org.delete();
    }

    @Test
    public void addBudgetFindBudget() throws SQLException {
        User user = new User("Test");
        userDao.create(user);

        Budget budget = new Budget("Test", null);
        budget.setUser(user);
        budgetDao.create(budget);

        assertTrue(budgetDao.getUserBudgets(user).contains(budget));

    }

}
