CSC171 Project 3: Conway's Game of Life
Team Name: TeamEgypt
Team members:
	Nadine Eldallal
	Ebram Youssef

*********************************************************************************
//OVERVIEW:

This program is a simple simulation of Conway's Game of Life. The program is composed of a main public class (GameOfLife), a constructor and 2 inner classes (TimerCallBack and myCanvas) each with their methods, as well as five methods in the outer class and the main (driver) method. The main method begins by creating a new object of the class GameOfLife and initializing it to call the constructor method. The constructor method creates a JFrame, initializes it by setting its layout, exit operation and adding the canvas to it and making it visible. It also creates the main JPanel controlPanel, sets its colors, layout, dimensions and adds it to the frame. Three buttons are also added to the controlPanel through the constructor. The simulation begins automatically as soon as it is ran, the stop button stops the timer therefore stoping the simulation, the start button fires it up again from where it last left off, and the reset button resets the grid to remove all color, randomizes the grid from the beginning with 0s and 1s and automatically restarts the timer. This is all performed through ActionListener and subsequently the actionPerformed method.

*********************************************************************************

//FLOURISHES:

We chose to complete the following 3 flourishes:
	a) Include a slider to set the simulation speed (include labels to indicate the limits).
	c) Add a way to control the size of the simulation grid – as in the number of cells, rather than visually. This could be a text field or a slider.
	g) Make it look cool. Explore different colors for the dead cells, live cells, and the grid.

* Flourish (a):
	As a flourish, the constructor also adds a JSlider to the controlPanel to help set the speed (timer delay), with a minimum speed of 1 millisecond and a maximum speed of 2000 milliseconds. The default value for the timer is 1000 milliseconds (meaning a new generation is created every second). NOTE: the slider doesn't change the timer delay in real-time, meaning you have to either pause the simulation, change the slider value, then resume the simulation in order for the delay to change, or reset the simulation after changing the delay.


* Flourish (c):

	The control panel also includes a JTextField, as a means of input for the grid size (dimensions). The number entered (as a string) is then converted to an integer through the Integer.parseInt() method and passed to the instance variable dim. The default size of the grid is 20. You have to hit the reset button after entering a new size in order for the new grid to be drawn. You can enter any value in the text field, but 3 to 50 are recommended limits.


* Flourish (g):

	Following the RNG theme in any simulation, we chose to "make the simulation cool" by using a different, randomly generated color for each generation of alive cells. It particularly looks cool when the simulation reaches one of the infinitely repeating patterns (like oscillators) and the colors make the simulation look like it's "dancing." NOTE: since resizing the window recalls paintComponent each time, and the process of random color generation has to be carried out inside paintComponent, resizing the window keeps generating new random colors. We couldn't, however, find a way for this not to happen.

*********************************************************************************

//OTHER NOTES:

* The evolve() method is responsible for generating the next generation from the previous one by adding up the neighbors of each cell in the grid using 2 directional arrays, checking the validity of each cell and applying the rules of life through an if conditional statement. The method also includes a counter for each generation made that is displayed as a label (steps). The method calls repaint() in order to loop the display of each following generation on the grid in order to create the simulation of the game.

* In the class myCanvas, the paintComponent uses both Graphics and a Graphics2D objects to create the desired grid, through a List of type Rectangle, and nested for loops to fill the List. Then, using the Graphics2D object, another nested for loop sets the color of the alive cells to randomly generated colors (see flourish g) and dead cells to default color, and grid lines to gray in order to draw the grid.


*********************************************************************************

//COLLABORATION STATEMENT:

*Team Name: TeamEgypt
*Team members:
	Nadine Eldallal
	Ebram Youssef

*Contributions of Each Member:
	
*Nadine Eldallal:
-simulation of 20x20 grid by default (design of GUI)
-program behaving reasonably when resized
-creating the JFrame and JComponents (public GameOfLife())
-writing the class myCanvas (creation of the grid from cells)
-writing methods randomize() and resetGrid()
-flourish a) slider to set simulation speed

Nadine Eldallal and Ebram Youssef:
-writing main method
-writing the calculations for the next generation and applying the rules of the game
-button to reset/randomize the board
-buttons to start and stop the simulation
-Timer for the simulation

Ebram Youssef:
-Displaying number of steps since the simulation was most recently reset
-Writing the method evolve(), valid(), start()
-Debugged the timer, delay JSlider, and JButtons (actionListener) functionality
-Flourish c) grid size
-Flourish g) RNG colors
