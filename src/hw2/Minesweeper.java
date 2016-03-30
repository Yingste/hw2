package hw2;

import java.util.ArrayList;
import java.util.Random;

import api.Cell;
import api.CellObserver;
import api.Mark;
import api.Status;

/**
 * This class encapsulates the state of a minesweeper game.
 */
public class Minesweeper
{
	private String[]initMine;
	private Cell[][] grid2;
	private Boolean gameState = true;
	private int cCount = 0;
  
  /**
   * Constructs an instance of the game using the given array
   * of strings to initialize the mine locations. 
   * The jth character of the ith string corresponds to row i, column j
   * of the Cell array.  A <code>GameUtil.MINE_CHAR</code> character 
   * means the corresponding cell will be set as a mine. All 
   * strings in the given array must have the same length.  Initially
   * all cells have status HIDDEN and the counts are correct. 
   * @param descriptor
   *   array of strings representing mine positions
   */
  public Minesweeper(String[] descriptor)
  {
    // TODO
	  initMine = descriptor;
	  int i = 0;
	  grid2 = GridUtil.createFromStringArray(descriptor);
	  GridUtil.initCounts(grid2);
	    String[] actual = GridUtil.convertToStringArray(grid2, true);
  }
  
  /**
   * Constructs an instance of the game of the specified size and
   * specified initial number of mines, using the given <code>Random</code> object
   * to select the mine locations.  The selection is performed in such a way that
   * each cell is equally likely to be selected as one of the mines.
   * Initially all cells have status HIDDEN and the counts are correct.
   * @param rows
   *   number of rows in the grid
   * @param columns
   *   number of columns in the grid
   * @param numberOfMines
   *   number of mines in the grid
   * @param givenRandom
   *   random number generator to use for placing mines and <code>randomMove</code>
   */
  public Minesweeper(int rows, int columns, int numberOfMines, Random givenRandom)
  {
	  System.out.println(rows);
	  System.out.println(columns);
	  System.out.println(numberOfMines);
	  System.out.println(givenRandom);
	  //TODO
  }
  
  /**
   * Returns the number of clicks for revealing a cell that have been made.
   * Note that this number may be smaller than the number of revealed
   * cells.  An operation to toggle the mark of a cell does not count
   * as a click.
   * @return
   *   number of clicks
   */
  public int getClicks()
  {
    
    return cCount;
  }
  
  /**
   * Returns the total number of mines in the grid.
   * @return
   *   number of mines
   *   
   */
  public int getNumMines()
  {
    
    return GridUtil.countAllMines(grid2);
  }
  
  /**
   * Returns the total number of cells with mark value FLAG.
   * @return
   *   number of flagged cells
   */
  public int getNumFlags()
  {
    
    return GridUtil.countAllFlags(grid2);
  }
  
  /**
   * Returns the number of rows in the grid.
   * @return
   *   number of rows in the grid
   */
  public int getRows()
  {
    
    return grid2.length;
  }
  
  /**
   * Returns the number of columns in the grid.
   * @return 
   *   number of columns in the grid
   */
  public int getColumns()
  {
    
    return grid2[0].length;
  }
  
  /**
   * Returns the cell at the specified position.
   * <p>
   * NOTE: The caller of this method should normally not modify
   * the returned cell.
   * 
   * @param row
   *   given position row
   * @param col
   *   given position column
   * @return
   *   cell at the given position
   */
  public Cell getCell(int row, int col)
  {
   
    return grid2[row][col];
  }
  
  /**
   * Returns this game's grid as an array of strings, according
   * to the conventions described in 
   * <code>GridUtil.convertToStringArray</code>.
   * @param revealAll
   *   true if hidden cell values should be shown
   * @return
   *   array of strings representing this game's grid
   */
  
