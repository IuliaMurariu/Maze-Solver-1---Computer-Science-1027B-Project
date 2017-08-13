import java.io.IOException;
/**
 * The MazeSolver class uses an array stack to search through a maze of hexagon tiles to find the end tile.
 * @author IuliaMurariu
 * 
 */
public class MazeSolver {
	  /**
	   * This main method uses a maze.txt file to create a maze object, and then finds the end tile of the maze.
	   * @param args
	   */
      public static void main (String[] args) {
            try{
            	  // checks if a .txt maze file was entered 
                  if(args.length<1){
                	  throw new IllegalArgumentException("I don't think you provided a maze!");
                  }
                  
                  //stores the input file into the variable mazeFileName
                  String mazeFileName = args[0];
                  
                  //creating a new maze object using the input file
                  Maze MazeWindow = new Maze(mazeFileName);
                  
                  //referencing the starting hexagon from the maze
                  Hexagon StartingHexagon = MazeWindow.getStart();            
                  
                  //creates an array stack for the hexagon tiles
                  ArrayStack<Hexagon> HexagonStack = new ArrayStack<Hexagon>();
                  
                  //pushing starting hexagon on stack, then setting it as pushed to make it appear pink in maze
                  HexagonStack.push(StartingHexagon);
                  StartingHexagon.setPushed(); 
                  
                  //creating bookkeeping counters StepCounter and hexagonOnStack 
                  int StepCounter = 1;
                  int hexagonOnStack = 1;
                  
                  //creating a boolean called found that will later be set to true if the end tile is found and processed
                  boolean found = false;
                  
                  //as long as there is something on the stack, we will pop off a tile and examine all 6 of its neighbours 
                  while (hexagonOnStack != 0){
                
                	  //popping the starting hexagon tile off the stack, and adjusting StepCounter and hexagonOnStack as needed
                	  Hexagon PoppedHexagon = HexagonStack.pop();
                	  PoppedHexagon.setProcessed();
                	  StepCounter ++;
                	  hexagonOnStack --;
                	 
                	  //if popped hexagon tile is end tile, declare that we have found the end, update StepCounter and hexagonOnStack counters
                	  if (PoppedHexagon.isEnd()){
                		  //this indicates that we have found the end tile 
                		  found = true;
                		  System.out.println("We have found the end!");
                		  System.out.println("It took us " + StepCounter + " steps to find it.");
                		  System.out.println(hexagonOnStack + " is the number of tiles still left on the array stack.");
                		  break;
                	  }
                	  
                	  //if the popped hexagon tile is not the end tile, examine each of it's neighbours and place them on the stack as long as:
                	  		//the neighbour is not null, it's not a wall, it is unvisited and is not processed
                	  else{
                		  for(int i = 0; i <6; i++){
                			  Hexagon neighbour = PoppedHexagon.getNeighbour(i);
                			  if (neighbour != null && !neighbour.isWall() && neighbour.isUnvisited() && !neighbour.isProcessed()){
                				  HexagonStack.push(neighbour);
                				  neighbour.setPushed();
                				  hexagonOnStack ++;
                			  }
                		  }
                	  }
                	  //setting the current hexagon as processed, and repainting the maze window
                	  PoppedHexagon.setProcessed();
                	  MazeWindow.repaint();
                 }  
                //if the end tile is still not found after checking all tiles, then found remains false, and user is notified
                if (found == false){
                	System.out.println("Could not find the end of the maze!");
                }
            }
            //InvalidNeighbourIndexException - checks if program tries to access invalid neighbor.
            catch(InvalidNeighbourIndexException e){
            	System.out.println(e.getMessage() + "You tried to access a neighbour that doesn't exist!");
            }
            //EmptyCollectionException - makes sure program does not try to pop off tile if tile the array stack is empty.
            catch(EmptyCollectionException e){
            	System.out.println(e.getMessage() + "There are no hexagons on your stack!");
            
            }
            //UnknownMazeCharacterException - checks if unknown character was encountered during maze building.
            catch(UnknownMazeCharacterException e){
            	System.out.println(e.getMessage() + " Oops! A character was encountered during maze building that the maze cannot recognize!");
            }
            //IllegalArgumentException - checks if no maze.txt file was entered.
            catch(IllegalArgumentException e){
            	System.out.println(e.getMessage() + "Try adding it in again!");
            }
            //IOException - makes sure a valid maze file was entered.
            catch(IOException e){
            	System.out.println(e.getMessage() + " Oops! I don't think the maze you entered exists.");
            }
      }
}