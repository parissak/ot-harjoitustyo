package budjetointisovellus;

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

    public List<String> getBudgets() {
        List<String> list = new ArrayList<>();

        for (Budget budget : budgetList) {
            int balance = calculateBalance(budget);
            list.add(budget.getName() + ", " + balance);
        }
        return list;
    }

    public void addEntryToBudget(String budget, String name, int amount) {
        for (Budget b : budgetList) {
            if (b.getName().equals(budget)) {
                b.setEvent(name, amount);
            }
        }
    }

    private int calculateBalance(Budget budget) {
        int balance = 0;

        for (Map.Entry<String, Integer> entry : budget.getEvents().entrySet()) {
            balance += entry.getValue();
        }
        return balance;
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
}
