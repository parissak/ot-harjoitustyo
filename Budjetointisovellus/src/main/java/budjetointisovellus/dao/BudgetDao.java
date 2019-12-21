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

    List<Budget> getAll() throws SQLException;
    
}
