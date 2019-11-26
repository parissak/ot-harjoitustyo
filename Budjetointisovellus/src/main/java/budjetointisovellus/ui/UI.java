package budjetointisovellus.ui;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.BudgetController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import sun.awt.X11.XConstants;

public class UI {

    private Scanner scanner;
    private BudgetController budgetController;

    public UI(BudgetController budgetController, Scanner scanner) {
        this.budgetController = budgetController;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("**Welcome to Budgeting App**");
        System.out.println("****************************");

        while (true) {
            System.out.println("");
            System.out.println("Choose one of the following:");
            System.out.println("****************************");
            System.out.println("1: add a budget");
            System.out.println("2: remove a budget");
            System.out.println("3: print all budgets");
            System.out.println("4: add an expence or an income for one budget");
            System.out.println("5: view events of one budget");
            System.out.println("6: remove an event from budget");
            System.out.println("7: close the app");
            System.out.println("");

            System.out.print("> ");
            String pick = scanner.nextLine();

            if (pick.equals("1")) {
                addBudget();
            }

            if (pick.equals("2")) {
                removeBudget();
            }

            if (pick.equals("3")) {
                printBudgets();
            }

            if (pick.equals("4")) {
                addEventAndEntry();
            }

            if (pick.equals("5")) {
                printBudgetEvents();
            }

            if (pick.equals("6")) {
                removeEvent();
            }

            if (pick.equals("7")) {
                System.out.println("**Closing Budgeting App**");
                break;
            }
        }
    }

    private void addBudget() {
        String name = "";

        while (name.isEmpty()) {
            System.out.print("Insert name: ");
            name = scanner.nextLine();
        }
        budgetController.addBudget(name);
    }

    private void removeBudget() {
        String budgetName = subAskBudget();

        budgetController.removeBudget(budgetName);

    }

    private void printBudgets() {
        System.out.println("Existing budgets with corresponding balance: ");
        for (Budget budget : budgetController.getBudgets()) {
            System.out.println(budget);
        }
    }

    private void addEventAndEntry() {
        String budgetName = subAskBudget();

        System.out.println("Choose a name for event: ");
        System.out.print("> ");
        String eventName = scanner.nextLine();

        System.out.println("Add income or expense (with negative (-) sign)");
        System.out.print("> ");
        int amount = Integer.parseInt(scanner.nextLine());

        budgetController.addEventToBudget(budgetName, eventName, amount);

    }

    private void printBudgetEvents() {
        String budgetName = subAskBudget();
        subPrintEvents(budgetName);
    }

    private void removeEvent() {
        String budgetName = subAskBudget();
        subPrintEvents(budgetName);

        System.out.println("Choose an event");
        String eventName = scanner.nextLine();

        budgetController.removeBudgetEvent(budgetName, eventName);
    }

    private String subAskBudget() {
        printBudgets();
        System.out.println();
        System.out.println("Choose a budget");
        System.out.print("> ");
        String name = scanner.nextLine();

        return name;
    }

    private void subPrintEvents(String budgetName) {
        Map<String, Integer> map = budgetController.getBudgetEvents(budgetName);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ", " + value);
        }
        System.out.println("");

    }

}
