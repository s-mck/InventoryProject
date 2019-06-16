package gameproject;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.*;

/**
 * GUI Class that contains main()
 * 
 * @author Sarah McKinnon
 * @version 2.0
 * Assignment: Final Project
 * Program: Systems Analyst
 * Date started: March 24, 2018
 */
public class GameGUI_V2 extends Application {
    
    Stage mainStage;
    BorderPane root = new BorderPane();
    
    // Large buttons for main page
    Button viewAllBtn = new Button("View All Games");
    Button searchBtn1 = new Button("Search Games");
    
    // Buttons on list page
    Button addBtn = new Button("Add Game");
    Button searchBtn2 = new Button("Search Games");
    Button deleteBtn = new Button("Delete Game");
    Button resetBtn = new Button("Reset");
    Button exitBtn = new Button("Quit");
    
    // Search Page buttons
    Button submitBtn = new Button("Submit");
    Button cancelBtn = new Button("Cancel");
    
    // Delete page buttons
    Button confirmBtn = new Button("Confirm");
    Button cancelBtn2 = new Button("Cancel");
    
    // No match search results
    Button okBtn = new Button("OK");
 
    File inputFile = new File("game.dat");
    GameList list = new GameList();
    TableView<Game> table = new TableView<>();
    ObservableList<Game> obsList = FXCollections.observableArrayList();
    
    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        
        // Main Window =======================================================

        HBox btnContainer = new HBox(8);
        btnContainer.getChildren().addAll(formatLargeBtn(viewAllBtn),formatLargeBtn(searchBtn1));
        viewAllBtn.setId("viewAllBtn");
        searchBtn1.setId("searchBtn1");
        btnContainer.setSpacing(100);
        btnContainer.setAlignment(Pos.CENTER);
        
        root.setTop(createHeader("Video Game Organizer"));
        root.setCenter(btnContainer);
        root.setId("root");
     
        Scene scene1 = new Scene(root, 1000, 600);
        scene1.getStylesheets().add(GameGUI_V2.class.getResource("gameCSS.css").toExternalForm());

        mainStage.setMaximized(true);
        mainStage.setScene(scene1);
        mainStage.show();
        mainStage.setOnCloseRequest(e -> {
            obsList.setAll(list.getGameList());
            list.writeToFile(obsList);
        });

        // List/Records Window ==================================================================

