package budjetointisovellus.dao;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
import budjetointisovellus.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Luokka vastaa tietokantaan ja tietorakenteeseen tallentamisesta ja tietojen
 * noutamisesta edellä mainituista kohteista.
 */
public class FileBudgetDao implements BudgetDao {

    public List<Budget> budgetList;
    private String budgetFile;
    private UserDao userDao;

    public FileBudgetDao(String file, UserDao users) throws Exception {
        this.budgetList = new ArrayList<>();
        this.budgetFile = file;
        this.userDao = users;
        readFile();
    }

    /**
     * Kutsutaan käynnistyksen yhteydessä. Lukee tietokannasta tiedot ohjelmaan 
     * tietorakenteisiin. Mikäli tietokantaa ei ole, niin luo sellaisen.
     */
    private void readFile() throws IOException {
        try {
            Scanner reader = new Scanner(new File(this.budgetFile));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                User user = userDao.getAll().stream().filter(u -> u.getUsername()
                        .equals(parts[0])).findFirst().orElse(new User(parts[0]));
                String budgetName = parts[1];
                String eventName = parts[2];
                Integer eventAmount = Integer.parseInt(parts[3]);

                Budget budget = findBudget(budgetName, user);

                if (eventAmount != 0) {
                    budget.setTransaction(eventName, eventAmount);
                }

                if (!budgetList.contains(budget)) {
                    budgetList.add(budget);
                }
            }

        } catch (Exception e) {
            System.out.println("Initializing new budget file");
            FileWriter writer = new FileWriter(new File(budgetFile));
            writer.close();
        }
    }

    /**
     * Apumetodi. Etsii tietorakenteesta olemassa olevan nimistä budjettia.
     * Mikäli sellaista ei ole, niin luo uuden budjetin. Lopulta palauttaa
     * budjetti-olion.
     *
     * @param name annettu budjetin nimi
     */
    private Budget findBudget(String budgetName, User user) {
        return budgetList.stream()
                .filter(b -> b.getName()
                .equals(budgetName))
                .filter(b -> b.getUser()
                .equals(user))
                .findFirst()
                .orElse(new Budget(budgetName, user));
    }

    /**
     * Tallentaa tietorakenteesta tietokantaan. Käytetään muutoksien tekemisen
     * jälkeen.
     */
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(budgetFile)) {

            for (Budget budget : budgetList) {
                ArrayList<Transaction> actions = budget.getTransactions();

                if (!actions.isEmpty()) {
                    for (Transaction action : actions) {
                        writer.write(budget.getUser().getUsername() + ";" + budget.getName() + ";" + action.getName() + ";"
                                + action.getAmount() + "\n");
                    }
                } else {
                    writer.write(budget.getUser().getUsername() + ";" + budget.getName() + ";" + "0" + ";" + "0" + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("error at file writing");
        }
    }

    /**
     * Lisää tietorakenteeseen budjetin ja kutsuu tietokantaan tallentamista.
     *
     * @param budget budjetti-olio.
     */
    @Override
    public void create(Budget budget) throws Exception {
        budgetList.add(budget);
        save();
    }

    /**
     * Lisää tietorakenteeseen erän budjetille ja kutsuu tietokantaan
     * tallentamista.
     *
     * @param budget haettu budjetti-olio.
     * @param name erän nimi.
     * @param amount erän määrä.
     */
    public void createTransaction(Budget budget, String name, int amount) throws Exception {
        budget.setTransaction(name, amount);
        save();
    }

    /**
     * Poistaa budjetista tietyn erän ja kutsuu tietokantaan tallentamista.
     *
     * @param budget haettu budjetti-olio.
     * @param eventName erän nimi.
     */
    public void removeTransaction(Budget budget, String eventName) throws Exception {
        budget.getTransactions().remove(new Transaction(eventName, 0));
        save();
    }

    /**
     * Poistaa budjetin ja kutsuu tietokantaan tallentamista.
     *
     * @param budget haettu budjetti-olio.
     */
    public void remove(Budget budget) throws Exception {
        budgetList.remove(budget);
        save();
    }

    /**
     * Palauttaa tietorakenteesta listan budjeteista.
     *
     * @return lista budjeteista.
     */
    @Override
    public List<Budget> getBudgets() {
        return budgetList;
    }

}
