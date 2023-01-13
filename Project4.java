import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.awt.Color;

/**
 * Rectangle of Rectangles: Project 4. 
 *
 * Gateway Programming: Java
 * Johns Hopkins University
 * Fall 2022
 * @Author: Yoohyuk Chang
 * @JHED: ychang82
 * @Date: November 3th 2022
 * The purpose of the program is to create and display 2D arrays of 
 * rectangles on a drawing window using the StdDraw library.
 * Using this program, the users can type in their desired size of the 
 * drawing and type of files to display on the drawing windows.
 * The users can conduct checkerboard, snake, and spiral operations, 
 * which draws on the drawing window with each of their 
 * programmed animations.
 * 
 */
public class Project4 {

   /**
   * Read files containing data of what to draw.
   * Then with the data collected from file, the method creates a new
   * Rectangle[][] type grid array.
   * @param filename Filen that the program will read
   * @return A Rectangle[][] type grid 2D array stored with data from filename
   * @throws IOException
   */
   public static Rectangle[][] gridMaker(String filename) throws IOException {
      FileInputStream fileIn = new FileInputStream(filename);
      Scanner fileRead = new Scanner(fileIn);
      
      int rows = fileRead.nextInt(); 
      int cols = fileRead.nextInt(); 
      
      String dummyLine = fileRead.nextLine(); // dummy line
      
      Rectangle[][] newGrid = new Rectangle[rows][cols];
      
      for (int i = 0; i < rows; i++) { 
         for (int j = 0; j < cols; j++) { 
            newGrid[i][j] = new Rectangle(fileRead.nextLine()); 
         }
      }
      fileRead.close();
      fileIn.close();
            
      return newGrid;
   }
   
