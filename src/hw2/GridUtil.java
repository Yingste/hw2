
package hw2;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

import api.Cell;
import api.Mark;
import api.Status;

/**
 * Utility class for an implementation of Minesweeper.  This class
 * contains methods for examining and updating a 2D array of Cell
 * objects.
 */
public class GridUtil
{
	private static ArrayList<String> que = new ArrayList<String>();
	private static ArrayList<String> done = new ArrayList<String>();
	private static Boolean skip = false;
  /**
   * Marker character for initializing a 2D array of cells from
   * an array of Strings.
   */
  public static final char MINE_CHAR = 'x';

  /**
   * Creates a 2D array of Cells from an array of strings, where
   * each string corresponds to one row of the returned array.
   * The jth character of the ith string corresponds to row i, column j
   * of the Cell array.  A MINE_CHAR character means the corresponding
   * cell will be set as a mine. All strings in the given array
   * must have the same length. This method does not calculate the
   * counts for the cells.  Initially all cells have status HIDDEN, 
   * count 0, and mark value NONE.
   * @param descriptor
   *    array of strings from which to construct the Cell array
   * @return
   *    2D array of Cells
   */
  public static Cell[][] createFromStringArray(String[] descriptor)
  {
    int width = descriptor[0].length();
    int height = descriptor.length;
    Cell[][] grid = new Cell[height][width];
    for (int row = 0; row < height; row += 1)
    {
      for (int col = 0; col < width; col += 1)
      {
        grid[row][col] = new Cell(row, col);
        //System.out.println(row);
        if (descriptor[row].charAt(col) == MINE_CHAR)
        {
          grid[row][col].setIsMine(true);
        }
      }
    }
    return grid;
  }

