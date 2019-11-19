package budjetointisovellus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
            System.out.println("1: add budget");
            System.out.println("2: print all budgets");
            System.out.println("3: add expence or income for budget");
            System.out.println("4: view events of one budget");
            System.out.println("5: close the app");
            System.out.println("");

            System.out.print("> ");
            String pick = scanner.nextLine();

            if (pick.equals("1")) {
                addBudget();
            }

            if (pick.equals("2")) {
                printBudgets();
            }

            if (pick.equals("3")) {
                addEventAndEntry();
            }

            if (pick.equals("4")) {
                viewEvents();
            }

            if (pick.equals("5")) {
                System.out.println("**Closing Budgeting App**");
                break;
            }
        }
    }

    private void addBudget() {
        System.out.print("Insert name: ");
        String name = scanner.nextLine();
        budgetController.addBudget(name);
    }

    private void printBudgets() {
        System.out.println("Existing budgets with corresponding balance: ");
        for (String budget : budgetController.getBudgets()) {
            System.out.println(budget);
        }
    }

    private void addEventAndEntry() {
        printBudgets();
        System.out.println();
        System.out.println("Choose a budget");
        System.out.print("> ");
        String budget = scanner.nextLine();

        System.out.println("Choose a name for event: ");
        System.out.print("> ");
        String name = scanner.nextLine();

        System.out.println("Add income or expense (with negative (-) sign)");
        System.out.print("> ");
        int amount = Integer.parseInt(scanner.nextLine());

        budgetController.addEntryToBudget(budget, name, amount);

    }

    private void viewEvents() {
        printBudgets();
        System.out.println();
        System.out.println("Choose a budget");
        System.out.print("> ");
        String budget = scanner.nextLine();

        Map<String, Integer> map = budgetController.getBudgetEvents(budget);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ", " + value);
        }

    }

}
