/*
    Name:   Prutha Bharadwaj
    Date:   3/25/24
    Period: 5

    Is this lab fully working?  
    If not, explain: 
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class P5_Bharadwaj_Prutha_MinesweeperModel implements P5_Bharadwaj_Prutha_MSModelInterface{
    private int[][] onTop;
    
    public static final int UNTOUCHED = -10;
    public static final int FLAGGED_MINE_PRESENT = -2;
    public static final int FLAGGED_MINE_NOT_PRESENT = -3;
    public static final int MINE = -1;
    public static final int TOUCHED_MINE = -4;
    public static final int VISITED = -5;
    public static final int REVEALED = -6;
    private static boolean gameOver = false;
    private static boolean gameLost = false;
    private static int numMines  = 0;
    private static int numRows =0;
    private static int numCols = 0;
    private static ImageView[][] imageView;
    
    public void setImageView(int row, int col, ImageView imgView) {
        if(imgView != null) {
            imageView[row][col] = imgView;
        }
    }
    
    public void setImage(int row, int col, Image img) {
        try {
            if (img != null) {
                imageView[row][col].setImage(img);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println ("Row=" + row + " col="+col + " ImageView Len=" + imageView.length + " ImageView col Length=" + imageView[0].length);
        }
    }

    public P5_Bharadwaj_Prutha_MinesweeperModel(int[][] onTop) {
        this.onTop = onTop;
        imageView = new ImageView[onTop.length][onTop[0].length];
    }
    @Override
    public int getNumRows() {
        return onTop.length;
    }

    @Override
    public int getNumCols() {
        return onTop[0].length;
    }

    @Override
    public void setValueAt(int row, int col, int value) {
        onTop[row][col] = value;

    }

    @Override
    public void addListener(Object listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListener(Object listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setGrid(int[][] gridValues) {
        this.onTop = gridValues;
    }

    //needs to recursively flip
    @Override
    public void flipTile(int row, int col, boolean revealOnly) {
        if(onTop[row][col] == UNTOUCHED) {
            if (!revealOnly) {
                onTop[row][col] = VISITED;
            }
            int numNeighbours = numNeighborMines(row, col);
            System.out.println("Num neighbours " + numNeighbours);
            if(numNeighbours == 0) {
                if (onTop[row][col] != VISITED) {
                    onTop[row][col] = REVEALED;
                }
                if(inBounds(row, col-1)) {
                    flipTile(row, col-1, true);
                } 
                if(inBounds(row+1, col-1)) {
                    flipTile(row+1, col-1, true);
                } 
                if(inBounds(row+1, col)) {
                    flipTile(row+1, col, true);
                } 
                if(inBounds(row+1, col-1)) {
                    flipTile(row+1, col-1, true);
                }
                if(inBounds(row, col-1)) {
                    flipTile(row, col-1, true);
                } 
                if(inBounds(row-1, col-1)) {
                    flipTile(row-1, col-1, true);
                } 
                if(inBounds(row-1, col)) {
                    flipTile(row-1, col, true);
                } 
                if(inBounds(row-1, col+1)) {
                    flipTile(row-1, col+1, true);
                } 
            } else {
                onTop[row][col] = numNeighbours;
            }
            
        } else if(onTop[row][col] == MINE && !revealOnly) {
            System.out.println("Mine present, finishing the game");
            setGameOver(row, col);
            return;
        } 
        if (onTop[row][col] == VISITED && !revealOnly) {
            onTop[row][col] = numNeighborMines(row, col);
        }
    }

    public void setGameOver(int row, int col) {
        if(onTop[row][col] != MINE) {
            onTop[row][col] = numNeighborMines(row, col);
        }else {
            // Set game is over
            onTop[row][col] = TOUCHED_MINE;
            gameOver = true;
            gameLost = true;
        }
    }

    @Override
    public void flagTile(int row, int col) {
        if(inBounds(row, col)) {
            if (onTop[row][col] == MINE) {
                onTop[row][col] = FLAGGED_MINE_PRESENT;
            }else if (onTop[row][col] == UNTOUCHED){
                onTop[row][col] = FLAGGED_MINE_NOT_PRESENT;
            }else if (onTop[row][col] == FLAGGED_MINE_PRESENT) {
                onTop[row][col] = MINE;
            } else if(onTop[row][col] == FLAGGED_MINE_NOT_PRESENT) {
                onTop[row][col] = UNTOUCHED;
            }
        }
    }

    @Override
    public void unFlagTile(int row, int col) {
        if (onTop[row][col] == FLAGGED_MINE_PRESENT) {
            onTop[row][col] = MINE;
        }else {
            onTop[row][col] = UNTOUCHED;
        }

    }

    @Override
    public boolean isGameOver(int row, int col) { 
        if(onTop[row][col] == REVEALED && isTileMine(row, col)) {
            gameOver = true;
            //return true;
        } else {
            gameOver = false;
        }
        return gameOver;
    }

    @Override
    public boolean isGameWon() {    
        System.out.println("You won.");
        return (gameOver && !gameLost);
    }

    @Override
    public boolean isGameLost() {
        System.out.println("You lost.");
        return gameLost;
    }

    @Override
    public void initializeBoard(int nr, int nc, int numMns) {
        numMines = numMns;
        numRows = nr;
        numCols = nc;
        onTop = new int [nr][nc];
        imageView = new ImageView[nr][nc];
        setMines(numMines);
        for(int i = 0; i<numRows; i++) {
            for(int j = 0; j<numCols; j++) {
                if(onTop[i][j] != MINE) {
                    onTop[i][j] = UNTOUCHED;
                }
            }
        }

    }

    @Override
    public boolean isFlagged(int row, int col) {
        if(inBounds(row, col) && (onTop[row][col] == FLAGGED_MINE_PRESENT || onTop[row][col] == FLAGGED_MINE_NOT_PRESENT)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTouched(int row, int col) {
        if (row < numRows && row >=0 && col >= 0 && col < numCols) {
            if (onTop[row][col] == UNTOUCHED || onTop[row][col] == MINE) {
                return false;
            }
        }

        return true;
    }
    @Override
    public int getValueAt(int row, int col) {
        if (inBounds(row, col)) {
            return onTop[row][col];
        }
        return UNTOUCHED;
    }

    @Override
    public int numNeighborMines(int row, int col) {
        int count = 0;
        if (row < 0  || col < 0 || row >= numRows || col >= numCols) {
            return 0;
        }
        //top
        if(col > 0) {
            if(onTop[row][col-1] == MINE) {
                count++;
            }
        }

        //top right
        if(row+1 < numRows && col > 0) {
            if(onTop[row+1][col-1] == MINE) {
                count++;
            }
        }

        //right
        if(row+1 < onTop.length) {
            if(onTop[row+1][col] == MINE) {
                count++;
            }
        }

        //bottom right
        if(row+1 < numRows && col+1 < numCols) {
            if(onTop[row+1][col+1] == MINE) {
                count++;
            }
        }

        //bottom
        if(col+1 < numCols) {
            if(onTop[row][col+1] == MINE) {
                count++;
            }
        }

        //bottom left
        if(row > 0 && col+1 < numCols) {
            if(onTop[row-1][col+1] == MINE) {
                count++;
            }
        }

        //left
        if(row > 0) {
            if(onTop[row-1][col] == MINE) {
                count++;
            }
        }

        //top left



if(row > 0 && col > 0) {
            if(onTop[row-1][col-1] == MINE) {
                count++;
            } 
        }
        return count;
    }

    @Override
    public boolean isTileMine(int row, int col) {
        if(onTop[row][col] == MINE) {
            return true;
        }
        return false;
    }

    @Override
    public int totalMines() {
        int c = 0;
        for(int i = 0; i<onTop.length; i++) {
            for(int j = 0; j<onTop[0].length; j++) {
                if(onTop[i][j] == MINE) {
                    c++;
                }
            }
        }
        return c;
    }

    @Override
    public void resetBoard() {
        initializeBoard(numRows, numCols, numMines);

    }


    @Override
    public boolean inBounds(int row, int col) {
        if(row >= 0 && row < onTop.length) {
            if(col >= 0 && col < onTop[0].length) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
    @Override
    public void setMines(int numMines) {
        int randRow;
        int randCol; 
        int count = 0;
        while(count<numMines) {
            randRow = (int) (Math.random() * numRows);
            randCol = (int) (Math.random() * numCols);
            if(inBounds(randRow, randCol)) {
                if (onTop[randRow][randCol] != MINE) {
                    onTop[randRow][randCol] = MINE;
                    count++;
                }
            }
        }

    }

}
