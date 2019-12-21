package budjetointisovellus.dao;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
import budjetointisovellus.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Luokka vastaa tietokantaan ja tietorakenteeseen tallentamisesta ja tietojen
 * noutamisesta edellä mainituista kohteista.
 */
public class DBBudgetDao implements BudgetDao {

    private UserDao userDao;

    public DBBudgetDao(UserDao users) throws Exception {
        this.userDao = users;
        createBudgetTable();
    }

    /**
     * Apumetodi. Etsii tietorakenteesta olemassa olevan nimistä budjettia.
     * Mikäli sellaista ei ole, niin luo uuden budjetin. Lopulta palauttaa
     * budjetti-olion.
     *
     * @param name annettu budjetin nimi
     */
    public Budget findBudget(String budgetName, User user, List<Budget> list) {
        return list.stream()
                .filter(b -> b.getName()
                .equals(budgetName))
                .filter(b -> b.getUser()
                .equals(user))
                .findFirst()
                .orElse(new Budget(budgetName, user));
    }

    /**
     * Lisää tietorakenteeseen budjetin ja kutsuu tietokantaan tallentamista.
     *
     * @param budget budjetti-olio.
     */
    @Override
    public void create(Budget budget) throws SQLException {
        PreparedStatement stmt = null;

        int userId = userDao.read(budget.getUser());

        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");

        try {
            stmt = conn.prepareStatement("INSERT INTO Budget (name, user_id) VALUES (?, ?)");
            stmt.setString(1, budget.getName());
            stmt.setInt(2, userId);
            stmt.execute();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            stmt.close();
            conn.close();
        }

        stmt.close();
        conn.close();
//        save();
    }

    public void updateSum(Budget budget, User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT SUM(amount) AS Sum FROM BUDGET \n"
                + " JOIN USER on User_id = user.id \n"
                + " LEFT JOIN TRANSACTION ON BUDGET_name = budget.name"
                + " WHERE budget.name = (?) AND user.name = (?);");

        stmt.setString(1, budget.getName());
        stmt.setString(2, user.getUsername());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int sum = rs.getInt("Sum");
        }

        rs.close();
        stmt.close();
        conn.close();

    }

    /**
     * Poistaa budjetin ja kutsuu tietokantaan tallentamista.
     *
     * @param budget haettu budjetti-olio.
     */
    public void remove(Budget budget) throws SQLException {
        int userId = userDao.read(budget.getUser());

        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Budget WHERE name = (?) "
                + "AND user_id = (?)");
        stmt.setString(1, budget.getName());
        stmt.setInt(2, userId);
        stmt.executeUpdate();
        stmt.close();
        conn.close();

    }

    /**
     * Palauttaa tietorakenteesta listan budjeteista.
     *
     * @return lista budjeteista.
     */
    @Override
    public List<Budget> getAll() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("SELECT budget.id, budget.name, "
                + "transaction.name, amount, user.name FROM BUDGET "
                + "JOIN USER on User_id = user.id "
                + "LEFT JOIN TRANSACTION ON BUDGET_name = budget.name;");
        ResultSet rs = stmt.executeQuery();

        List<Budget> list = new ArrayList<>();

        while (rs.next()) {
            String budgetName = rs.getString("budget.name");
            String entryName = rs.getString("transaction.name");
            int entryAmount = rs.getInt("amount");
            String userName = rs.getString("user.name");

            User user = userDao.findByUsername(rs.getString("user.name"));
            Budget budget = findBudget(budgetName, user, list);

            if (entryAmount != 0) {
                budget.setTransaction(entryName, entryAmount);
            }

            if (!list.contains(budget)) {
                list.add(budget);
            }

        }

        rs.close();
        conn.close();
        stmt.close();

        return list;
    }

    private void createBudgetTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Budget"
                + " (id IDENTITY AUTO_INCREMENT, name varchar(15), user_id INTEGER,"
                + " FOREIGN KEY (user_id) REFERENCES user(id));").executeUpdate();
        conn.close();
    }

}
