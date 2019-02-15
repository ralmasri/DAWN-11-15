package edu.kit.informatik.result;

import edu.kit.informatik.data.Board;

public class DiagonalFreeSpaces extends FreeSpaces {

    @Override
    public boolean check(Board board, int mcoord, int ncoord) {
        boolean checklower = false;
        boolean checkupper = false;
        //lower
        for (int i = mcoord + 1, j = ncoord + 1; board.isInBounds(i, j); i++, j++) {
            if (board.getCell(i, j).isOccupied()) {
                checklower = true;
                break;
            }
        }
        //upper
        for (int i = mcoord - 1, j = ncoord - 1; board.isInBounds(i, j); i--, j--) {
            if (board.getCell(i, j).isOccupied()) {
                checkupper = true;
                break;
            }
        }
       if (mcoord == 10 || ncoord == 14) {
           checklower = true;
       }
       if (mcoord == 0 || ncoord == 0) {
           checkupper = true;
       }
       
       return checklower && checkupper;
    }

    @Override
    public int calculateFreeSpaces(Board board, int mcoord, int ncoord) {
        int freespaces = 0;
        int upperm = mcoord - 1;
        int uppern = ncoord - 1;
        int lowerm = mcoord + 1;
        int lowern = ncoord + 1;
        
        //upper
        while(board.isInBounds(upperm, uppern) && !board.getCell(upperm, uppern).isOccupied()) {
            freespaces++;
            upperm--;
            uppern--;
        }
        System.out.println("this is the upper diagonal " + freespaces);
        //lower
        while (board.isInBounds(lowerm, lowern) && !board.getCell(lowerm, lowern).isOccupied()) {
            freespaces++;
            lowerm++;
            lowern++;
        }
        System.out.println("This is the diagonal " + freespaces);
        return freespaces;
    }

}
