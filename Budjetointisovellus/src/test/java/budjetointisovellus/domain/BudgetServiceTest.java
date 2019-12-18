package budjetointisovellus.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BudgetServiceTest {

    BudgetService service;
    FakeBudgetDao budgetDao;
    FakeUserDao userDao;

    @Before
    public void setUp() throws Exception {
        budgetDao = new FakeBudgetDao();
        userDao = new FakeUserDao();
        User userOne = new User("tester1");
        User userTwo = new User("tester2");

        userDao.create(userOne);
        userDao.create(userTwo);
        budgetDao.create(new Budget("Budget", userOne));

        service = new BudgetService(budgetDao, userDao);
        service.logIn("tester1");

    }

    @Test
    public void createBudget() {
        assertTrue(service.createBudget("Budget"));
    }

    @Test
    public void removeBudget() {
        assertTrue(service.removeBudget("Budget"));
    }

    @Test
    public void budgetIsContained() {
        assertEquals(1, service.getBudgets().size());
    }

    @Test
    public void addTransactionToCorrectBudget() {
        assertTrue(service.addTransactionToBudget("Budget", "test", 100));
    }

    @Test
    public void transactionIsContained() {
        service.addTransactionToBudget("Budget", "Event", 100);
        Transaction event = new Transaction("Event", 0);

        assertTrue(service.getBudgetEvents("Budget").contains(event));
    }

    @Test
    public void correctTransactionRemoved() {
        service.addTransactionToBudget("Budget", "Event", 100);
        service.addTransactionToBudget("Budget", "Another", 100);
        service.removeBudgetEvent("Budget", "Event");

        Transaction testA = new Transaction("Event", 0);
        Transaction testB = new Transaction("Another", 0);

        ArrayList<Transaction> list = service.getBudgetEvents("Budget");

        assertFalse(list.contains(testA));
        assertTrue(list.contains(testB));
    }
}
