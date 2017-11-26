package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application 
{
    private HBox root, custRoot, custDataRoot;
    private VBox leftRoot;

    private String[] buttonIcons = {"|<", "<", ">", ">|", "Save", "New"};
    private Button[] buttons;
    private int Index;

    private String[] labelNames = {"First Name", "Last Name", "Email", "Phone No", "Rating", "Service Provider"};
    private Label[] labels;

    private TextField[] textFields;
    private GridPane [] gridPane;
    private TextArea [] textAreas;

    private Boolean addingNewCustomer;
    // Start
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        initOperators();
         Customer.setUpCustomers();
         primaryStage.setTitle("Hello World");
         Scene scene = new Scene(root, 1160, 540);
        // scene.getStylesheets().add("sample/style.css");
       // scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);

        getCustomer();
        primaryStage.show();
       // primaryStage.setOnCloseRequest( e -> Customer.onClose());
    }
    public Main()
    {
        int arraySize = 6; // can get from Settings.txt if complexity increases
        Index = 0;

        buttons    = new Button   [arraySize];
        labels     = new Label    [arraySize];
        textFields = new TextField[arraySize];

        arraySize = 2; // can get from Settings.txt if complexity increases

        gridPane  = new GridPane[arraySize];
        textAreas = new TextArea[arraySize];

        addingNewCustomer = false;
    }
    //// Button Handles
    // < or >
    private void getNextCustomer(Boolean bool)
    {
        if (addingNewCustomer) {addingNewCustomer = false;}
            if (bool)
            {
                if (Index < Customer.getCustomerCount() - 1) {Index++;}
            }
            else if (Index == 0) {}
            else     {Index--;}

            getCustomer();
    }
    // |< or >|
    private void farCustomer(Boolean bool)
    {
        if (addingNewCustomer) { addingNewCustomer = false;}
            if (bool) {
                Index = Customer.getCustomerCount() - 1;
            } else {
                Index = 0;
            }

            getCustomer();
    }
    private void getCustomer()
    {
        String[]  currentCust = Customer.getCustomer(Index);
        updateFields(currentCust);
    }
    private void updateFields(String[] customer)
    {
        for (int i = 0; i < 6; i++)
        {
            textFields[i].setText( customer[i]);
           // System.out.println("Cust: " + customer[i]);
        }
        textAreas[0].setText(customer[6]);
    }
    // Save
    private void updateCustomer()
    {
        String[] newCustomer = new String[6];

        for (int i = 0; i < textFields.length; i++)
        {
            newCustomer[i] = textFields[i].getText();
        }

        if (addingNewCustomer)
        {
            Customer.addNewCustomer(newCustomer);
            Index = Customer.getCustomerCount() - 1;
            addingNewCustomer = false;
        }
            Customer.updateCustomer(newCustomer, Index);
           getCustomer();
    }
    private void newCustomer()
    {
        System.out.println("Display Blank");
        String[] tempCust = new String[7];
        updateFields(tempCust);
        addingNewCustomer = true;
    }
    // called to update gui with customer based on Index Location

    // Creating initial Gui Components
    // Recursion  Button, Labels, TextFields | Initializes, Names.
    // Then -> Initializes GridPanes [Rec] | add feature -> Initializes Text Areas [rex] | Added features -> added to GUI node
    private void initOperators()
    {
        int fontSize = 12;

        if (Index < labels.length)
        {
            buttons[Index] = new Button(buttonIcons[Index]);
            buttons[Index].setMinWidth(40);
            labels[Index] = new Label(labelNames[Index]);
            labels[Index].setFont(Font.font("Tahoma ", FontWeight.NORMAL, fontSize));

            textFields[Index] = new TextField();

            Index++;
            initOperators();
        } else // Called when buttons & labels have been initialized
            {
                Index = 0;
                initButtonFunc();
                createGridPane();
            }
    }
    private void initButtonFunc()
    {
        buttons[0].setOnAction(event -> {farCustomer    (false);});
        buttons[1].setOnAction(event -> {getNextCustomer(false);});
        buttons[2].setOnAction(event -> {getNextCustomer(true) ;});
        buttons[3].setOnAction(event -> {farCustomer    (true) ;});

        buttons[4].setOnAction(event -> updateCustomer());
        buttons[4].setPrefWidth(60);
        buttons[5].setOnAction(event -> newCustomer   ());
        buttons[5].setPrefWidth(60);
    }
    private void createGridPane()
    {
       if (Index < gridPane.length)
       {
           gridPane[Index] = new GridPane();
           gridPane[Index].setAlignment(Pos.CENTER_LEFT);
           gridPane[Index].setVgap(5);
           gridPane[Index].setHgap(10);
           gridPane[Index].setPadding(new Insets(10, 30, 10, 10));
           gridPane[Index].getStyleClass().add("grid");

           textAreas[Index] = new TextArea();

           Index++;
           createGridPane();
       }else // Called when grid pane & text areas are initialized
           {
               Index = 0;
               initGrid();
               initTextAreas();
           }
    }
    private void initGrid()
    {
        int x, y, xPad, yPad, r_GapPad;
        x = 0; y = 0; xPad = 1; yPad = 1;  r_GapPad = 3;

        gridPane[Index].add(labels    [0], x                    , y       , 1, 1);
        gridPane[Index].add(textFields[0], x + xPad             , y       , 1, 1);
        gridPane[Index].add(labels    [1], x                    , y + yPad, 1, 1);
        gridPane[Index].add(textFields[1], x + xPad             , y + yPad, 1, 1);
        gridPane[Index].add(labels    [2], x + r_GapPad         , y       , 1, 1);
        gridPane[Index].add(textFields[2], x + r_GapPad + xPad  , y       , 1, 1);
        gridPane[Index].add(labels    [3], x + r_GapPad         , y + yPad, 1, 1);
        gridPane[Index].add(textFields[3], x + r_GapPad + xPad  , y + yPad, 1, 1);

        Index++;

        gridPane[Index].add(labels    [4], 0, 0, 1, 1);
        gridPane[Index].add(textFields[4], 0, 2, 1, 1);

        gridPane[Index].add(labels    [5], 1, 0, 1, 1);
        gridPane[Index].add(textFields[5], 1, 2, 1, 1);

        gridPane[Index].add(buttons   [0], 2, 0, 1, 1);
        gridPane[Index].add(buttons   [1], 3, 0, 1, 1);
        gridPane[Index].add(buttons   [2], 6, 0, 1, 1);
        gridPane[Index].add(buttons   [3], 7, 0, 1, 1);
        gridPane[Index].add(buttons   [4], 2, 2, 2, 1);
        gridPane[Index].add(buttons   [5], 6, 2, 2, 1);

        Index = 0;
    }
    private void initTextAreas()
    {
        textAreas[Index].setPrefHeight(380) ;
        textAreas[Index].setWrapText  (true);
        //textAreas[Index].getStyleClass().add("text-area");

        Index++;

        textAreas[Index].setPrefHeight(500);
        textAreas[Index].setPrefWidth (600);
       // textAreas[Index].getStyleClass().add("text-area");
        textAreas[Index].setPadding(new Insets(5, 5, 5, 5));
        Index = 0;
        initGuiNodes();
    }
    private void initGuiNodes()
    {
        root = new HBox();
        root.getStyleClass().add("hbox1");
        root.setPadding(new Insets(5, 5, 5, 5));

        leftRoot = new VBox();
        leftRoot.getStyleClass().add("hbox");

        custRoot = new HBox();
        custRoot.getStyleClass().add("hbox");

        custDataRoot = new HBox();
        custDataRoot.getStyleClass().add("hbox");

        custRoot    .getChildren().add(gridPane[0]);
        custDataRoot.getChildren().add(gridPane[1]);

        leftRoot.getChildren().addAll(custRoot, custDataRoot, textAreas[0]);
        VBox.setMargin(custRoot    , new Insets(5, 5, 5, 5));
        VBox.setMargin(custDataRoot, new Insets(0, 5, 5, 5));
        VBox.setMargin(textAreas[0], new Insets(0, 5, 5, 5));

        HBox.setMargin(leftRoot    , new Insets(5, 10, 5, 5));
        HBox.setMargin(textAreas[1], new Insets(5, 5, 5, 5));
        root.getChildren().addAll(leftRoot, textAreas[1]);
    }
} // End of Class




