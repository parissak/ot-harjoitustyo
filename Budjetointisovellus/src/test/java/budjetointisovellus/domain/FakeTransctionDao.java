/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.TransactionDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author parissak
 */
class FakeTransctionDao implements TransactionDao {

    private BudgetDao budgetDao;

    public FakeTransctionDao(BudgetDao budgetDao) throws SQLException {
        this.budgetDao = budgetDao;
    }

    @Override
    public void create(Budget budget, String name, int amount) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Transaction (name, amount, budget_name) "
                + "VALUES (?,?,?);");
        stmt.setString(1, name);
        stmt.setInt(2, amount);
        stmt.setString(3, budget.getName());
        stmt.executeUpdate();

        conn.close();
    }

    @Override
    public void remove(Budget budget, Transaction transaction) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM TRANSACTION "
                + " WHERE budget_name = (?)"
                + " AND transaction.name = (?)"
                + " AND amount = (?);");
        stmt.setString(1, budget.getName());
        stmt.setString(2, transaction.getName());
        stmt.setInt(3, transaction.getAmount());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public List<Transaction> getUserBudgetsTransactions(User user, Budget budget) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Budget\n"
                + " JOIN Transaction ON budget.name = budget_name"
                + " JOIN User ON user_id = user.id"
                + " WHERE user.name = (?)"
                + " AND budget.name = (?);");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, budget.getName());

        ResultSet rs = stmt.executeQuery();
        List<Transaction> actions = new ArrayList<>();

        while (rs.next()) {
            Transaction action = new Transaction(rs.getString("transaction.name"), rs.getInt("amount"));
            actions.add(action);
        }

        rs.close();
        stmt.close();
        conn.close();

        return actions;
    }

}
