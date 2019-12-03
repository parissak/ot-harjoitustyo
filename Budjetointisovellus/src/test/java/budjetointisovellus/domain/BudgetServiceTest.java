package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.FileBudgetDao;
import budjetointisovellus.domain.BudgetService;
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
    public void addEventToCorrectBudget() {
        assertEquals(true, service.addEventToBudget("New Budget", "test", 100));
    }

    @Test
    public void eventIsContained() {
        service.addEventToBudget("New Budget", "key", 100);
        HashMap<String, Integer> testMap = service.getBudgetEvents("New Budget");
        
        assertEquals(true, testMap.containsKey("key"));
        assertEquals(true, testMap.containsValue(100));
    }

    @Test
    public void correctEventRemoved() {
        service.addEventToBudget("New Budget", "a", 100);
        service.addEventToBudget("New Budget", "b", 300);
        service.removeBudgetEvent("New Budget", "a");
        HashMap<String, Integer> testMap = service.getBudgetEvents("New Budget");

        assertEquals(false, testMap.containsKey("a"));
        assertEquals(false, testMap.containsValue(100));
    }

}
