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
    public void setCorrectNameGetCorrectName() {
        assertEquals("New Budget", budget.getName());
    }
    
    @Test
    public void budgetContainsEvent() {
        budget.setEvent("event", 100);
        HashMap<String, Integer> test = new HashMap<>();
        test.put("event", 100);
        
        assertEquals(test, budget.getEvents());
    }
    
    
}
