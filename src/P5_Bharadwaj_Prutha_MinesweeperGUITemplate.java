/*
    Name:   Prutha Bharadwaj
    Date:   3/24/24
    Period: 5

    Is this lab fully working?  
    If not, explain: 
*/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class P5_Bharadwaj_Prutha_MinesweeperGUITemplate extends Application{
    private int minesRemaining = 5;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Minesweeper");
        
        VBox root = new VBox();
        MenuBar menubar = new MenuBar();
        
        Menu gameMenu = new Menu("Game");
        MenuItem begGame = new MenuItem("New Beginner Game");
        MenuItem intGame = new MenuItem("New Intermediate Game");
        MenuItem expGame = new MenuItem("New Expert Game");
        MenuItem custGame = new MenuItem("New Custom Game");
        MenuItem exit = new MenuItem("Exit");
        
        Menu optionsMenu = new Menu("Options");
        MenuItem numMines = new MenuItem("Set Number of Mines");
        MenuItem setGridSize = new MenuItem("Set Grid Size");
        
        Menu helpMenu = new Menu("Help");
        MenuItem howToPlay = new MenuItem("How To Play");
        MenuItem about = new MenuItem("About");
  
        gameMenu.getItems().addAll(begGame, intGame, expGame, custGame, exit);
        optionsMenu.getItems().addAll(numMines, setGridSize);
        helpMenu.getItems().addAll(howToPlay, about);
        menubar.getMenus().addAll(gameMenu, optionsMenu, helpMenu);
        
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20,0,0,0));
        pane.setAlignment(Pos.CENTER);
        Image img = new Image("file:bin/minesweeper_images/blank.gif");
        for(int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                ImageView imageView = new ImageView(img);
                imageView.setImage(img);
                pane.add(imageView, i, j);
            }
        }
        
        BorderPane mines = new BorderPane();
        
        mines.setPadding(new Insets(20,20,0,20));
        Label mine1Lbl = new Label("Mines Remaining: " + minesRemaining);
        Label time = new Label("Time Elapsed: 0:18");
        time.setAlignment(Pos.CENTER);
        mine1Lbl.setAlignment(Pos.CENTER);
        mines.setLeft(mine1Lbl);
        mines.setRight(time);
        
        
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        root.getChildren().addAll(menubar, pane, mines);
        stage.show();
        
    }
}
