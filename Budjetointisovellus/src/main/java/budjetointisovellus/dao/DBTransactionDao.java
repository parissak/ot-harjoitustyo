package budjetointisovellus.dao;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
import budjetointisovellus.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBTransactionDao implements TransactionDao {

    private BudgetDao budgetDao;

    public DBTransactionDao(BudgetDao budgetDao) throws SQLException {
        this.budgetDao = budgetDao;
        createTransactionTable();
    }

    /**
     * Lisää tietorakenteeseen erän budjetille ja tallentaa tietokantaan.
     *
     * @param budget haettu budjetti-olio.
     * @param name erän nimi.
     * @param amount erän määrä.
     */
    @Override
    public void create(Budget budget, String name, int amount) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Transaction (name, amount, budget_name) "
                + "VALUES (?,?,?);");
        stmt.setString(1, name);
        stmt.setInt(2, amount);
        stmt.setString(3, budget.getName());
        stmt.executeUpdate();

        conn.close();
    }

    /**
     * Hakee tietokannasta tietyn käyttäjän budjettiin liittyvät erät.
     *
     * @param budget haettu budjetti-olio.
     * @param name erän nimi.
     * @param amount erän määrä.
     * @return lista eristä.
     */
    @Override
    public List<Transaction> getUserBudgetsTransactions(User user, Budget budget) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
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

    /**
     * Poistaa budjetista tietyn erän ja tallentaa tietokantaan.
     *
     * @param budget budjetti-olio.
     * @param transaction erään liittyvä olio.
     */
    @Override
    public void remove(Budget budget, Transaction transaction) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
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
    
     private void createTransactionTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Transaction "
                + "(id IDENTITY AUTO_INCREMENT, name varchar(15), amount integer, budget_name varchar(15), "
                + "FOREIGN KEY (budget_name) REFERENCES "
                + "Budget(name) ON DELETE CASCADE);").executeUpdate();
        conn.close();
    }
}
