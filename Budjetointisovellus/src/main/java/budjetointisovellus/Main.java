package budjetointisovellus;

import budjetointisovellus.dao.FileBudgetDao;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.ui.UI;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        String budgetFile = "budgets.txt";
        
        FileBudgetDao budgetDao = new FileBudgetDao(budgetFile);
        
        BudgetService service = new BudgetService(budgetDao);
        
        UI ui = new UI(service, scanner);
        
        ui.start();

    }
}
