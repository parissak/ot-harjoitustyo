package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * Mikäli sellaista ei ole, niin luo uuden budjetin.
     *
     * @param name annettu budjetin nimi
     * @return budjetti-olio.
     */
    private Budget findBudget(String budgetName, User user, List<Budget> list) {
        return list.stream()
                .filter(b -> b.getName()
                .equals(budgetName))
                .findFirst()
                .orElse(new Budget(budgetName, user));
    }

    /**
     * Lisää tietorakenteeseen budjetin ja tallentaa tietokantaan.
     *
     * @param budget budjetti-olio.
     */
    @Override
    public void create(Budget budget) throws SQLException {
        int userId = userDao.read(budget.getUser());

        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Budget (name, user_id) VALUES (?, ?)");
        stmt.setString(1, budget.getName());
        stmt.setInt(2, userId);
        stmt.execute();

        stmt.close();
        conn.close();
    }

    /**
     * Poistaa budjetin ja tallentaa tietokantaan.
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
     * Palauttaa tietokannasta listan budjetti-olioista, joille on lisätty
     * käyttäjä-olio.
     *
     * @return lista budjeteista.
     */
    @Override
    public List<Budget> getUserBudgets(User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
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

    private void createBudgetTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Budget"
                + " (id IDENTITY AUTO_INCREMENT, name varchar(15), user_id INTEGER,"
                + " FOREIGN KEY (user_id) REFERENCES user(id));").executeUpdate();
        conn.close();
    }

}
