import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by chichunchen on 9/4/17.
 */
public class Solver {
    private int moves;
    private MinPQ<Board> minPQ;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        this.moves = 0;
        minPQ = new MinPQ<>();
    }

//    public boolean isSolvable()            // is the initial board solvable?
//    {
//
//    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return moves;
    }

//    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
//    {
//
//    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {

    }
}