   /**
   * Checkerboard operation runs by this method.
   * Displays on drawing window with the row, column, and color values 
   * a user enters from the main method.
   * Creates checkerboard pattern drawing from left to right, top to bottom.
   * It also creates a file stored with the displayed data values.
   * @param rows Number of rows for the checkerboard
   * @param cols Number of cols for the checkerboard
   * @param red The RGB value for red
   * @param green The RGB value for green
   * @param blue The RGB value for blue
   * @throws IOException
   */
   public static void checkerboardOperation(int rows, int cols, 
                                         int red, int green, 
                                         int blue) throws IOException {
      double width = 1.0 / cols;  
      double height = 1.0 / rows; 
      double xCenter = width / 2.0;   
      double yCenter = height / 2.0;  
      boolean filled;
      
      PrintWriter outfile = new PrintWriter("checkerboard" + rows + ".txt");
      
      Rectangle[][] drawing = new Rectangle[rows][cols];
      outfile.println(rows + " " + cols);  
      
      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            filled = true;
            if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1) {
               drawing[i][j] = new Rectangle(new Color(255, 255, 255),
               width, height, filled,
               (j * width) + xCenter, 1 - ((i * height) + yCenter));
            }
            else {
               drawing[i][j] = new Rectangle(new Color(red, green, blue), 
               width, height, filled,
               (j * width) + xCenter, 1 - ((i * height) + yCenter));
            }
            drawing[i][j].draw(); 
            StdDraw.pause(200);
            outfile.println(drawing[i][j]);  // calls toString
         }
      }
      outfile.flush();
      outfile.close();
   }
   
   /**
   * Snake operation runs by this method.
   * Draws with the snakeGrid, the Rectangle[][] type grid 
   * read from file in the main method, on the drawing window.
   * It repeats drawing from the top to bottom and then bottom to the top   
   * until it reaches the end of the column from left to right.
   * @param snakeGrid Rectangle[][] type grid that will be used 
                      for snake operation.
   * @param rows The number of rows of snakeGrid.    
   * @param cols The number of columns of snakeGrid.
   */
   public static void snakeOperation(Rectangle[][] snakeGrid, 
                                     int rows, int cols) {
      for (int j = 0; j < cols; j++) {
         if (j % 2 == 1) {
            for (int i = rows - 1; i >= 0; i--) {
               snakeGrid[i][j].draw();
               StdDraw.pause(200);
            }
         }
         else {
            for (int i = 0; i < rows; i++) {
               snakeGrid[i][j].draw();
               StdDraw.pause(200);
            }
         }
      }
   }
   
   /**
   * Spiral operation runs by this method.
   * Draws with the spiralGrid, the Rectangle[][] type grid 
   * read from file in the main method, on the drawing window.
   * The method draws on the drawing window with a spiral shape.
   * @param spiralGrid Rectangle[][] type grid that will be used 
                      for spiral operation.
   * @param rows The number of rows of spiralGrid.    
   * @param cols The number of columns of spiralGrid.
   */
   public static void spiralOperation(Rectangle[][] spiralGrid, 
                                      int rows, int cols) {
      int top = 0;
      int bottom = rows - 1;
      int left = 0;
      int right = cols - 1;
      char direction = 'L';
      int totalNumberOfSquares = rows * cols;
      int countSquares = 0;
      boolean isLastSquare = true;
      int i;
      int j;
      
      while (isLastSquare) {
         if (direction == 'L') {
            i = bottom;
            for (j = right; j >= left; j--) {
               spiralGrid[i][j].draw();
               StdDraw.pause(200);
               countSquares = countSquares + 1;
               if (countSquares == totalNumberOfSquares) {
                  isLastSquare = false;
               }
            }
            bottom = bottom - 1;
            direction = 'U';
         }
         else if (direction == 'U') {
            j = left;
            for (i = bottom; i >= top; i--) {
               spiralGrid[i][j].draw(); 
               StdDraw.pause(200); 
               countSquares = countSquares + 1;
               if (countSquares == totalNumberOfSquares) {
                  isLastSquare = false;
               }
            }
            left = left + 1;
            direction = 'R';
         }
         else if (direction == 'R') {
            i = top;
            for (j = left; j <= right; j++) {
               spiralGrid[i][j].draw(); 
               StdDraw.pause(200);
               countSquares = countSquares + 1;
               if (countSquares == totalNumberOfSquares) {
                  isLastSquare = false;
               }
            }
            top = top + 1;
            direction = 'D';
         }
         else if (direction == 'D') {
            j = right;
            for (i = top; i <= bottom; i++) {
               spiralGrid[i][j].draw(); 
               StdDraw.pause(200);
               countSquares = countSquares + 1;
               if (countSquares == totalNumberOfSquares) {
                  isLastSquare = false;
               }
            }
            right = right - 1;
            direction = 'L';
         }
      }
   }
   
   /**
    * The main method for Rectangle of Rectangle program.
    * Drives the Rectangle of Rectangle program.
    * @param args This program does not take commandline arguments.
    * @throws IOException
    */
   public static void main(String[] args) throws IOException {
   
      Scanner scnr = new Scanner(System.in);
      
      // CHECKERBOARD
      System.out.print("Enter checkerboard size: ");
      int checkerboardSize = scnr.nextInt();
      int rows = checkerboardSize;
      int cols = rows;
      
      System.out.print("Enter RGB values, each [0,255]: ");
      int red = scnr.nextInt();
      int green = scnr.nextInt();
      int blue = scnr.nextInt();
      
      StdDraw.clear(Color.LIGHT_GRAY);
      
      checkerboardOperation(rows, cols, red, green, blue);
      
            
      // SNAKE 
      System.out.print("Enter snake input filename: ");
      String fileNameforSnake = scnr.next();
      
      StdDraw.clear(Color.LIGHT_GRAY);
      Rectangle[][] snakeGrid = gridMaker(fileNameforSnake);
      rows = snakeGrid.length;
      cols = snakeGrid[0].length;
      
      snakeOperation(snakeGrid, rows, cols);
      

      // SPIRAL
      System.out.print("Enter spiral input filename: ");
      String fileNameforSpiral = scnr.next();
      
      StdDraw.clear(Color.LIGHT_GRAY);
      
      Rectangle[][] spiralGrid = gridMaker(fileNameforSpiral);
      
      rows = spiralGrid.length; 
      cols = spiralGrid[0].length;
      
      spiralOperation(spiralGrid, rows, cols);
   }
}