package edu.kit.informatik.result;

import edu.kit.informatik.data.Board;

public class VerticalFreeSpaces extends FreeSpaces {

    @Override
    public boolean check(Board board, int mcoord, int ncoord) {
        boolean checkup = false;
        boolean checkdown = false;
        
        //down
        for (int i = mcoord + 1; i < 10; i++) {
            if (board.getCell(i, ncoord).isOccupied()) {
                checkdown = true;
                break;
            }
        }
        //up
        for (int i = mcoord - 1; i >= 0; i--) {
            if (board.getCell(i, ncoord).isOccupied()) {
                checkup = true;
                break;
            }
        }
        
        if (mcoord == 0) {
            checkup = true;
        }
        if (ncoord == 10) {
            checkdown = true;
        }
        return checkup && checkdown;
    }

    @Override
    public int calculateFreeSpaces(Board board, int mcoord, int ncoord) {
       int freespaces = 0;
       int up = mcoord - 1;
       int down = mcoord + 1;
       
       //up
       while (board.isInBounds(up, ncoord) && !board.getCell(up, ncoord).isOccupied()) {
           freespaces++;
           up--;
       }
       System.out.println("this is the up" + freespaces);
       //down
       while (board.isInBounds(down, ncoord) && !board.getCell(down, ncoord).isOccupied()) {
           freespaces++;
           down++;
       }
       System.out.println("this is the total vertical" + freespaces);
       return freespaces;
    }

}
