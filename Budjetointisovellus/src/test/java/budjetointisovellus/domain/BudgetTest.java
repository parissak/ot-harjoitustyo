package budjetointisovellus.domain;

import budjetointisovellus.domain.Budget;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BudgetTest {

    Budget budget;

    @Before
    public void setUp() {
        budget = new Budget("New Budget");
    }

    @Test
    public void setNameGetName() {
        budget.setName("Test name");
        assertEquals("Test name", budget.getName());
    }

    @Test
    public void containsEvent() {
        HashMap<String, Integer> test = new HashMap<>();
        budget.setEvent("income", 100);
        test.put("income", 100);

        assertEquals(test, budget.getEvents());
    }

    @Test
    public void removeEvent() {
        HashMap<String, Integer> test = new HashMap<>();
        budget.setEvent("income", 100);
        budget.removeEvent("income");

        assertEquals(test, budget.getEvents());
    }

    @Test
    public void getCorrectBalance() {
        budget.setEvent("income", 1000);
        assertEquals(1000, budget.getBalance());
    }
    
    @Test
    public void correctOutput() {
        budget.setEvent("income", 500);
        assertEquals("New Budget, 500", budget.toString());
    }
    

}
