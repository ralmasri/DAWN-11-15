package edu.kit.informatik.result;

import edu.kit.informatik.data.Board;

public abstract class FreeSpaces {

    public abstract boolean check(Board board, int mcoord, int ncoord);
    
    public abstract int calculateFreeSpaces(Board board, int mcoord, int ncoord);
        
}
