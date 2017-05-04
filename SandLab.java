import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields
  private int[][] grid = new int[120][80];
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //store this value in the corresponding position of the grid array
    grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
    //for loop to select pixels and put color on it
    //translate ints in grid array into display
    for(int row=0; row<grid.length; row++) {
      for(int col=0; col<grid[row].length; col++) {
      
         Color metalColor = new Color(122, 122, 122);
         Color emptyColor = new Color(0, 0, 0);
         Color sandColor = new Color(255, 255, 102);
         Color waterColor = new Color(51, 153, 255);
         
         if (grid[row][col] == 0) {
            display.setColor(row, col, emptyColor);
         } else if (grid[row][col] == 1) {
            display.setColor(row, col, metalColor);
         } else if (grid[row][col] == 2) {
            display.setColor(row, col, sandColor);
         } else if (grid[row][col] == 3) {
            display.setColor(row, col, waterColor);
         }
      }
    }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
      int k = (int)(Math.random()*grid.length-2)+1;
      int j = (int)(Math.random()*grid[k].length-2)+1;
      
      if ((grid[k][j] == 2)) {
         
         if (grid[k+1][j] == 0){
            grid[k+1][j] = 2;
            grid[k][j] = 0;
         } else if (grid[k+1][j] == 3) {
            grid[k+1][j] = 2;
            grid[k][j] = 3;
         }
         
      } else if ((grid[k][j] == 3) && (grid[k+1][j] == 0)) {
         grid[k+1][j] = 3;
         grid[k][j] = 0;
      } else if ((grid[k][j] == 3) && (grid[k][j+1] == 0)) {
         grid[k][j+1] = 3;
         grid[k][j] = 0;
      } else if (((grid[k][j] == 3)&&(j>1)) && ((grid[k][j-1] == 0) && (j > 1))) {
         grid[k][j-1] = 3;
         grid[k][j] = 0;
      } 


  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
