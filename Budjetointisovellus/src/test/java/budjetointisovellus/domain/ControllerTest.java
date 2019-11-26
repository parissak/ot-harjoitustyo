package budjetointisovellus.domain;

import budjetointisovellus.domain.BudgetController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ControllerTest {

    Budget budget;
    BudgetController controller;

    @Before
    public void setUp() {
        this.controller = new BudgetController();
        controller.addBudget("New Budget");
        
    }

    @Test
    public void addBudget() {
        assertEquals("New Budget", controller.getBudgets().get(0).getName());
    }
    
    @Test
    public void removeBudget() {
        controller.removeBudget("New Budget");
        assertEquals(true, controller.getBudgets().isEmpty());
    }
}
