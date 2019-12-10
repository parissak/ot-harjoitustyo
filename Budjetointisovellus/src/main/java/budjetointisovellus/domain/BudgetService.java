package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sovelluslogiikasta vastaava luokka, joka välittää pyynnöt tietokantaa 
 * käsittelevälle rajapinnalle
 */
public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    /**
     * Pyytää rajapintaa luomaan uuden budjetin.
     *
     * @name budjettia kuvaava nimi käyttäjän syöttämässä muodossa.
     */
    public boolean create(String name) {
        Budget budget = new Budget(name);

        try {
            budgetDao.create(budget);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * Pyytää rajapintaa poistamaan budjetin.
     * 
     * @budgetName käyttäjän syöttämä budjetin nimi.
     */
    public boolean removeBudget(String budgetName) {
        try {
            for (Budget one : budgetDao.getBudgets()) {
                if (one.getName().equals(budgetName)) {
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
        return budgetDao.getBudgets();
    }

    /**
     * Pyytää rajapintaa lisäämään erän tiettyyn budjettiin.
     * 
     * @budget käyttäjän syöttämä budjetin nimi.
     * @name käyttäjän syöttämä erän nimi.
     * @amount käyttäjän syöttämä lukumäärä erälle.
     */
    public boolean addEventToBudget(String budget, String actionName, int amount) {
        try {
            for (Budget b : budgetDao.getBudgets()) {
                if (b.getName().equals(budget)) {
                    budgetDao.create(b, actionName, amount);
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
            if (budget.getName().equals(name)) {
                list = budget.getEvents();
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
                if (one.getName().equals(budgetName)) {
                    budgetDao.remove(one, name);
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}