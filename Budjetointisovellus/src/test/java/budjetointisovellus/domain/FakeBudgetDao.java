package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeBudgetDao implements BudgetDao {

    private UserDao userDao;

    public FakeBudgetDao(UserDao users) {
        this.userDao = users;
    }

    @Override
    public void create(Budget budget) throws SQLException {
        int userId = userDao.read(budget.getUser());

        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Budget (name, user_id) VALUES (?, ?)");
        stmt.setString(1, budget.getName());
        stmt.setInt(2, userId);
        stmt.execute();

        stmt.close();
        conn.close();
    }

    @Override
    public void remove(Budget budget) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Budget WHERE name = (?)");
        stmt.setString(1, budget.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public List<Budget> getUserBudgets(User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT budget.id, budget.name, "
                + "transaction.name, amount, user.name FROM BUDGET "
                + "JOIN USER on User_id = user.id "
                + "LEFT JOIN TRANSACTION ON BUDGET_name = budget.name "
                + "WHERE user.name = (?);");
        stmt.setString(1, user.getUsername());
        ResultSet rs = stmt.executeQuery();
        

        List<Budget> list = new ArrayList<>();

        while (rs.next()) {
            Budget b = findBudget(rs.getString("budget.name"), user, list);
            if (!list.contains(b)) {
                list.add(b);
            }
        }

        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    private Budget findBudget(String budgetName, User user, List<Budget> list) {
        return list.stream()
                .filter(b -> b.getName()
                .equals(budgetName))
                .findFirst()
                .orElse(new Budget(budgetName, user));
    }

}
