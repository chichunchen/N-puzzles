import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * Created by chichunchen on 9/4/17.
 */
public class Solver {
    private int moves;
    private MinPQ<Board> minPQ;
    private ArrayList<Board> solutions;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        this.moves = 0;
        this.solutions = new ArrayList<>();
        this.minPQ = new MinPQ<>();
        Board currBoard = initial;
        Board prevBoard = initial;
        minPQ.insert(currBoard);
        solutions.add(currBoard);

        if (minPQ.delMin().isGoal()) {      // pop the initial
            return;
        }
        else {                              // since initial is not in goal state, push initial's neighbors
            do {
                for (Board b : currBoard.neighbors()) {

                    // critical optimization
                    // don't enqueue a neighbor that is the same as the previous search node
                    if (prevBoard == initial || b != prevBoard) {
                        minPQ.insert(b);
                    }
                }
                moves++;
                StdOut.println("prev: " + prevBoard);
                StdOut.println("curr: " + currBoard);
                solutions.add(currBoard);
                prevBoard = currBoard;
                currBoard = minPQ.delMin();
            } while(!currBoard.isGoal());
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return true;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return moves;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return solutions;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {

    }
}