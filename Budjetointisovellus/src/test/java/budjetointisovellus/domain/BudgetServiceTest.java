package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.FileBudgetDao;
import budjetointisovellus.domain.BudgetService;
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
    FakeBudgetDao fakeDao;

    @Before
    public void setUp() {
        fakeDao = new FakeBudgetDao();
        service = new BudgetService(fakeDao);
        service.create("New Budget");
    }

    @Test
    public void createBudget() {
        assertEquals(true, service.create("New Budget"));
    }

    @Test
    public void removeBudget() {
        assertEquals(true, service.removeBudget("New Budget"));
    }

    @Test
    public void budgetIsContained() {
        assertEquals(1, service.getBudgets().size());
    }

    @Test
    public void addTransactionToCorrectBudget() {
        assertEquals(true, service.addEventToBudget("New Budget", "test", 100));
    }

    @Test
    public void transactionIsContained() {
        service.addEventToBudget("New Budget", "New event", 100);
        Transaction event = new Transaction("New event", 0);

        assertEquals(true, service.getBudgetEvents("New Budget").contains(event));
    }

    @Test
    public void correctTransactionRemoved() {
        service.addEventToBudget("New Budget", "Event", 100);
        service.addEventToBudget("New Budget", "Another", 100);
        service.removeBudgetEvent("New Budget", "Event");

        Transaction testA = new Transaction("Event", 0);
        Transaction testB = new Transaction("Another", 0);

        ArrayList<Transaction> list = service.getBudgetEvents("New Budget");

        assertFalse(list.contains(testA));
        assertTrue(list.contains(testB));
    }
}