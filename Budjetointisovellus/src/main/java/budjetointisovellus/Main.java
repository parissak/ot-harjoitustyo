package budjetointisovellus;

import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.BudgetController;
import budjetointisovellus.ui.UI;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetController controller = new BudgetController();

        UI ui = new UI(controller, scanner);
        ui.start();

    }
}
