package budjetointisovellus.dao;

import budjetointisovellus.dao.BudgetDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.Transaction;
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

    /**
     * Lukee tietokannasta tiedot ohjelmaan tietorakenteisiin. Mikäli
     * tietokantaa ei ole, niin luo sellaisen.
     *
     * @param file tekstitiedoston nimi.
     */
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

                Budget budget = findOne(budgetName);

                if (budgetList.contains(budget)) {
                    budget.setEvent(eventName, eventAmount);
                } else {
                    budget.setEvent(eventName, eventAmount);
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
    private Budget findOne(String name) {
        Budget budget = budgetList.stream()
                .filter(b -> b.getName().equals(name)).findFirst()
                .orElse(new Budget(name));

        return budget;
    }

    /**
     * Tallentaa tietorakenteesta tietokantaan. Käytetään muutoksien tekemisen
     * jälkeen.
     */
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(budgetFile)) {

            for (Budget one : budgetList) {
                ArrayList<Transaction> actions = one.getEvents();
                if (!actions.isEmpty()) {
                    for (Transaction action : actions) {
                        writer.write(one.getName() + ";" + action.getName() + ";"
                                + action.getAmount() + "\n");
                    }
                } else {
                    writer.write(one.getName() + ";" + "0" + ";" + "0" + "\n");
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
    public void create(Budget budget, String name, int amount) throws Exception {
        budget.setEvent(name, amount);
        save();
    }

    /**
     * Poistaa budjetista tietyn erän ja kutsuu tietokantaan tallentamista.
     *
     * @param budget haettu budjetti-olio.
     * @param eventName erän nimi.
     */
    public void remove(Budget budget, String eventName) throws Exception {
        budget.getEvents().remove(new Transaction(eventName, 0));
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