package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.dao.TransactionDao;
import budjetointisovellus.dao.UserDao;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Sovelluslogiikasta vastaava luokka, joka välittää pyynnöt tietokantaa
 * käsittelevälle rajapinnalle
 */
public class BudgetService {

    private BudgetDao budgetDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private User loggedIn;

    public BudgetService(BudgetDao budgetDao, UserDao userDao, TransactionDao transactionDao) {
        this.budgetDao = budgetDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }

    /**
     * Asettaa käyttäjän sisäänkirjautuneeksi
     *
     * @param username käyttäjän nimimerkki
     * @return true jos nimimerkki on olemassa, muuten false
     */
    public boolean logIn(String username) {
        User user = null;

        try {
            user = userDao.findByUsername(username);
        } catch (SQLException ex) {
            return false;
        }

        if (user == null) {
            return false;
        }

        this.loggedIn = user;

        return true;
    }

    /**
     * Asettaa käyttäjän uloskirjautuneeksi
     */
    public void logOut() {
        this.loggedIn = null;
    }

    /**
     * Luo budjetti-olion ja pyytää rajapintaa tallentamaan sen kantaan.
     *
     * @name budjettia kuvaava nimi käyttäjän syöttämässä muodossa. return true
     * jos tallennus onnistuu, muuten false
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

    /**
     * Etsii nimimerkkiä kannasta ja jos sellaista ei löydy, luo käyttäjä-olion
     * ja pyytää rajapintaa tallentamaan sen kantaan.
     *
     * @name nimimerkki käyttäjän syöttämässä muodossa.
     * @return true jos nimimerkkiä ei löydy ja tallennus onnistuu, muuten
     * false.
     */
    public boolean createUser(String name) {
        try {
            if (userDao.findByUsername(name) != null) {
                return false;
            }
            User user = new User(name);
            userDao.create(user);
            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Pyytää rajapintaa poistamaan budjetin.
     *
     * @budget budjetti-olio.
     * @return true jos poistaminen onnistuu, muuten false.
     */
    public boolean removeBudget(Budget budget) {
        try {
            budgetDao.remove(budget);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Pyytää rajapinnalta käyttäjän budjetit ja budjettiin liittyvät erät ja
     * yhdistää nämä.
     *
     * @return palauttaa haetut budjetit listana tai budjettien puuttuessa tyhjä
     * lista.
     */
    public List<Budget> getUserBudgets() {
        try {
            List<Budget> budgets = budgetDao.getUserBudgets(loggedIn);
            for (Budget budget : budgets) {
                List<Transaction> actions
                        = transactionDao.getUserBudgetsTransactions(loggedIn, budget);
                budget.setTransactions(actions);
            }

            return budgets;
        } catch (SQLException ex) {
        }
        return new ArrayList<Budget>();
    }

    /**
     * Pyytää rajapintaa lisäämään erän tiettyyn budjettiin. Lisää myös
     * budjetti-olioon erän, jotta budjetin summa tulostuu oikein 
     * käyttöliittymässä.
     *
     * @budget käyttäjän valitsema budjetti.
     * @name käyttäjän syöttämä erän nimi.
     * @amount käyttäjän syöttämä lukumäärä erälle.
     * @return true jos lisääminen onnistuu, muuten false
     */
    public boolean createTransaction(Budget budget, String actionName, int amount) {
        budget.setTransaction(new Transaction(actionName, amount));
        try {
            transactionDao.create(budget, actionName, amount);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Pyytää rajapinnalta tiettyyn budjettiin liittyvät erät.
     *
     * @budget budjetti.
     * @return saadut erät listana tai erien puuttuessa tyhjä lista
     */
    public List<Transaction> getBudgetTransactions(Budget budget) {
        List<Transaction> list = new ArrayList<>();

        try {
            list = transactionDao.getUserBudgetsTransactions(loggedIn, budget);
            return list;
        } catch (SQLException ex) {
        }
        return list;
    }

    /**
     * Pyytää rajapintaa poistamaan tietyn budjetin tietyn erän. Poistaa myös
     * budjetti-oliolta erän, jotta budjetin summa tulostuu oikein 
     * käyttöliittymässä.
     *
     * @budget käyttäjän valitsema budjetti.
     * @transaction käyttäjän valitsema erä.
     * @return true jos poistaminen onnistui, muuten false
     */
    public boolean removeTransaction(Budget budget, Transaction transaction) {
        budget.removeTransaction(new Transaction(transaction.getName(), 
                transaction.getAmount()));
        
        try {
            transactionDao.remove(budget, transaction);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}
