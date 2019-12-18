package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.Budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sovelluslogiikasta vastaava luokka, joka välittää pyynnöt tietokantaa
 * käsittelevälle rajapinnalle
 */
public class BudgetService {

    private BudgetDao budgetDao;
    private UserDao userDao;
    private User loggedIn;

    public BudgetService(BudgetDao budgetDao, UserDao userDao) {
        this.budgetDao = budgetDao;
        this.userDao = userDao;
    }

    /**
     * Pyytää rajapintaa luomaan uuden budjetin.
     *
     * @name budjettia kuvaava nimi käyttäjän syöttämässä muodossa.
     */
    public boolean createBudget(String name) {
        Budget budget = new Budget(name, loggedIn);

        try {
            budgetDao.create(budget);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean createUser(String name) {
        if (userDao.findByUsername(name) != null) {
            return false;
        }

        User user = new User(name);

        try {
            userDao.create(user);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean logIn(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }

        this.loggedIn = user;

        return true;
    }

    public void logOut() {
        this.loggedIn = null;
    }

    /**
     * Pyytää rajapintaa poistamaan budjetin.
     *
     * @budgetName käyttäjän syöttämä budjetin nimi.
     */
    public boolean removeBudget(String budgetName) {
        try {
            for (Budget one : budgetDao.getBudgets()) {
                if (one.getName().equals(budgetName) && one.getUser().equals(loggedIn)) {
                    budgetDao.remove(one);
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    /**
     * Pyytää rajapinnalta budjetit.
     *
     * @return palauttaa haetut budjetit.
     */
    public List<Budget> getBudgets() {
        return budgetDao.getBudgets()
                .stream()
                .filter(t -> t.getUser().equals(loggedIn))
                .collect(Collectors.toList());
    }

    /**
     * Pyytää rajapintaa lisäämään erän tiettyyn budjettiin.
     *
     * @budget käyttäjän syöttämä budjetin nimi.
     * @name käyttäjän syöttämä erän nimi.
     * @amount käyttäjän syöttämä lukumäärä erälle.
     */
    public boolean addTransactionToBudget(String budget, String actionName, int amount) {
        try {
            for (Budget b : budgetDao.getBudgets()) {
                if (b.getName().equals(budget) && b.getUser().equals(loggedIn)) {
                    budgetDao.createTransaction(b, actionName, amount);
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    /**
     * Pyytää rajapinnalta tiettyyn budjettiin liittyvät erät.
     *
     * @name käyttäjän syöttämä budjetin nimi.
     * @return saadut erät listana.
     */
    public ArrayList getBudgetEvents(String name) {
        ArrayList<Transaction> list = new ArrayList<>();

        for (Budget budget : budgetDao.getBudgets()) {
            if (budget.getName().equals(name) && budget.getUser().equals(loggedIn)) {
                list = budget.getTransactions();
            }
        }
        return list;
    }

    /**
     * Pyytää rajapintaa poistamaan budjetin tietyn erän.
     *
     * @budgetName käyttäjän syöttämä budjetin nimi.
     * @name käyttäjän syöttämä erän nimi.
     */
    public boolean removeBudgetEvent(String budgetName, String name) {
        try {
            for (Budget one : budgetDao.getBudgets()) {
                if (one.getName().equals(budgetName) && one.getUser().equals(loggedIn)) {
                    budgetDao.removeTransaction(one, name);
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
