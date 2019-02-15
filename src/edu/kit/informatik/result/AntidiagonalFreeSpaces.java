package edu.kit.informatik.result;

import edu.kit.informatik.data.Board;

public class AntidiagonalFreeSpaces extends FreeSpaces {

    @Override
    public boolean check(Board board, int mcoord, int ncoord) {
        boolean checkupper = false;
        boolean checklower = false;
        
        //upper
        for (int i = mcoord - 1, j = ncoord + 1; board.isInBounds(i, j); i--, j++) {
            if (board.getCell(i, j).isOccupied()) {
                checkupper = true;
                break;
            }
        }
        //lower
        for (int i = mcoord + 1, j = ncoord - 1; board.isInBounds(i, j); i++, j--) {
            if (board.getCell(i, j).isOccupied()) {
                checklower = true;
                break;
            }
        }
        
        if (mcoord == 10 || ncoord == 0) {
            checklower = true;
        }
        
        if (mcoord == 0 || ncoord == 14) {
            checkupper = true;
        }
        
        return checklower && checkupper;
    }

    @Override
    public int calculateFreeSpaces(Board board, int mcoord, int ncoord) {
        int freespaces = 0;
        int lowerm = mcoord + 1;
        int lowern = ncoord - 1;
        int upperm = mcoord - 1;
        int uppern = ncoord + 1;
        int rightm = mcoord;
        int rightn = ncoord + 1;
        int leftm = mcoord;
        int leftn = ncoord - 1;
        int upm = mcoord - 1;
        int upn = ncoord;
        int downm = mcoord + 1;
        int downn = ncoord;
        
        //upper
        while (board.isInBounds(upperm, uppern) 
                && board.isInBounds(leftm, leftn)
                && board.isInBounds(rightm, rightn)
                && !board.getCell(upperm, uppern).isOccupied()
                && (!board.getCell(rightm, rightn).isOccupied() || !board.getCell(upm, upn).isOccupied())) {
            freespaces++;
            upperm--;
            uppern++;
            rightn++;
            upm--;
        }
        System.out.println("this is the antidiagonal upper " + freespaces);
        //lower
        while (board.isInBounds(lowerm, lowern) 
                && board.isInBounds(leftm, leftn)
                && board.isInBounds(downm, downn)
                && !board.getCell(lowerm, lowern).isOccupied()
                && (!board.getCell(leftm, leftn).isOccupied() || !board.getCell(downm, downn).isOccupied())) {
            freespaces++;
            lowerm++;
            lowern--;
            downm++;
            leftn--;
        }
        System.out.println("this is the antidiagonal total " + freespaces);
        return freespaces;
    }

}
