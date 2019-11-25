package budjetointisovellus.domain;

import budjetointisovellus.domain.Budget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetController {

    private List<Budget> budgetList;

    public BudgetController() {
        this.budgetList = new ArrayList<>();
    }

    public void addBudget(String name) {
        Budget budget = new Budget(name);
        this.budgetList.add(budget);
    }

    public void removeBudget(String budgetName) {
        Budget budget = null;

        for (Budget b : budgetList) {
            if (b.getName().equals(budgetName)) {
                budget = b;
            }
        }
        budgetList.remove(budget);
    }

    public List<Budget> getBudgets() {
        return budgetList;
    }

    public void addEventToBudget(String budget, String name, int amount) {
        for (Budget b : budgetList) {
            if (b.getName().equals(budget)) {
                b.setEvent(name, amount);
            }
        }
    }

    public HashMap<String, Integer> getBudgetEvents(String name) {
        HashMap<String, Integer> map = new HashMap<>();

        for (Budget budget : budgetList) {
            if (budget.getName().equals(name)) {
                map = budget.getEvents();
            }
        }
        return map;
    }

    public void removeBudgetEvent(String budgetName, String name) {

        for (Budget one : budgetList) {
            if (one.getName().equals(budgetName)) {
                one.getEvents().remove(name);
            }
        }
    }

}