  /**
   * Converts a grid into an array of strings.  Each row of the grid
   * corresponds to one string. There is one character for each cell.
   * If <code>revealAll</code> is false, then all HIDDEN cells are
   * are displayed as '-', 'f', or '?' depending on whether the mark value
   * is NONE, FLAG, or QUESTION_MARK. Revealed mines are displayed with MINE_CHAR,
   * and other revealed cells are displayed as the count.  
   * <p>
   * if <code>revealAll</code> is true, then all mines are displayed as
   * MINE_CHAR and all other non-mines are displayed as the count.
   *
   * @param grid
   *   2D array of Cells
   * @param revealAll
   *   true if hidden values should be shown, false otherwise
   * @return
   *   array of strings representing the grid
   */
  public static String[] convertToStringArray(Cell[][] grid, boolean revealAll)
  {
    String[] ret = new String[grid.length];
    for (int row = 0; row < grid.length; row += 1)
    {
      String current = "";
      for (int col = 0; col < grid[0].length; col += 1)
      {
        Cell c = grid[row][col];
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
            current += MINE_CHAR;
          }
          else
          {
            current += "" + c.getCount();
          }
        }
      }
      ret[row] = current;
    }
    return ret;
  }
  
  /**
   * Initialize the count value for each cell in the given 
   * 2D array.  The count for a non-mine is the number of neighboring 
   * cells (left, right, top, bottom, and diagonal) that are mines.
   * The count for a mine is -1.
   * @param grid
   *   2D array of Cells
   */
  public static void initCounts(Cell[][] grid)
  {
	  int i  = 0;
	  int j = 0;
	  
	  for(i = 0; i < grid.length; i ++)
	  {
		  for(j = 0; j < grid[i].length; j++)
		  {
			  if(!grid[i][j].isMine())
			  {
				  grid[i][j].setCount(0);//reset count incase of bad data
				  grid[i][j].setCount(countNeighboringMines(grid, i, j));
			  }
		  }
		  
		  
	  }
	  //System.out.println(grid[5][4].getCount());
  }

  /**
   * Counts the number of neighbors of the given position
   * (left, right, top, bottom, and diagonal) that are mines.
   * @param grid
   *   2D array of Cells
   * @param givenRow
   *   given position row
   * @param givenCol
   *   given position column
   * @return
   *   number of neighbors that are mines
   */
  public static int countNeighboringMines(Cell[][] grid, int givenRow, int givenCol)
  {
	  int count = 0;
	  if(givenRow >= 1)
	  { 
		  if(grid[givenRow - 1][givenCol].isMine())
		  {
			  count++;
			  //System.out.println(givenRow + " " + givenCol);
		  }
		  
		  if(givenCol < grid[givenRow].length - 1)
		  {
			  if(grid[givenRow - 1][givenCol + 1].isMine())
			  {
				  count++;
			  }
		  }
		  if(givenCol >= 1)
		  {
			  if(grid[givenRow - 1][givenCol - 1].isMine())
			  {
				  count++;
			  }
		  }
		  
	  }
	  if(givenRow < grid.length - 1)
	  {
		  //System.out.println(grid.length);
		  //System.out.println(givenCol);
		  ///**
		  if(grid[givenRow + 1][givenCol].isMine())
		  {
			  count++;
		  }
		  //*/
		  
		  if(givenCol < grid[givenRow].length - 1)
		  {
			  if(grid[givenRow + 1][givenCol + 1].isMine())
			  {
				  count++;
			  }
		  }
		  if(givenCol >= 1)
		  {
			  if(grid[givenRow + 1][givenCol - 1].isMine())
			  {
				  count++;
			  }
		  }
	  }
	  
	  
	  
	  if(givenCol >= 1)
	  {
		  if(grid[givenRow][givenCol - 1].isMine())
		  {
			  count++;
			  //System.out.println(givenRow + " " + givenCol);
		  }
		  
	  }
	  if(givenCol < grid[givenRow].length - 1)
	  {
		  //System.out.println(grid.length);
		  //System.out.println(givenCol);
		  ///**
		  if(grid[givenRow][givenCol + 1].isMine())
		  {
			  count++;
		  }
		  //*/
	  }
	  
	  
    return count;
  }

  
  /**
   * Determines whether all the non-mine cells have status
   * REVEALED.
   * @param grid
   *   2D array of Cells
   * @return
   *   true if all non-mine cells are revealed, false otherwise
   */
  public static boolean areAllCellsRevealed(Cell[][] grid)
  {
	  
	  int i  = 0;
	  int j = 0;
	  Boolean isRev = true;
	  for(i = 0; i < grid.length; i ++)
	  {
		  for(j = 0; j < grid[i].length; j++)
		  {
			  if(!grid[i][j].isMine())
			  {
				  if(grid[i][j].getStatus().toString() == "HIDDEN")
				  {
					  //System.out.println(false);
					  isRev = false;
				  }
			  }
		  }
		  
		  
	  }
    return isRev;
  }

  /**
   * Sets all mine cells to have status REVEALED.
   * Other cells are not modified.
   * @param grid
   *   2D array of Cells
   */
  public static void revealAllMines(Cell[][] grid)
  {
	  int i  = 0;
	  int j = 0;
	  
	  for(i = 0; i < grid.length; i ++)
	  {
		  for(j = 0; j < grid[i].length; j++)
		  {
			  if(grid[i][j].isMine())
			  {
				  if(grid[i][j].getStatus().toString() == "HIDDEN")
				  {
					  //System.out.println(false);
					  grid[i][j].setStatus(Status.REVEALED);
					  
				  }
			  }
		  }
		  
		  
	  }
  }

  /**
   * Returns the total number of mines in the given array.
   * @param grid
   *   2D array of Cells
   * @return
   *   number of mines in the array
   */
  public static int countAllMines(Cell[][] grid)
  {
	  int i  = 0;
	  int j = 0;
	  int count = 0;
	  for(i = 0; i < grid.length; i ++)
	  {
		  for(j = 0; j < grid[i].length; j++)
		  {
			  if(grid[i][j].isMine())
			  {
				 count++;
			  }
		  }
		  
		  
	  }
    return count;
  }

  /**
   * Returns the total number of cells marked as FLAG in the given array.
   * @param grid
   *   2D array of Cells
   * @return
   *   number of flagged cells
   */
  public static int countAllFlags(Cell[][] grid)
  {
	  int i  = 0;
	  int j = 0;
	  int count = 0;
	  for(i = 0; i < grid.length; i ++)
	  {
		  for(j = 0; j < grid[i].length; j++)
		  {
			  if(grid[i][j].getMark() == Mark.FLAG)
			  {
				  
				  count ++;
			  }
		  }
		  
		  
	  }
    return count;
  }

  /**
   * Returns one neighbor (left, right, top, bottom, diagonal) 
   * of the given position that is hidden and has count greater than zero
   * or null if there is no such cell.
   * @param grid
   *   2D array of Cells
   * @param givenRow
   *   given position row
   * @param givenCol
   *   given position column
   * @return
   *   a hidden, non-mine cell that neighbors the given position
   */
  public static Cell findOneHiddenNeighbor(Cell[][] grid, int givenRow, int givenCol)
  {
	  
	  if(givenRow >= 1)
	  {
		  if(grid[givenRow - 1][givenCol].getStatus() == Status.HIDDEN)
		  {
			  return grid[givenRow - 1][givenCol];
			  
		  }
		  
		  if(givenCol < grid[givenRow].length - 1)
		  {
			  if(grid[givenRow - 1][givenCol + 1].getStatus() == Status.HIDDEN)
			  {
				  return grid[givenRow - 1][givenCol + 1];
			  }
		  }else if(givenCol >= 1)
		  {
			  if(grid[givenRow - 1][givenCol - 1].getStatus() == Status.HIDDEN)
			  {
				  return grid[givenRow - 1][givenCol - 1];
			  }
		  }
		  
	  }
	  if(givenRow < grid.length - 1)
	  {
		  //System.out.println(grid.length);
		  //System.out.println(givenCol);
		  ///**
		  if(grid[givenRow + 1][givenCol].getStatus() == Status.HIDDEN)
		  {
			 return grid[givenRow + 1][givenCol];
		  }
		  //*/
		  
		  if(givenCol < grid[givenRow].length - 1)
		  {
			  if(grid[givenRow + 1][givenCol + 1].getStatus() == Status.HIDDEN)
			  {
				  return grid[givenRow + 1][givenCol + 1];
			  }
		  }else if(givenCol >= 1)
		  {
			  if(grid[givenRow + 1][givenCol - 1].getStatus() == Status.HIDDEN)
			  {
				  return grid[givenRow + 1][givenCol - 1];
			  }
		  }
	  }
	  
	  
	  
	  if(givenCol >= 1)
	  {
		  if(grid[givenRow][givenCol - 1].getStatus() == Status.HIDDEN)
		  {
			  
			  return grid[givenRow][givenCol - 1];
		  }
		  
	  }
	  if(givenCol < grid[givenRow].length - 1)
	  {
		  //System.out.println(grid.length);
		  //System.out.println(givenCol);
		  ///**
		  if(grid[givenRow][givenCol + 1].getStatus() == Status.HIDDEN)
		  {
			  return grid[givenRow][givenCol + 1];
		  }
		  //*/
	  }
	  
	  
	  
	  
    return null;
  }

  /**
   * Sets all mine cells to have mark value <code>Mark.FLAG</code>.
   * @param grid
   *   2D array of Cells
   */
  public static void flagAllMines(Cell[][] grid)
  {
	  int i  = 0;
	  int j = 0;
	  
	  for(i = 0; i < grid.length; i ++)
	  {
		  for(j = 0; j < grid[i].length; j++)
		  {
			  if(grid[i][j].isMine())
			  {
				  grid[i][j].setMark(Mark.FLAG);
				  
			  }
		  }
		  
		  
	  }
  }    
  
  /**
   * Reveals all neighbors of the Cell at the given position that
   * are not mines and have count greater than zero.  If the given <code>ArrayList</code>
   * is non-null, all revealed cells are added to the list, in the order in which
   * they are revealed.
   * @param grid
   *   2D array of Cells
   * @param givenRow
   *   given position row
   * @param givenCol
   *   given position column
   * @param history
   *   list to which revealed cells are added
   */
  public static void revealNeighbors(Cell[][] grid, int givenRow, int givenCol, ArrayList<Cell> history)
  {
    // find the boundary around the given cell; this will normally be a 3x3 
    // region, but we may be against one or both of the borders
    int upper = Math.max(0, givenRow - 1);
    int lower = Math.min(grid.length - 1, givenRow + 1);
    int left = Math.max(0, givenCol - 1);
    int right = Math.min(grid[0].length - 1, givenCol + 1);
    
    // now we can iterate over all cells except for the center one
    for (int row = upper; row <= lower; row += 1)
    {
      for (int col = left; col <= right; col += 1)
      {
        if (!(row == givenRow && col == givenCol))
        {
          Cell d = grid[row][col];
          if (d.getCount() > 0)
          {
            d.setStatus(Status.REVEALED);
            if (history != null)
            {
              history.add(d);
            }
          }
        }
      }
    }
  }

  /**
   * Performs a "flood fill" algorithm to reveal a region of 
   * all reachable cells with count zero, 
   * plus the nonzero cells at the boundary of the region, starting at
   * the given position. The region does not include cells with count zero
   * that are only connected diagonally.  If the cell at the given position does not have 
   * count 0, this method does nothing.  If the given <code>ArrayList</code>
   * is non-null, all revealed cells are added to the list, in the order in which
   * they are revealed.
   * <p>
   * See the document:<br>
   * <a href="http://web.cs.iastate.edu/~cs227/homework/hw2/clearRegion.pdf">
   * http://web.cs.iastate.edu/~cs227/homework/hw2/clearRegion.pdf</a><br>
   * for details.
   * 
   * @param grid
   *   2D array of cells
   * @param row
   *   initial cell row
   * @param col
   *   initial cell column
   * @param history
   *   list to which revealed cells are added
   *   
   */
  public static void clearRegion(Cell[][] grid, int row, int col, ArrayList<Cell> history)
  {
	  
	  
	  if(skip)
	  {
		  if(que.size() > 0)
		  {
			  
			  Scanner scan = new Scanner(que.get(0));
			  row = scan.nextInt();//get the row from que
			  col = scan.nextInt();//get the col from que
			  done.add(que.get(0));//copy from que to done 
			  history.add(grid[row][col]);
			  que.remove(0);//remove que
			  
			  if(row >= 1)// check up
			  {
				  grid[row][col].setStatus(Status.EXPLORE_UP);
				  if(!grid[row - 1][col].isMine())
				  {
					  if(grid[row - 1][col].getCount() == 0)//check if we can add the cell to the queue
					  {
						  Boolean canAdd = true;
						  if(done.contains((row - 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row - 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row - 1) + " " + col);
						  }
					  }
					  grid[row - 1][col].setStatus(Status.REVEALED);
					  
				  }
			  }
			  if(row < grid.length - 1)//check down
			  {
				  grid[row][col].setStatus(Status.EXPLORE_DOWN);
				  if(!grid[row + 1][col].isMine())
				  {
					  if(grid[row + 1][col].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row + 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row + 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row + 1) + " " + col);
						  }
					  }
					  grid[row + 1][col].setStatus(Status.REVEALED);
				  }
			  }
			  
			  
			  
			  if(col >= 1)//check left
			  {
				  grid[row][col].setStatus(Status.EXPLORE_LEFT);
				  if(!grid[row][col - 1].isMine())
				  {
					  
					  if(grid[row][col - 1].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row) + " " + (col - 1)))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row) + " " + (col - 1)))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row) + " " + (col - 1));
						  }
					  }
					  grid[row][col - 1].setStatus(Status.REVEALED);
				  }
				  
			  }
			  if(col < grid[row].length - 1)
			  {
				  grid[row][col].setStatus(Status.EXPLORE_RIGHT);
				  if(!grid[row][col + 1].isMine())
				  {
					  if(grid[row][col + 1].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row) + " " + (col + 1)))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row) + " " + (col + 1)))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row) + " " + (col + 1));
						  }
					  }
					  grid[row][col + 1].setStatus(Status.REVEALED);
				  }
			  }
			  
			  
			//check up right 
			  //check up left 
			  if (row >= 1)
			  {
				  if (col < grid[row].length - 1)
				  {
					  grid[row - 1][col + 1].setStatus(Status.REVEALED);
				  
				  }
				  
				  if (col >= 1)
				  {
				  
					  grid[row - 1][col - 1].setStatus(Status.REVEALED);
					  
				  }
				  
			  }
			  
			  
			  //check down right 
			  //check down left
			  if (row < grid.length - 1)
			  {
				  if (col < grid[row].length - 1)
				  {
					  
						  
						  grid[row + 1][col + 1].setStatus(Status.REVEALED);
					  
				  }
				  
				  if (col >= 1)
				  {
					  
						  grid[row + 1][col - 1].setStatus(Status.REVEALED);
						  
					  
				  }
				  
			  }
			  
			  
			  //clearq(grid, history);//runs the tasks on the que
			  clearRegion(grid, row, col, history);
			  int i;
			  for(i = 0; i < done.size() - 1; i++)
			  {
				  
			  }
		  }
		  
	  }else
	  {
		  done.add(row + " " + col);//store the given values as done.
		  skip = true;
		  Cell c = grid[row][col];
		    if (c.getCount() == 0)
		    {
		      c.setStatus(Status.REVEALED);
		      if (history != null)
		      {
		        history.add(c);
		      }
		    }
		    
		    if(row >= 1)// check up
			  {
				  if(!grid[row - 1][col].isMine())
				  {
					  if(grid[row - 1][col].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row - 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row - 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row - 1) + " " + col);
						  }
					  }
					  
					  grid[row - 1][col].setStatus(Status.REVEALED);
					  
				  }
			  }
			  if(row < grid.length - 1)//check down
			  {
				  if(!grid[row + 1][col].isMine())
				  {
					  if(grid[row + 1][col].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row + 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row + 1) + " " + col))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row + 1) + " " + col);
						  }
					  }
					  grid[row + 1][col].setStatus(Status.REVEALED);
				  }
			  }
			  
			  
			  
			  if(col >= 1)//check left
			  {
				  if(!grid[row][col - 1].isMine())
				  {
					  
					  if(grid[row][col - 1].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row) + " " + (col - 1)))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row) + " " + (col - 1)))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row) + " " + (col - 1));
						  }
					  }
					  grid[row][col - 1].setStatus(Status.REVEALED);
				  }
				  
			  }
			  if(col < grid[row].length - 1)//check right
			  {
				  if(!grid[row][col + 1].isMine())
				  {
					  if(grid[row][col + 1].getCount() == 0)
					  {
						  Boolean canAdd = true;
						  if(done.contains((row) + " " + (col + 1)))
						  {
							  canAdd = false;
						  }
						  if(que.contains((row) + " " + (col + 1)))
						  {
							  canAdd = false;
						  }
						  if(canAdd)
						  {
							  que.add((row) + " " + (col + 1));
						  }
					  }
					  grid[row][col + 1].setStatus(Status.REVEALED);
				  }
			  }
			  
			  //check up right 
			  //check up left 
			  if (row >= 1)
			  {
				  if (col < grid[row].length - 1)
				  {
					  grid[row - 1][col + 1].setStatus(Status.REVEALED);
				  
				  }
				  
				  if (col >= 1)
				  {
				  
					  grid[row - 1][col - 1].setStatus(Status.REVEALED);
					  
				  }
				  
			  }
			  
			  
			  //check down right 
			  //check down left
			  if (row < grid.length - 1)
			  {
				  if (col < grid[row].length - 1)
				  {
					  
						  
						  grid[row + 1][col + 1].setStatus(Status.REVEALED);
					  
				  }
				  
				  if (col >= 1)
				  {
					  
						  grid[row + 1][col - 1].setStatus(Status.REVEALED);
						  
					  
				  }
				  
			  }
			  
			  
			  
			  
			  clearRegion(grid, row, col, history);//runs the tasks on the que
	  }
    
    
  }

  
  
  

}
