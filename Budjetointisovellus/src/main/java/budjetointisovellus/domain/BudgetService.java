package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.UserDao;
import budjetointisovellus.domain.Budget;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public boolean logIn(String username) {
        User user = null;

        try {
            user = userDao.findByUsername(username);
        } catch (SQLException ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
        }

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
     * Pyytää rajapintaa luomaan uuden budjetin.
     *
     * @name budjettia kuvaava nimi käyttäjän syöttämässä muodossa.
     */
    public boolean createBudget(String name) {
        Budget budget = new Budget(name, loggedIn);

        try {
            budgetDao.create(budget);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean createUser(String name) {
        try {
            if (userDao.findByUsername(name) != null) {
                return false;
            }
            User user = new User(name);
            userDao.create(user);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Pyytää rajapintaa poistamaan budjetin.
     *
     * @budgetName käyttäjän syöttämä budjetin nimi.
     */
    public boolean removeBudget(Budget budget) {
        try {
            budgetDao.remove(budget);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    /**
     * Pyytää rajapinnalta budjetit.
     *
     * @return palauttaa haetut budjetit.
     */
    public List<Budget> getBudgets() {
        try {
            return budgetDao.getBudgets()
                    .stream()
                    .filter(t -> t.getUser().equals(loggedIn))
                    .collect(Collectors.toList());
        } catch (SQLException ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<Budget>();
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

    public int balance(Budget budget) {
        try {
            return budgetDao.getBudgets()
                    .stream()
                    .filter(u -> u.getUser().equals(loggedIn))
                    .filter(b -> b.getName().equals(budget.getName()))
                    .mapToInt(t -> t.getBalance()).sum();
        } catch (SQLException ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Pyytää rajapinnalta tiettyyn budjettiin liittyvät erät.
     *
     * @name käyttäjän syöttämä budjetin nimi.
     * @return saadut erät listana.
     */
    public List<Transaction> getBudgetEvents(String name) {
        List<Transaction> list = new ArrayList<>();

        try {
            list = budgetDao.getTransactions(loggedIn, name);
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Pyytää rajapintaa poistamaan budjetin tietyn erän.
     *
     * @budgetName käyttäjän syöttämä budjetin nimi.
     * @name käyttäjän syöttämä erän nimi.
     */
    public boolean removeBudgetEvent(String budgetName, Transaction transaction) {
        try {
            for (Budget one : budgetDao.getBudgets()) {
                if (one.getName().equals(budgetName) && one.getUser().equals(loggedIn)) {
                    budgetDao.removeTransaction(one, transaction);
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
