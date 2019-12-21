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

    public DBTransactionDao() throws SQLException {
        createTransactionTable();
    }

    /**
     * Lisää tietorakenteeseen erän budjetille ja kutsuu tietokantaan
     * tallentamista.
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

    @Override
    public Integer read(Transaction transaction) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> getAll(User user, Budget budget) throws SQLException {
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
        conn.close();
        stmt.close();

        return actions;
    }

    private void createTransactionTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./database", "sa", "");
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Transaction "
                + "(id IDENTITY AUTO_INCREMENT, name varchar(15), amount integer, budget_name varchar(15), "
                + "FOREIGN KEY (budget_name) REFERENCES "
                + "Budget(name) ON DELETE CASCADE);").executeUpdate();
        conn.close();
    }

    /**
     * Poistaa budjetista tietyn erän ja kutsuu tietokantaan tallentamista.
     *
     * @param budget haettu budjetti-olio.
     * @param eventName erän nimi.
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

}
