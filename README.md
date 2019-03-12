# DAWN-11-15
This was the final project for my first semester Java course at KIT. This is a slightly updated version of the original [DAWN 11/15](https://www.althofer.de/dawn-11-15.html) game by Professor Ingo Althöfer.

Some notes:
* Terminal.printLine(message) is just System.out.println(message)
* Terminal.readLine takes in input from the user as a String
* Terminal.printError(message) is just System.out.println("Error, " + message)

They are methods from the Terminal class that KIT provides to us. It was not uploaded here due to copyright.

## Commands:
* state m;n : Prints the symbol representing the piece on the cell of coordinates (m,n). '-' for no piece, '+' for a Mission Control piece, 'V' for Vesta and 'C' for Ceres.

* print : Prints the board as a grid represented by the aforementioned symbols.

* set-vc m;n : Places a Nature piece on the cell of coordinates (m,n).
  
* roll symbol : Rolls the die.
  
* place x;y:m:n : Places a Mission Control piece either horizontally or vertically between cells (x,y) and (m,n).
  
* move m;n ... : Moves the current Nature piece some amount of moves. Each move is one elementary step (horizontal or vertical).

* show-result : Prints the result of the game based on a formula (see: DAWN 11/15 rules).

* reset : Starts a new game.

* quit : Exits the program.
