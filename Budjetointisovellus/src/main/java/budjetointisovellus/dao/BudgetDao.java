package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.util.HashMap;
import java.util.List;

public interface BudgetDao {

    void create(Budget budget) throws Exception;

    void createTransaction(Budget budget, String name, int amount) throws Exception;

    void removeTransaction(Budget budget, String eventName) throws Exception;

    void remove(Budget budget) throws Exception;

    List<Budget> getBudgets();

}