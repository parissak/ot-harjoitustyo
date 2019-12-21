package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
import budjetointisovellus.domain.User;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {

    void create(Budget budget, String name, int amount) throws SQLException;
    
    void remove(Budget budget, Transaction transaction) throws SQLException;

    List<Transaction> getUserBudgetsTransactions(User user, Budget budget) throws SQLException;
}
