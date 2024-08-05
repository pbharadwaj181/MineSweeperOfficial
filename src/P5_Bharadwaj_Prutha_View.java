/*
    Name:   Prutha Bharadwaj
    Date:   4/11/24
    Period: 5

    Is this lab fully working?  
    If not, explain:
 */

import javax.management.timer.Timer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class P5_Bharadwaj_Prutha_View extends Application{
    //private int minesRemaining = 5;
    static P5_Bharadwaj_Prutha_MinesweeperModel model;
    private static int numRows=10;
    private static int numCols=10;
    private static int numOfMines=5;
    ImageView imageView;
    GridPane pane;
    MenuItem begGame;
    MenuItem intGame;
    MenuItem expGame;
    MenuItem custGame;
    MenuItem exit;
    MenuItem about;
    MenuItem howToPlay;
    MenuItem numMines;
    MenuItem setGridSize;
    VBox root;
    //int time = 0;
    Label mine1Lbl;
    Label time;
    long startTime = System.currentTimeMillis();
    private MyAnimationTimer timer = new MyAnimationTimer();

    public static void main(String[] args) {
        launch(args);
    }
    private static void initialize(int row, int col, int num) {
        numRows = row;
        numCols = col;
        numOfMines = num;
        int[][]  board = new int[row][col];
        model = new P5_Bharadwaj_Prutha_MinesweeperModel(board);
        model.initializeBoard(row,  col,  numOfMines);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Minesweeper");

        initialize(numRows, numCols, numOfMines);
        root = new VBox();
        MenuBar menubar = new MenuBar();

        Menu gameMenu = new Menu("Game");
        
        begGame = new MenuItem("New Beginner Game");
        begGame.setOnAction(new MyGameMenuHandler());
        
        intGame = new MenuItem("New Intermediate Game");
        intGame.setOnAction(new MyGameMenuHandler());
        
        expGame = new MenuItem("New Expert Game");
        expGame.setOnAction(new MyGameMenuHandler());
        
        custGame = new MenuItem("New Custom Game");
        custGame.setOnAction(new MyGameMenuHandler());
        
        exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(event.getSource() == exit) {
                    System.exit(1);
                    stage.close();
                }
                
            }
            
        });

        Menu optionsMenu = new Menu("Options");
        numMines = new MenuItem("Set Number of Mines");
        numMines.setOnAction(new MyOptionsMenuHandler());
        setGridSize = new MenuItem("Set Grid Size");
        setGridSize.setOnAction(new MyOptionsMenuHandler());

        Menu helpMenu = new Menu("Help");
        howToPlay = new MenuItem("How To Play");
        howToPlay.setOnAction(new MyHelpMenuHandler());
        about = new MenuItem("About");
        about.setOnAction(new MyHelpMenuHandler());

        gameMenu.getItems().addAll(begGame, intGame, expGame, custGame, exit);
        optionsMenu.getItems().addAll(numMines, setGridSize);
        helpMenu.getItems().addAll(howToPlay, about);
        menubar.getMenus().addAll(gameMenu, optionsMenu, helpMenu);

        pane = new GridPane();
        pane.setPadding(new Insets(20,0,0,0));
        pane.setAlignment(Pos.CENTER);
        //Image img = new Image("file:bin/minesweeper_images/blank.gif");
        initializeGrid(10,10);
        model.setMines(3);
        numOfMines = 3;

        BorderPane mines = new BorderPane();

        mines.setPadding(new Insets(20,20,0,20));
        mine1Lbl = new Label("Mines Remaining: " + numOfMines);
        mine1Lbl.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
        time = new Label("0");
        timer.start();
        //updateTime();
        time.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
        time.setAlignment(Pos.CENTER);
        mine1Lbl.setAlignment(Pos.CENTER);
        mines.setLeft(mine1Lbl);
        mines.setRight(time);


        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        root.getChildren().addAll(menubar, pane, mines);
        stage.show();

    }

    public void updateView(int row, int col, ImageView imageView) {  

        for(int i = 0; i<model.getNumRows(); i++) {
            for(int j = 0; j<model.getNumCols(); j++) {
                int numNeighbors = model.getValueAt(i, j);
                //if(model.isFlagged(row, col) && i == row && j==col) {
                    if (numNeighbors == P5_Bharadwaj_Prutha_MinesweeperModel.FLAGGED_MINE_PRESENT) {
                        model.setImage(i, j, new Image("file:bin/minesweeper_images/bomb_flagged.gif"));
                        continue;
                    }else if (numNeighbors == P5_Bharadwaj_Prutha_MinesweeperModel.FLAGGED_MINE_NOT_PRESENT){
                        model.setImage(i, j, new Image("file:bin/minesweeper_images/bomb_flagged.gif"));
                        continue;
                        //model.setImage(i, j, new Image("file:bin/minesweeper_images/blank.gif"));
                        //model.setImage(i, j, new Image("file:bin/minesweeper_images/face_win.gif"));
                    }
                    //imageView.setImage(new Image("file:bin/minesweeper_images/bomb_flagged.gif"));
                //} 
                if(model.getValueAt(i, j) == model.UNTOUCHED) {
                    model.setImage(i, j, new Image("file:bin/minesweeper_images/blank.gif"));
                    continue;
                    
                } else {
                    
                    if (numNeighbors >= 0) {
                        if (numNeighbors == 0) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_0.gif"));
                            continue;
                        }else if(numNeighbors == 1) {
                            model.setImage(i, j,new Image("file:bin/minesweeper_images/num_1.gif"));
                            continue;
                        } else if(numNeighbors == 2) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_2.gif"));
                            continue;
                        } else if(numNeighbors == 3) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_3.gif"));
                            continue;
                        } else if(numNeighbors == 4) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_4.gif"));
                            continue;
                        } else if(numNeighbors == 5) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_5.gif"));
                            continue;
                        } else if(numNeighbors == 6) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_6.gif"));
                            continue;
                        } else if(numNeighbors == 7) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_7.gif"));
                            continue;
                        } else if(numNeighbors == 8) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/num_8.gif"));
                            continue;
                        } 
                    }else if (model.getValueAt(i, j) == model.REVEALED) {
                        model.setImage(i, j, new Image("file:bin/minesweeper_images/num_0.gif"));
                        continue;
                    }
                    else {
                        if(model.getValueAt(i, j) == model.TOUCHED_MINE && model.isGameOver(i, j) && row == i && col == j) {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/bomb_revealed.gif"));
                        } else if (i==row && j == col){
                            if (model.getValueAt(i, j) == model.TOUCHED_MINE) {
                                model.setImage(i, j, new Image("file:bin/minesweeper_images/blank.gif"));
                                model.setValueAt(row, col, model.MINE);
                                numOfMines--;
                                updateText();
                            }else {
                                model.setImage(i, j, new Image("file:bin/minesweeper_images/num_0.gif"));
                            }
                        }/*
                        else {
                            model.setImage(i, j, new Image("file:bin/minesweeper_images/blank.gif"));
                        }*/
                    }
                }
            }
        }
    }

    private class MyMouseHandler implements EventHandler<MouseEvent>{
        int row;
        int col;
        ImageView imageview;

        public MyMouseHandler(int row, int col, ImageView image) {
            this.row = row;
            this.col = col;
            this.imageview = image;
        }

        @Override
        public void handle(MouseEvent event) {
            if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                

                if(event.getButton() != MouseButton.PRIMARY) {
                    System.out.println("Mouse clicked on: for Flagged " + row + " col" + col);
                    //model.flagTile(row, col);
                    if (model.getValueAt(row, col) == model.MINE) {
                        model.setValueAt(row, col, model.FLAGGED_MINE_PRESENT);
                        //model.setImage(row, col, new Image("file:bin/minesweeper_images/bomb_flagged.gif"));
                        //numOfMines--;
                    }else if(model.getValueAt(row, col) == model.FLAGGED_MINE_PRESENT) {
                        model.setValueAt(row, col, model.MINE);
                        updateText();
                        //model.setValueAt(row, col, model.FLAGGED_MINE_NOT_PRESENT);
                        //model.setImage(row, col, new Image("file:bin/minesweeper_images/blank.gif"));
                    } else if (model.getValueAt(row, col) == model.UNTOUCHED) {
                        model.setValueAt(row, col, model.FLAGGED_MINE_NOT_PRESENT);
                        //model.setImage(row, col, new Image("file:bin/minesweeper_images/bomb_flagged.gif"));
                    }else if(model.getValueAt(row, col) == model.FLAGGED_MINE_NOT_PRESENT) {
                        numOfMines++;
                        updateText();
                        model.setValueAt(row, col, model.UNTOUCHED);
                        //model.setImage(row, col, new Image("file:bin/minesweeper_images/blank.gif"));
                    }
                    
                } else {
                    
                    if(model.getValueAt(row, col) != model.MINE) {
                        try {
                        model.flipTile(row, col, false);
                        System.out.println("Finished flip tile");
                        }catch (Exception ee) {
                            System.out.println("Mouse clicked on: not Mine " + row + " col" + col + " Exception" + ee.getMessage());
                        }
                        System.out.println("Mouse clicked on: not Mine " + row + " col" + col);
                        //model.setValueAt(row, col, model.numNeighborMines(row, col));
                    } else {
                        System.out.println("Mouse clicked on: mine " + row + " col" + col);
                        //model.setImage(row, col, new Image("file:bin/minesweeper_images/bomb_revealed.gif"));
                        model.setGameOver(row, col);
                    }
                    
                }
                System.out.println("Updating the view");
                updateView(row, col, imageView);
            }
        }

    } 
    
    private class MyOptionsMenuHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource() == numMines) {
                TextInputDialog input = new TextInputDialog();
                input.setHeaderText("How many mines would you like?");
                input.showAndWait();
                String answer = input.getEditor().getText();
                int mines = Integer.parseInt(answer);
                
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                model.initializeBoard(numRows, numCols, mines);
                initialize(numRows,numCols,mines);
                initializeGrid(numRows,numCols);
                updateText();
            }
            
            if(event.getSource() == setGridSize) {
                TextInputDialog input1 = new TextInputDialog();
                input1.setHeaderText("How many rows would you like?");
                input1.showAndWait();
                String answer1 = input1.getEditor().getText();
                int rows = Integer.parseInt(answer1);
                
                TextInputDialog input2 = new TextInputDialog();
                input2.setHeaderText("How many columns would you like?");
                input2.showAndWait();
                String answer2 = input2.getEditor().getText();
                int cols = Integer.parseInt(answer2);
                
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                model.initializeBoard(rows, cols, numOfMines);
                initialize(rows,cols,numOfMines);
                initializeGrid(rows,cols);
                updateText();
            }
            
        }
        
    }
    
    private class MyGameMenuHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(event.getSource() == begGame) {
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                model.initializeBoard(8, 8, 10);
                initialize(8,8,10);
                initializeGrid(8,8);
                updateText();
            } else if(event.getSource() == intGame) {
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                model.initializeBoard(16,16,40);
                initialize(16,16,40);
                initializeGrid(16,16);
                updateText();
            } else if(event.getSource() == expGame) {
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                model.initializeBoard(16,31,99);
                initialize(16,31,99);
                initializeGrid(16,31);
                updateText();
            } else if(event.getSource() == custGame) {
                TextInputDialog input1 = new TextInputDialog();
                input1.setHeaderText("How many rows would you like?");
                input1.showAndWait();
                String answer = input1.getEditor().getText();
                int rows = Integer.parseInt(answer);
                
                TextInputDialog input2 = new TextInputDialog();
                input2.setHeaderText("How many cols would you like?");
                input2.showAndWait();
                String answer2 = input2.getEditor().getText();
                int cols = Integer.parseInt(answer2);
                
                TextInputDialog input3 = new TextInputDialog();
                input3.setHeaderText("How many mines would you like");
                input3.showAndWait();
                String answer3 = input3.getEditor().getText();
                int mines = Integer.parseInt(answer3);
                
                removeFromScreen();
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                model.initializeBoard(rows, cols, mines);
                initialize(rows, cols, mines);
                initializeGrid(rows, cols);
                updateText();
            } 
            
        }
        
    }
    
    private class MyHelpMenuHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            if(event.getSource() == howToPlay) {
                Stage stage = new Stage();
                VBox root = new VBox();
                Scene scene = new Scene(root, 1000, 700);
                stage.setTitle("How to Play");
                
                HBox play = new HBox();
                play.setAlignment(Pos.CENTER);
                play.setPadding(new Insets(20,20,20,20));
                Image smile1 = new Image("file:bin/minesweeper_images/face_smile.gif");
                ImageView img1 = new ImageView(smile1);
                ImageView img2 = new ImageView(smile1);
                
                Label lbl1 = new Label("How to Play");
                lbl1.setPadding(new Insets(0,20,0,20));
                lbl1.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
                play.getChildren().addAll(img1, lbl1, img2);
                
                VBox overview = new VBox();
                overview.setAlignment(Pos.CENTER);
                Label lbl2 = new Label("Overview");
                lbl2.setPadding(new Insets(0,20,10,20));
                lbl2.setAlignment(Pos.CENTER);
                lbl2.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
                String s1 = "Minesweeper is a game of logic and probability. The game begins with a grid of cells some of ";
                String s2 = "which contain hidden mines. The goal of the game is to reveal all the cells that are safe by clicking";
                String s3 = "on them. A cell is safe if it doesn't contain a mine. Once the user has revealed all safe cells, the ";
                String s4 = "game is won!";
                Label lbl3 = new Label(s1 + "\n" + s2 + "\n" + s3 + "\n" + s4);
                lbl3.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                lbl3.setAlignment(Pos.CENTER);
                overview.getChildren().addAll(lbl2, lbl3);
                
                VBox types = new VBox();
                Label str = new Label("Types of Cells");
                str.setPadding(new Insets(20,0,20,0));
                str.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
                types.setAlignment(Pos.CENTER);
                types.setPadding(new Insets(20,0,20,0));
                HBox blank = new HBox();
                blank.setAlignment(Pos.TOP_CENTER);
                ImageView blankImg = new ImageView("file:bin/minesweeper_images/blank.gif");
                Label blankLbl = new Label("The game begins with unrevealed cells");
                blankLbl.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                blankLbl.setPadding(new Insets(0,20,0,20));
                blank.getChildren().addAll(blankImg, blankLbl);
                
                HBox one = new HBox();
                one.setPadding(new Insets(20,0,20,0));
                one.setAlignment(Pos.CENTER);
                ImageView oneImg = new ImageView("file:bin/minesweeper_images/num_1.gif");
                Label oneLbl = new Label("If you click an unrevealed cell you may uncover a number cell. Number cells range from 1 to \n8 and represent the number of neighboring cells containing mines.");
                oneLbl.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                oneLbl.setPadding(new Insets(0,20,0,20));
                one.getChildren().addAll(oneImg, oneLbl);
                
                HBox flag = new HBox();
                flag.setPadding(new Insets(20,0,20,0));
                flag.setAlignment(Pos.CENTER);
                ImageView flagImg = new ImageView("file:bin/minesweeper_images/bomb_flagged.gif");
                Label flagLbl = new Label("If you suspect that a cell contains a mine you can flag it by right-clicking the cell. ");
                flagLbl.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                flagLbl.setPadding(new Insets(0,20,0,20));
                flag.getChildren().addAll(flagImg, flagLbl);
                types.getChildren().addAll(str, blank, one, flag);
                
                VBox button = new VBox();
                button.setAlignment(Pos.CENTER);
                Button okBtn = new Button("OK");
                okBtn.setAlignment(Pos.CENTER);
                okBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(event.getSource() == okBtn) {
                            stage.close();
                        }
                        
                    }
                    
                });
                button.getChildren().add(okBtn);
                root.getChildren().addAll(play, overview, types, button);
                stage.setScene(scene);
                stage.show();
            } else if(event.getSource() == about) {
                Stage stage = new Stage();
                VBox root = new VBox();
                Scene scene = new Scene(root, 400, 200);
                stage.setTitle("About");
                
                VBox texts = new VBox();
                texts.setAlignment(Pos.CENTER);
                
                Label lbl1 = new Label("Minesweeper");
                lbl1.setAlignment(Pos.CENTER);
                lbl1.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
                Label lbl2 = new Label("version 1.0");
                lbl2.setPadding(new Insets(10,0,10,0));
                lbl2.setAlignment(Pos.CENTER);
                lbl2.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                Label lbl3 = new Label("By: Prutha Bharadwaj");
                lbl3.setFont(Font.font("mukta mahee", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                lbl3.setAlignment(Pos.CENTER);
                texts.getChildren().addAll(lbl1, lbl2, lbl3);
                
                VBox button = new VBox();
                button.setAlignment(Pos.CENTER);
                Button okBtn = new Button("OK");
                okBtn.setAlignment(Pos.CENTER);
                okBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(event.getSource() == okBtn) {
                            stage.close();
                        }
                        
                    }
                    
                });
                button.getChildren().add(okBtn);
                root.getChildren().addAll(texts, button);
                
                stage.setScene(scene);
                stage.show();
            }
            
        }
        
    }
    
    public void updateTime() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - startTime;
        time.setText("" + timeElapsed/1000);
    }
    
    private class MyAnimationTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            now = System.currentTimeMillis();
            long timeElapsed = now - startTime;
            time.setText("" + timeElapsed/1000);
            
        }
        
    }
    
    public void initializeGrid(int rows, int cols) {
        Image img = new Image("file:bin/minesweeper_images/blank.gif");

        for(int i = 0; i<rows; i++) {
            for(int j = 0; j<cols; j++) {
                imageView = new ImageView(img); 
                model.setImageView(i, j, imageView);
                imageView.setOnMousePressed(new MyMouseHandler(i, j, imageView));
                pane.add(imageView, j, i);
            }
        }
    }
    
    public void updateText() {
        //Label mine1Lbl = new Label("Mines Remaining: " + numOfMines);
        mine1Lbl.setText("Mines Remaining: " + numOfMines);
    }
    
    public void removeFromScreen() {
        for(int i = 0; i <numRows; i++) {
            for(int j = 0; j<numCols; j++) {
                pane.getChildren().remove(imageView);
            }
        }
    }
    
    public void gameOver(int row, int col) {
        if(model.isGameOver(row, col)) {
            if(model.isGameWon()) {
                Label won = new Label("YOU WON!");
                root.getChildren().add(won);
            }
            
            if(model.isGameLost()) {
                Label lost = new Label("YOU LOST!");
                root.getChildren().add(lost);
            }
        }
        
    }
}