        table.setEditable(true);
        table.setMaxSize(1050, 400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setId("tableView");
        setTableColumns();
    
        if(inputFile.canRead()){
            obsList.setAll(list.readFromFile());
            table.setItems(obsList);
        }
        else
            System.out.println("Error - file not found!");

        // Search Window ================================================================================
    
        Text searchText = new Text("Enter title or keyword here:");
        searchText.setId("searchText");
        TextField searchInput = new TextField();
        searchInput.setMinSize(75, 30);
        
        HBox btnHolder2 = new HBox(30, submitBtn, cancelBtn); 
        submitBtn.setId("submit");
        cancelBtn.setId("cancel");
        btnHolder2.setAlignment(Pos.CENTER);
        btnHolder2.setId("btnHolder2");
        
        FlowPane centerHolder = new FlowPane(Orientation.VERTICAL, searchText, searchInput, btnHolder2);
        centerHolder.setAlignment(Pos.CENTER);
        centerHolder.setVgap(40);
        
        BorderPane searchRoot = new BorderPane();
        searchRoot.setTop(createHeader("Search Games"));
        searchRoot.setCenter(centerHolder);
        searchRoot.setId("searchRoot");
        Scene searchScene = new Scene(searchRoot);
        
        Stage searchStage = new Stage();
        searchStage.setScene(searchScene);
        searchStage.setHeight(500);
        searchStage.setWidth(600);
        searchScene.getStylesheets().add(GameGUI_V2.class.getResource("gameCSS.css").toExternalForm());
    
        // Delete confirmation window =======================================================================
    
        confirmBtn.setId("confirm");
        cancelBtn2.setId("cancel2");
        HBox btnContainer3 = new HBox(30, confirmBtn, cancelBtn2);
        btnContainer3.setAlignment(Pos.CENTER);
        FlowPane deleteRoot = new FlowPane(Orientation.VERTICAL);
        deleteRoot.setAlignment(Pos.CENTER);
        deleteRoot.setVgap(50);
        deleteRoot.setId("deleteRoot");
        Text deleteText = new Text("Are you sure you want to delete this game?");
        deleteText.setId("deleteText");
        deleteRoot.getChildren().addAll(deleteText, btnContainer3);

        Scene deleteScene = new Scene(deleteRoot);
        Stage deleteStage = new Stage();
        deleteStage.setScene(deleteScene);
        deleteStage.setHeight(500);
        deleteStage.setWidth(600);
        deleteScene.getStylesheets().add(GameGUI_V2.class.getResource("gameCSS.css").toExternalForm());
    
        // Button Event Handlers ================================================================================
        viewAllBtn.setOnAction(e -> {
            root.setCenter(table);
            root.setLeft(navBtnHolder());
            root.setTop(createHeader("View All Games"));
            root.setBottom(addGameHolder());
        });
    
        searchBtn1.setOnAction(e -> {
            searchStage.show();
            root.setCenter(table);
            root.setLeft(navBtnHolder());
            root.setTop(createHeader("View All Games"));
            root.setBottom(addGameHolder());
        });
    
        searchBtn2.setOnAction(e -> {
            searchInput.clear();
            searchRoot.setCenter(centerHolder);
            searchStage.show();
            });
    
        deleteBtn.setOnAction(e -> deleteStage.show());
    
        resetBtn.setOnAction(e -> obsList.setAll(list.getGameList()));
    
        submitBtn.setOnAction(e -> {
            ArrayList<Game> tempList = list.searchByTitle(searchInput.getText());
            if(tempList.isEmpty() == false){
                obsList.setAll(tempList);
                searchInput.clear();
                searchStage.close();
            }
            else {
                searchRoot.setCenter(changeSearchScene());
            }
        });
    
        cancelBtn.setOnAction(e -> searchStage.close());
        
        cancelBtn2.setOnAction(e -> deleteStage.close());
        
        okBtn.setOnAction(e -> searchStage.close());
    
        exitBtn.setOnAction(e -> {
            obsList.setAll(list.getGameList());
            list.writeToFile(obsList);
            System.exit(0);
        });
    
        confirmBtn.setOnAction(e -> {
            Game tempGame = table.getSelectionModel().getSelectedItem();
            list.deleteGame(tempGame.getGameID());
            obsList.setAll(list.getGameList());
            deleteStage.close();
        });
    }
    
    // Creates the header used on all screens
    private HBox createHeader(String headerTitle){
        Text titleText = new Text(headerTitle);
        titleText.setId("titleText");
        HBox titleBox = new HBox(titleText);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setId("headerBox");
        return titleBox;
    }
    // Formats large buttons on main screen
    private Button formatLargeBtn(Button largeButton){
        largeButton.setMaxSize(600, 100);
        largeButton.setId("largeBtn");
        return largeButton;
    }
    // Creates container for all buttons on left side of table screen
    private VBox navBtnHolder(){
        VBox btnHolder = new VBox(50, searchBtn2, deleteBtn, resetBtn, exitBtn);
        searchBtn2.setId("searchBtn2");
        resetBtn.setId("resetBtn");
        exitBtn.setId("exitBtn");
        deleteBtn.setId("deleteBtn");
        btnHolder.setAlignment(Pos.CENTER);
        btnHolder.setId("btnHolder");
        return btnHolder;
    }
    // Creates new pane to swap with current search pane if there are no matches
    private FlowPane changeSearchScene(){

        Text noMatchText = new Text("Sorry, no games match that keyword.");
        noMatchText.setId("noMatchText");
        okBtn.setId("okBtn");
        HBox btnHolder3 = new HBox(okBtn);
        btnHolder3.setAlignment(Pos.CENTER);
        FlowPane noMatchRoot = new FlowPane(Orientation.VERTICAL, noMatchText, btnHolder3);
        noMatchRoot.setAlignment(Pos.CENTER);
        noMatchRoot.setVgap(40);
        return noMatchRoot;
    }
    
