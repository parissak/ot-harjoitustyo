package budjetointisovellus.domain;

import budjetointisovellus.dao.BudgetDao;
import java.util.ArrayList;
import java.util.List;

public class FakeBudgetDao implements BudgetDao {

    ArrayList<Budget> budgets;

    public FakeBudgetDao() {
        this.budgets = new ArrayList<>();
    }

    @Override
    public void create(Budget budget) throws Exception {
        this.budgets.add(budget);
    }

    @Override
    public void createTransaction(Budget budget, String name, int amount) throws Exception {
        budget.setTransaction(name, amount);
    }

    @Override
    public void removeTransaction(Budget budget, String eventName) throws Exception {
        budget.getTransactions().remove(new Transaction(eventName, 0));
    }

    @Override
    public void remove(Budget budget) throws Exception {
        this.budgets.remove(budget);
    }

    @Override
    public List<Budget> getBudgets() {
        return budgets;
    }

}