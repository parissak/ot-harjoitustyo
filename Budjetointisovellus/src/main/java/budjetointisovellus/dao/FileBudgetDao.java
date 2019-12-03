package budjetointisovellus.dao;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileBudgetDao implements BudgetDao {

    public List<Budget> budgetList;
    private String budgetFile;

    public FileBudgetDao(String file) throws Exception {
        this.budgetList = new ArrayList<>();
        this.budgetFile = file;

        try {
            Scanner reader = new Scanner(new File(this.budgetFile));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                String budgetName = parts[0];
                String eventName = parts[1];
                Integer eventAmount = Integer.parseInt(parts[2]);

                Budget some = findOne(budgetName);

                if (budgetList.contains(some)) {
                    some.setEvent(eventName, eventAmount);
                } else {
                    some.setEvent(eventName, eventAmount);
                    budgetList.add(some);
                }
            }
        } catch (Exception e) {
            System.out.println("Initializing new budget file");
            FileWriter writer = new FileWriter(new File(budgetFile));
            writer.close();
        }
    }

    // submethod
    private Budget findOne(String name) {
        Budget budget = budgetList.stream()
                .filter(b -> b.getName().equals(name)).findFirst()
                .orElse(new Budget(name));

        return budget;
    }

    // Saves budgets to file
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(budgetFile)) {
            for (Budget one : budgetList) {

                if (!one.getEvents().isEmpty()) {
                    for (Map.Entry<String, Integer> entry : one.getEvents().entrySet()) {
                        String key = entry.getKey();
                        Integer value = entry.getValue();
                        writer.write(one.getName() + ";" + key + ";" + value + "\n");
                    }
                } else {
                    writer.write(one.getName() + ";" + "0" + ";" + "0" + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("error at file writing");
        }
    }

    // Adds a budget to list and saves to file
    @Override
    public void create(Budget budget) throws Exception {
        budgetList.add(budget);
        save();
    }

    // Creates event and adds it to budget
    public void create(Budget budget, String name, int amount) throws Exception {
        budget.setEvent(name, amount);
        save();
    }

    // Deletes event from budget and saves
    public void remove(Budget budget, String eventName) throws Exception {
        budget.getEvents().remove(eventName);
        save();
    }

    // Deletes budget and saves
    public void remove(Budget budget) throws Exception {
        budgetList.remove(budget);
        save();
    }

    // Returns list of budgets
    @Override
    public List<Budget> getBudgets() {
        return budgetList;
    }


}