    // Creates bottom container used for adding games
    private HBox addGameHolder(){
        
        TextField addTitle = new TextField();
        addTitle.setPromptText("Enter Title");
        addTitle.setId("addTitle");
        
        TextField addGenre = new TextField();
        addGenre.setPromptText("Enter Genre");
        addGenre.setId("addGenre");
        
        TextField addPlatform = new TextField();
        addPlatform.setPromptText("Enter Platform");

        TextField addYear = new TextField();
        addYear.setPromptText("Enter Year");
        addYear.setId("addYear");
        
        TextField addAch = new TextField();
        addAch.setPromptText("Enter Achievements");
        addAch.setId("addAchievements");
        
        TextField addPlays = new TextField();
        addPlays.setPromptText("Enter Playthroughs");
        addPlays.setId("addPlays");
        
        addBtn.setOnAction(e -> {
            try {
                int newYear = Integer.parseInt(addYear.getText());
                int newAch = Integer.parseInt(addAch.getText());
                int newPlays = Integer.parseInt(addPlays.getText());
                Game newGame = new Game(addTitle.getText(), addGenre.getText(),
                                addPlatform.getText(), newYear, newAch, newPlays);
                list.addGame(newGame);
                obsList.add(newGame);
                addTitle.clear();
                addGenre.clear();
                addPlatform.clear();
                addYear.clear();  
                addAch.clear();
                addPlays.clear();
            }
            catch(NumberFormatException ex){
                System.out.println("Error parsing numbers");
            }
        });
        
        HBox outerHolder = new HBox(30, addTitle, addGenre, addPlatform, addYear, addAch, addPlays, addBtn);
        addBtn.setId("addBtn");
        outerHolder.setId("outerHolder");
        return outerHolder;
    }
 
    // Create and fill table columns with data
    private void setTableColumns(){
        TableColumn<Game, String> titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        titleCol.setMinWidth(200);
        titleCol.setId("titleCol");
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titleCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTitle(e.getNewValue());
        });
        
        TableColumn<Game, String> genreCol = new TableColumn("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        genreCol.setMinWidth(150);
        genreCol.setId("genreCol");
        genreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genreCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setGenre(e.getNewValue());
        });
        
        TableColumn<Game, String> platformCol = new TableColumn("Platform");
        platformCol.setCellValueFactory(new PropertyValueFactory<>("Platform"));
        platformCol.setMinWidth(100);
        platformCol.setId("platformCol");
        platformCol.setCellFactory(TextFieldTableCell.forTableColumn());
        platformCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlatform(e.getNewValue());
        });
        
        TableColumn<Game, Integer> yearCol = new TableColumn("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("Year"));
        yearCol.setMinWidth(70);
        yearCol.setId("yearCol");
        yearCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yearCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setYear(e.getNewValue());
        }); 
        
        TableColumn<Game, Integer> achCol = new TableColumn("Achievements");
        achCol.setCellValueFactory(new PropertyValueFactory<>("Achievements"));
        achCol.setMinWidth(70);
        achCol.setId("achCol");
        achCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        achCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setAchievements(e.getNewValue());
        });
        
        TableColumn<Game, Integer> playsCol = new TableColumn("Plays");
        playsCol.setCellValueFactory(new PropertyValueFactory<>("Plays"));
        playsCol.setMinWidth(50);
        playsCol.setId("playsCol");
        playsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        playsCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlays(e.getNewValue());
        }); 
        table.getColumns().addAll(titleCol, genreCol, platformCol, yearCol, achCol, playsCol);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

    