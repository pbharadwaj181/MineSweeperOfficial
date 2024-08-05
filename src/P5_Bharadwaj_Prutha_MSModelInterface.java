/*
    Name:   Prutha Bharadwaj
    Date:   3/25/24
    Period: 5

    Is this lab fully working?  
    If not, explain: 
*/

import java.util.ArrayList;

public interface P5_Bharadwaj_Prutha_MSModelInterface {
    public int getNumRows();
    public int getNumCols();
    public void setValueAt(int row, int col, int value);
    public void addListener(Object listener);
    public void removeListener(Object listener);
    public void setGrid(int [][] gridValues);
    public void flipTile(int row, int col, boolean revealOnly); // recursively reveals
    public void flagTile(int row, int col);
    public void unFlagTile(int row, int col);
    public boolean isGameOver(int row, int col);
    public boolean isGameWon();
    public boolean isGameLost();
    public void initializeBoard(int numRows, int numCols, int numMines);
    public boolean isFlagged(int row, int col);
    public int numNeighborMines(int row, int col);
    public int getValueAt(int row , int col);
    public boolean isTileMine(int row, int col);
    public int totalMines();
    public void resetBoard();
    public boolean inBounds(int row, int col);
    public void setMines(int numMines);
    public boolean isTouched(int row, int col);
}
