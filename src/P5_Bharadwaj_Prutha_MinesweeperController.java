/*
    Name:   Prutha Bharadwaj
    Date:   3/25/24
    Period: 5

    Is this lab fully working?  
    If not, explain: 
*/
import java.util.Scanner;

public class P5_Bharadwaj_Prutha_MinesweeperController {
    private int numRows;
    private int numCols;
    private int numOfMines;
    private P5_Bharadwaj_Prutha_MinesweeperModel model;
    
    public P5_Bharadwaj_Prutha_MinesweeperController(int row, int col, int num) {
        this.numRows = row;
        this.numCols = col;
        this.numOfMines = num;
        int[][]  board = new int[row][col];
        this.model = new P5_Bharadwaj_Prutha_MinesweeperModel(board);
        model.initializeBoard(row,  col,  numOfMines);
    }
    
    public void printBoard() {
        System.out.print("What the player sees\n  ");
        for (int i = 0; i < numCols; i++) {
            System.out.print(i + " ");
        }
        
        System.out.println("");
        for (int i=0; i < numRows; i++) {
            System.out.print(i + " ");
            
            for (int j=0; j < numCols; j++) {
                if (model.isFlagged(i, j)) {
                    System.out.print("F");
                }else if (model.isTouched(i, j)) {
                    if (model.isTileMine(i, j)) {
                        System.out.print("*");
                    }else {
                        System.out.print(model.getValueAt(i, j));
                    }
                }else {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("There are " + model.totalMines() + " mines remaining");
        System.out.println("\n " + "Lower Board");
        System.out.print("  ");
        for(int i = 0; i<numCols; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        for(int i = 0; i<numRows; i++) {
            System.out.print(i + " ");
            for(int j = 0; j<numCols; j++) {
                if(model.isTileMine(i, j)) {
                    System.out.print("* ");
                } else if(model.isFlagged(i, j)) {
                    System.out.print("F ");
                } else {
                    System.out.print(model.numNeighborMines(i, j) + " ");
                }
            }
            System.out.println();
        }
        
    }
    
    public void play() {
        System.out.println("Welcome to Minesweeper!");
        Scanner in = new Scanner(System.in);
        int r = 0;
        int c = 0;
        while (!model.isGameOver(r, c)) {
            printBoard();
            String answer = "";
            
            do {
                try {
                    System.out.print("Would you like continue? (y/n)");
                    answer = in.next();
                    if(answer.equalsIgnoreCase("n")) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } while (!answer.equalsIgnoreCase("y"));
            
            if(answer.equalsIgnoreCase("y")) {
                int row;
                int col;
                System.out.println("Flag or reveal? (f/r)");
                String ans = in.next();
                System.out.print("Enter the row:");
                row = in.nextInt();
                System.out.print("Enter the Col:");
                col = in.nextInt();
                if (ans != null && ans.equalsIgnoreCase("f")) {
                    model.flagTile(row, col);
                } else if(ans != null && ans.equalsIgnoreCase("r")) {
                    if(!model.isGameOver(row, col)) {
                        System.out.println("they picked reveal");
                        model.flipTile(row, col, true);
                    } else {
                        in.close();
                    }
                } else {
                    model.setGameOver(row, col);
                    if (!model.isGameOver(row, col)) {
                        model.flipTile(row, col, true);
                    } else {
                        in.close();
                    }
                } 
            } else {
                break;
            }
        } 
        
    }
}
