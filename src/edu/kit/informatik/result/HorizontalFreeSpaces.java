package edu.kit.informatik.result;

import edu.kit.informatik.data.Board;

public class HorizontalFreeSpaces extends FreeSpaces {

    @Override
    public boolean check(Board board, int mcoord, int ncoord) {
        boolean checkright = false;
        boolean checkleft = false; 
        // right
        for (int i = ncoord + 1; i <= 14 ; i++) {
            if (board.getCell(mcoord, i).isOccupied()) {
                checkright = true;
                break;
            }
        }
        //left
        for (int i = ncoord - 1; i >= 0; i--) {
            if (board.getCell(mcoord, i).isOccupied()) {
                checkleft = true;
                break;
            }
        }
        return checkright && checkleft;
    }

    @Override
    public int calculateFreeSpaces(Board board, int mcoord, int ncoord) {
        int freespaces = 0;
        int right = ncoord + 1;
        int left = ncoord - 1;
        //right
        while (board.isInBounds(mcoord, right) && !board.getCell(mcoord , right).isOccupied()) {
            freespaces++;
            right++;
        }
        System.out.println("this is the right " + freespaces);
        //left
        while (board.isInBounds(mcoord, left) && !board.getCell(mcoord , left).isOccupied()) {
            freespaces++;
            left--;
        }
        System.out.println("this is the horizontal " + freespaces);
        return freespaces;
    }
}
