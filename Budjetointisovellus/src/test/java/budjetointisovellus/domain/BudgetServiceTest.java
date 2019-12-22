package budjetointisovellus.domain;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BudgetServiceTest {

    private Connection conn;

    private BudgetService service;
    private FakeBudgetDao budgetDao;
    private FakeUserDao userDao;
    private FakeTransctionDao transctionDao;

    @Before
    public void setUp() throws Exception {

        userDao = new FakeUserDao();
        budgetDao = new FakeBudgetDao(userDao);
        transctionDao = new FakeTransctionDao(budgetDao);

        service = new BudgetService(budgetDao, userDao, transctionDao);
        service.createUser("User");

        initializeDatabase(conn);
    }

    @After
    public void tearDown() throws SQLException {
        File testDb = new File("test.mv.db");
        File testTrace = new File("test.trace.db");
        testDb.delete();
        testTrace.delete();
    }

    @Test
    public void createUser() {
        assertTrue(service.createUser("Test"));
        assertFalse(service.createUser("Test"));
    }

    @Test
    public void existingUserlogIn() {
        assertTrue(service.logIn("User"));
        assertFalse(service.logIn("NonExists"));
        service.logOut();
    }

    @Test
    public void existingUserlogOut() {
        service.logIn("User");
        service.logOut();
        assertEquals(null, service.getLoggedUser());
    }

    @Test
    public void createBudget() {
        service.createUser("Creator");
        service.logIn("Creator");
        assertTrue(service.createBudget("TestBudget"));
        service.logOut();
    }

    @Test
    public void removeCorrectBudget() throws SQLException {
        service.createUser("Remover");
        service.logIn("Remover");
        service.createBudget("DontRemove");
        service.createBudget("Remove");

        Budget removable = new Budget("Remove", new User("Remover"));
        Budget notRemovable = new Budget("DontRemove", new User("Remover"));

        service.removeBudget(removable);

        List<Budget> budgets = service.getUserBudgets();

        assertTrue(!budgets.contains(removable));
        assertTrue(budgets.contains(notRemovable));
        service.logOut();
    }

    @Test
    public void createTransaction() throws SQLException {
        service.createUser("Transactioner");
        service.logIn("Transactioner");

        service.createBudget("Test");
        Budget test = new Budget("Test", service.getLoggedUser());

        assertTrue(service.createTransaction(test, "TA", 100));
        service.logOut();
    }

    @Test
    public void removeCorrectTransaction() throws SQLException {
        service.logIn("Transactioner");

        Budget test = new Budget("Test", service.getLoggedUser());
        service.createTransaction(test, "TR", 500);
        Transaction removable = new Transaction("TR", 500);
        service.removeTransaction(test, removable);

        List<Transaction> actions = service.getBudgetTransactions(test);

        assertFalse(actions.contains(removable));
        service.logOut();
    }

    private void initializeDatabase(Connection conn) throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");
        
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS User "
                + "(id INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15));").executeUpdate();

        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Budget"
                + " (id IDENTITY AUTO_INCREMENT, name varchar(15), user_id INTEGER,"
                + " FOREIGN KEY (user_id) REFERENCES user(id));").executeUpdate();

        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Transaction "
                + "(id IDENTITY AUTO_INCREMENT, name varchar(15), amount integer, budget_name varchar(15), "
                + "FOREIGN KEY (budget_name) REFERENCES "
                + "Budget(name) ON DELETE CASCADE);").executeUpdate();

        conn.close();
    }

}
