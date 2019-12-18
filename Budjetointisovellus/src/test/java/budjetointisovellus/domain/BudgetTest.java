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
        budget = new Budget("New Budget", new User("User"));
    }

    @Test
    public void setEventToBudget() {
        budget.setTransaction("income", 100);
        assertEquals(1, budget.getTransactions().size());
    }

    @Test
    public void removeEvent() {
        budget.setTransaction("income", 100);
        budget.getTransactions().remove(new Transaction("income", 0));
        assertEquals(0, budget.getTransactions().size());
    }

    @Test
    public void getCorrectBalance() {
        budget.setTransaction("income", 1000);
        budget.setTransaction("expense", -500);
        assertEquals(500, budget.getBalance());
    }

    @Test
    public void notEqualWithDifferentUser() {
        Budget anotherBudget = new Budget("New Budget", new User("UserB"));
        assertFalse(budget.equals(anotherBudget));
    }

    public void notEqualWithDifferentName() {
        Budget anotherBudget = new Budget("Another Budget", new User("UserB"));
        assertFalse(budget.equals(anotherBudget));
    }

}