  public String[] getGridAsStringArray(boolean revealAll)
  {
	  String[] ret = new String[grid2.length];
	    for (int row = 0; row < grid2.length; row += 1)
	    {
	      String current = "";
	      for (int col = 0; col < grid2[0].length; col += 1)
	      {
	        Cell c = grid2[row][col];
	        if (c.getStatus() == Status.HIDDEN && !revealAll)
	        {
	          if (c.getMark() == Mark.FLAG)
	          {
	            current += "f";
	          }
	          else if (c.getMark() == Mark.QUESTION_MARK)
	          {
	            current += "?";          
	          }
	          else
	          {
	            current += "-";
	          }
	        }
	        else
	        {
	          if (c.isMine())
	          {
	            current += 'x';
	          }
	          else
	          {
	            current += "" + c.getCount();
	          }
	        }
	      }
	      ret[row] = current;
	    }
    return null;
  }
  
  /**
   * Returns a reference to the list of all revealed cells, in 
   * the order they were revealed.
   * <p>
   * NOTE: The caller of this method should normally not modify
   * the returned list or the cells it contains.
   * @return
   *   list of all revealed cells
   */
  public ArrayList<Cell> getHistory()
  {
    // TODO
    return null;
  }
  
  
  /**
   * Returns true if the game is over, false otherwise.
   * The game is over if the player attempts to reveal a mine,
   * or if all non-mine cells are revealed. 
   * @return
   *   true if the game is over, false otherwise
   */
  public boolean isOver()
  {
    
    return !gameState;
  }
  
  /**
   * Toggle the mark value on the cell at the given 
   * position.  The values should cycle through
   * <code>Mark.NONE</code>, <code>Mark.FLAG</code>, and
   * <code>Mark.QUESTION_MARK</code>, in that order.
   * @param row
   *   given position row
   * @param col
   *   given position column
   */
  public void toggleMark(int row, int col)
  {
	  if(grid2[row][col].getMark() == Mark.NONE)
	  {
		  grid2[row][col].setMark(Mark.FLAG);
	  }else if(grid2[row][col].getMark() == Mark.FLAG)
	  {
		  grid2[row][col].setMark(Mark.QUESTION_MARK);
	  }else
	  {
		  grid2[row][col].setMark(Mark.NONE);
	  }
	  
  }
  
  /**
   * Processes a selection by the player to reveal the cell at the 
   * given position.  In general revealing a mine
   * should end the game, and revealing a cell with
   * count zero should initiate a call to 
   * <code>GridUtil.clearRegion</code>.  However, a special
   * case is made for the first selection: if the player
   * selects a mine as the first move, the cell is first converted
   * to a non-mine and the count is adjusted, and then the 
   * selection is processed normally.  This method does
   * nothing if the game is over.
   * @param row
   *   given position row
   * @param col
   *   given position column
   */
  public void play(int row, int col)
  {
	  if(!isOver())//if game is playable
	  {
		  cCount ++;
		  grid2[row][col].setStatus(Status.REVEALED);
		  if (grid2[row][col].isMine())
		  {
			  endGame();
		  }else if(grid2[row][col].getCount() == 0)
		  {
			  GridUtil.clearRegion(grid2, row, col, getHistory());
		  }
		  System.out.println(!gameState);
		  if(isWon())
		  {
			  endGame();
		  }
	  }
	  
  }
  
  /**
   * Returns whether or not the game has been won.
   * @return
   *   true if the game is won, false otherwise
   */
  public boolean isWon()
  {
    
    return GridUtil.areAllCellsRevealed(grid2);
  }
  
  /**
   * Iterates over the history of revealed cells, in reverse order, to find a
   * neighboring cell that is still hidden and has count greater than zero, 
   * and then reveals the first such cell found.  If the history is empty or no such cell
   * exists, this method does nothing. (The latter can only occur if all remaining hidden
   * non-mine cells have count zero.)
   */
  public void hint()
  {
    // TODO
  }
  
  
  /**
   * Calls setObserver with the given <code>CellObserver</code> on every cell 
   * of the grid.
   * @param observer
   *   reference to a <code>CellObserver</code>
   */
  public void setObserver(CellObserver observer)
  {
	  int i  = 0;
	  int j = 0;
	  
	  for(i = 0; i < grid2.length; i ++)
	  {
		  for(j = 0; j < grid2[i].length; j++)
		  {
			 grid2[i][j].setObserver(observer);
		  }
		  
		  
	  }
  }
  
  private void endGame()
  {
	  gameState = false;
  }
  
  
}
