package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import java.util.HashMap;
import java.util.List;

public interface BudgetDao {

    // Creates budget
    void create(Budget budget) throws Exception;

    // Creates event to budget
    void create(Budget budget, String name, int amount) throws Exception;

    // Removes event from budget 
    void remove(Budget budget, String eventName) throws Exception;

    // Removes budget
    void remove(Budget budget) throws Exception;

    List<Budget> getBudgets();

}
