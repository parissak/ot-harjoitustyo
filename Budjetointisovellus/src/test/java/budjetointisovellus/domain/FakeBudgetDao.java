package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import java.util.ArrayList;
import java.util.List;

public class FakeBudgetDao implements BudgetDao{
    ArrayList<Budget> budgets;

    public FakeBudgetDao() {
        this.budgets = new ArrayList<>();
    }

    @Override
    public void create(Budget budget) throws Exception {
        this.budgets.add(budget);
    }

    @Override
    public void create(Budget budget, String name, int amount) throws Exception {
        budget.setEvent(name, amount);
    }

    @Override
    public void remove(Budget budget, String eventName) throws Exception {
        budget.getEvents().remove(eventName);
    }

    @Override
    public void remove(Budget budget) throws Exception {
        this.budgets.remove(budget);
    }

    @Override
    public List<Budget> getBudgets() {
         return budgets;
    }

    void create(String name) {
        this.budgets.add(new Budget(name));
    }
    
}
