package budjetointisovellus;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetController controller = new BudgetController();
        UI ui = new UI(controller, scanner);

        ui.start();

    }
}
