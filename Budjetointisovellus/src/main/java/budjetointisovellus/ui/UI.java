package budjetointisovellus.ui;

import budjetointisovellus.dao.DBBudgetDao;
import budjetointisovellus.dao.DBTransactionDao;
import budjetointisovellus.dao.DBUserDao;
import budjetointisovellus.domain.Budget;
import budjetointisovellus.domain.BudgetService;
import budjetointisovellus.domain.Transaction;

import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Käyttöliittymästä vastaava luokka
 */
public class UI extends Application {

    private BudgetService service;

    //käsiteltävä budjetti, voisi siirtää parametriksi
    private Budget budget;
    //summaan liittyvä label
    private Label sumLabel = new Label();

    private Stage stage;
    private VBox budgetNodes;
    private VBox entryNodes;

    private Scene entryScene;
    private Scene loginScene;
    private Scene budgetScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {

        DBUserDao userDao = new DBUserDao();
        DBBudgetDao budgetDao = new DBBudgetDao(userDao);
        DBTransactionDao transactionDao = new DBTransactionDao(budgetDao);
        this.service = new BudgetService(budgetDao, userDao, transactionDao);
    }

    /**
     * Käyttöliittymän käynnistysmetodi. Tarjoaa budjettien lisäämisen.
     *
     * @param primaryStage ikkunaa kuvaava Stage-olio
     */
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        //login scene
        VBox loginPane = new VBox(10);

        HBox userPane = new HBox(20);
        userPane.setPadding(new Insets(30));
        TextField userField = new TextField();
        setTextFieldWidth(userField);
        userField.setPromptText("Insert username");
        Button logInButton = new Button("Login");
        userPane.getChildren().addAll(userField, logInButton);

        Label createMessage = new Label();
        createMessage.setPadding(new Insets(0, 0, 0, 30));

        logInButton.setOnAction((event) -> {
            String logUser = userField.getText();

            if (validateStringField(logUser)) {
                if (service.logIn(logUser)) {
                    budgetScene();
                } else {
                    createMessage.setText("No such user exists");
                }
                userField.clear();
            }
        });

        HBox newUserPane = new HBox(20);
        newUserPane.setPadding(new Insets(30));
        TextField newUserField = new TextField();
        setTextFieldWidth(newUserField);
        newUserField.setPromptText("Insert name");
        Button createButton = new Button("Create new user");

        createButton.setOnAction((event) -> {
            String newUserName = newUserField.getText();

            if (validateStringField(newUserName)) {
                if (service.createUser(newUserName)) {
                    createMessage.setText("User created succesfully");
                } else {
                    createMessage.setText("Username already in use");
                }
                newUserField.clear();
            }
        }
        );

        newUserPane.getChildren().addAll(newUserField, createButton);

        loginPane.getChildren().addAll(userPane, newUserPane, createMessage);
        Scene loginScene = new Scene(loginPane, 500, 300);

