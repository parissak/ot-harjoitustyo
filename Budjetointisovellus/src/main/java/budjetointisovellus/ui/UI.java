package budjetointisovellus.ui;

import budjetointisovellus.dao.FileBudgetDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.domain.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Käyttöliittymästä vastaava luokka
 */
public class UI extends Application {

    private BudgetService service;

    private Budget budget;

    private Stage stage;
    private VBox budgetNodes;
    private VBox entryNodes;

    private Scene entryScene;
    private Scene mainScene;

    /**
     * Apumetodi, mikä palauttaa totuusarvon toiminnan onnistumisesta riippuen.
     */
    private void testAction(Boolean test) {
        if (test == true) {
            System.out.println("Action successful");
        } else {
            System.out.println("Action failed");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        String budgetFile = "budgets.txt";

        FileBudgetDao budgetDao = new FileBudgetDao(budgetFile);
        this.service = new BudgetService(budgetDao);
    }

    /**
     * Käyttöliittymän käynnistysmetodi. Tarjoaa budjettien lisäämisen.
     *
     * @param primaryStage ikkunaa kuvaava Stage-olio
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        //main scene
        TextField textField = new TextField();
        textField.setPromptText("Insert name");
        Button submitButton = new Button("Add budget");
        BorderPane mainPane = new BorderPane();

        HBox menuPane = new HBox(10);
        menuPane.getChildren().addAll(textField, submitButton);
        menuPane.setPadding(new Insets(20, 20, 20, 20));

        submitButton.setOnAction((event) -> {
            testAction(service.create(textField.getText()));
            textField.clear();
            redrawBudgets();
        });

        this.budgetNodes = new VBox(10);
        this.budgetNodes.setMaxWidth(350);
        this.budgetNodes.setMinWidth(280);
        redrawBudgets();

        mainPane.setTop(menuPane);
        mainPane.setCenter(budgetNodes);
        mainScene = new Scene(mainPane, 500, 300);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Luo yhteen budjettiin littyvän Noden. Tarjoaa budjetin poistamisen ja
     * siirtymisen sen erien muokkaamiseen.
     *
     * @param budget käyttäjän valitsema budjetti
     *
     * @return budjettiin liityvä Node
     */
    public Node createBudgetNode(Budget budget) {
        HBox hBox = new HBox(10);
        Label label = new Label(budget.toString());
        label.setMinHeight(28);
        Button removeButton = new Button("Remove budget");
        Button entryButton = new Button("Edit entries");

        removeButton.setOnAction(e -> {
            service.removeBudget(budget.getName());
            redrawBudgets();
        });

        entryButton.setOnAction(e -> {
            entryScene(budget);
        }
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.setPadding(new Insets(0, 5, 0, 5));
        hBox.getChildren().addAll(label, spacer, removeButton, entryButton);

        return hBox;
    }

    /**
     * Luo budjettiin littyvän erän Noden. Tarjoaa toiminnallisuuden erän
     * poistamiseen.
     *
     * @param transaction budjettiin liittyvä erä
     *
     * @return erään liityvä Node
     */
    public Node createEntryNode(Transaction transaction) {
        HBox hBox = new HBox(10);
        Label label = new Label(transaction.toString());
        Button removeButton = new Button("Remove");
        label.setMinHeight(28);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.setPadding(new Insets(0, 5, 0, 5));
        hBox.getChildren().addAll(label, spacer, removeButton);

        removeButton.setOnAction(eb -> {
            service.removeBudgetEvent(budget.getName(), transaction.getName());
            redrawEntries();
        });

        return hBox;
    }

    /**
     * Budjettiin liityvien erien näyttäminen. Tarjoaa toiminnallisuuden erän
     * luomiseen.
     *
     * @param budget käyttäjän valitsema budjetti
     *
     */
    public void entryScene(Budget budget) {
        this.budget = budget;
        BorderPane entryPane = new BorderPane();

        HBox components = new HBox(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Insert name");
        TextField amountField = new TextField();
        amountField.setPromptText("Insert amount");
        Button addEntryButton = new Button("Add entry");
        Button previousButton = new Button("Go Back");

        components.getChildren().addAll(nameField, amountField, addEntryButton, previousButton);
        components.setPadding(new Insets(20, 20, 20, 20));

        addEntryButton.setOnAction(eb -> {
            service.addEventToBudget(budget.getName(), nameField.getText(),
                    Integer.parseInt(amountField.getText()));
            nameField.clear();
            amountField.clear();
            redrawEntries();
        });

        previousButton.setOnAction(eb -> {
            stage.setScene(mainScene);
            redrawBudgets();
        });

        this.entryNodes = new VBox(10);
        this.entryNodes.setMaxWidth(350);
        this.entryNodes.setMinWidth(280);
        redrawEntries();

        entryPane.setTop(components);
        entryPane.setCenter(entryNodes);
        entryScene = new Scene(entryPane, 500, 300);
        stage.setScene(entryScene);
    }

    /**
     * Päivittää budjettien Nodet.
     */
    public void redrawBudgets() {
        budgetNodes.getChildren().clear();

        List<Budget> budgetList = service.getBudgets();
        budgetList.forEach(budget -> {
            budgetNodes.getChildren().add(createBudgetNode(budget));
        });
    }

    /**
     * Päivittää budjetin erien Nodet.
     */
    private void redrawEntries() {
        entryNodes.getChildren().clear();

        List<Transaction> list = service.getBudgetEvents(this.budget.getName());
        list.forEach(Transaction -> {
            entryNodes.getChildren().add(createEntryNode(Transaction));
        });

    }
}