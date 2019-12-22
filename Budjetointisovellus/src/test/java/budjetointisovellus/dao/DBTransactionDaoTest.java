package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
import budjetointisovellus.domain.User;
import java.io.File;
import java.sql.SQLException;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DBTransactionDaoTest {

    BudgetDao budgetDao;
    UserDao userDao;
    TransactionDao transactionDao;

    @Before
    public void setUp() throws Exception {
        userDao = new DBUserDao();
        budgetDao = new DBBudgetDao(userDao);
        transactionDao = new DBTransactionDao(budgetDao);
    }

    @Test
    public void addAndFindTransaction() throws SQLException {
        User user = new User("Test");
        userDao.create(user);

        Budget budget = new Budget("Budget", null);
        budget.setUser(user);
        budgetDao.create(budget);

        Transaction action = new Transaction("Transaction", 1000);
        transactionDao.create(budget, "Transaction", 1000);

        assertTrue(transactionDao.getUserBudgetsTransactions(user, budget).contains(action));
    }

    @Test
    public void removeCorrectTransaction() throws SQLException {
        User user = new User("Test");
        userDao.create(user);

        Budget budget = new Budget("Test", null);
        budget.setUser(user);
        budgetDao.create(budget);

        Transaction income = new Transaction("Income", 1000);
        Transaction expense = new Transaction("Expense", -500);
        transactionDao.create(budget, "Income", 1000);
        transactionDao.create(budget, "Expense", -500);
        transactionDao.remove(budget, income);

        assertFalse(transactionDao.getUserBudgetsTransactions(user, budget).contains(income));
        assertTrue(transactionDao.getUserBudgetsTransactions(user, budget).contains(expense));
    }

    @After
    public void tearDown() throws SQLException {
        File org = new File("database.mv.db");
        File orgTrace = new File("database.trace.db");
        org.delete();
        org.delete();
    }

}
