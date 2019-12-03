package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    public boolean create(String name) {
        Budget budget = new Budget(name);

        try {
            budgetDao.create(budget);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

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

    public List<Budget> getBudgets() {
        return budgetDao.getBudgets();
    }

    public boolean addEventToBudget(String budget, String name, int amount) {
        try {
            for (Budget b : budgetDao.getBudgets()) {
                if (b.getName().equals(budget)) {
                    budgetDao.create(b, name, amount);
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return false;

    }

    public HashMap<String, Integer> getBudgetEvents(String name) {
        HashMap<String, Integer> map = new HashMap<>();

        for (Budget budget : budgetDao.getBudgets()) {
            if (budget.getName().equals(name)) {
                map = budget.getEvents();
            }
        }
        return map;
    }

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
