package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
import budjetointisovellus.domain.User;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface BudgetDao {

    void create(Budget budget) throws SQLException;

    void remove(Budget budget) throws SQLException;

    List<Budget> getBudgets() throws SQLException;
    
    List<Transaction> getTransactions(User user, String budgetName) throws SQLException;
    
    void createTransaction(Budget budget, String name, int amount) throws Exception;

    void removeTransaction(Budget budget, Transaction transaction) throws Exception;

}