        this.stage.setTitle("Budgeting App");
        this.stage.setScene(loginScene);
        this.stage.show();
    }

    public void budgetScene() {
        TextField nameField = new TextField();
        nameField.setPromptText("Insert name");
        setTextFieldWidth(nameField);
        Button submitButton = new Button("Add budget");
        Button logOut = new Button("Log out");

        ScrollPane budgetScroll = new ScrollPane();
        BorderPane mainPane = new BorderPane(budgetScroll);
        budgetScene = new Scene(mainPane, 500, 300);

        HBox menu = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        menu.getChildren().addAll(nameField, spacer, submitButton, logOut);
        menu.setPadding(new Insets(20, 20, 20, 20));

        submitButton.setOnAction((event) -> {
            if (validateStringField(nameField.getText())) {
                service.createBudget(nameField.getText());
                redrawBudgets();
            }
            nameField.clear();
        });

        logOut.setOnAction((event) -> {
            start(this.stage);

        });

        this.budgetNodes = new VBox(10);
        this.budgetNodes.setMaxWidth(350);
        this.budgetNodes.setMinWidth(280);
        redrawBudgets();

        budgetScroll.setContent(budgetNodes);
        mainPane.setTop(menu);

        stage.setTitle("Budgeting App");
        stage.setScene(budgetScene);
        stage.show();
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
            service.removeBudget(budget);
            redrawBudgets();
        });

        entryButton.setOnAction(e -> {
            entryScene(budget);
        });

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
            service.removeTransaction(this.budget, transaction);
            redrawEntries();
            updateBudgetSum();
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

        ScrollPane entryScroll = new ScrollPane();
        BorderPane entryPane = new BorderPane(entryScroll);

        HBox components = new HBox(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Insert name");
        TextField amountField = new TextField();
        amountField.setPromptText("Insert amount");
        Button addEntryButton = new Button("Add entry");
        Button previousButton = new Button("Go Back");

        setTextFieldWidth(nameField);
        setTextFieldWidth(amountField);
        components.getChildren().addAll(nameField, amountField, addEntryButton, previousButton);
        components.setPadding(new Insets(20, 20, 20, 20));

        sumLabel.setPadding(new Insets(20, 20, 20, 20));
        updateBudgetSum();

        addEntryButton.setOnAction(eb -> {
            if (validateStringField(nameField.getText()) && validateNumberField(amountField.getText())) {
                service.createTransaction(budget, nameField.getText(),
                        Integer.valueOf(amountField.getText()));
                redrawEntries();
                updateBudgetSum();
            }
            nameField.clear();
            amountField.clear();
        });

        previousButton.setOnAction(eb -> {
            stage.setScene(budgetScene);
            redrawBudgets();
        });

        this.entryNodes = new VBox(10);
        this.entryNodes.setMaxWidth(350);
        this.entryNodes.setMinWidth(280);
        redrawEntries();

        entryPane.setTop(components);
        entryPane.setBottom(sumLabel);
        entryScroll.setContent(this.entryNodes);
        entryScene = new Scene(entryPane, 500, 300);
        stage.setScene(entryScene);
    }

    /**
     * Päivittää budjettien Nodet.
     */
    public void redrawBudgets() {
        budgetNodes.getChildren().clear();

        List<Budget> budgetList = service.getUserBudgets();
        budgetList.forEach(budget -> {
            budgetNodes.getChildren().add(createBudgetNode(budget));
        });
    }

    /**
     * Päivittää budjetin erien Nodet.
     */
    private void redrawEntries() {
        entryNodes.getChildren().clear();

        List<Transaction> list = service.getBudgetTransactions(this.budget);
        list.forEach(Transaction -> {
            entryNodes.getChildren().add(createEntryNode(Transaction));
        });
    }

    private void setTextFieldWidth(TextField field) {
        field.setMaxWidth(130);
    }

    /**
     * Päivittää budjetin summan.
     */
    private void updateBudgetSum() {
        if (this.budget.getBalance() < 0) {
            sumLabel.setTextFill(Color.RED);
        } else {
            sumLabel.setTextFill(Color.BLACK);
        }
        this.sumLabel.setText("Total: " + budget.getBalance());
    }

    @Override
    public void stop() {
        System.out.println("Closing App");
    }

    private boolean validateStringField(String text) {
        if (text.isEmpty() || text.length() > 15) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Name cannot be empty or over 15 characters");
            errorAlert.show();
            return false;
        }
        return true;
    }

    private boolean validateNumberField(String toBeParsed) {
        if (!toBeParsed.matches("^-?[1-9]?[0-9]+$") || toBeParsed.isEmpty()) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Amount must only contain digits");
            errorAlert.show();
            return false;
        }
        return testNumberLenght(toBeParsed);
    }

    private boolean testNumberLenght(String number) {
        if (number.length() > 7) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Amount cannot be over seven digits");
            errorAlert.show();
            return false;
        }
        return true;
    }
}
