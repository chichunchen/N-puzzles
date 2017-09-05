import edu.princeton.cs.algs4.*;

import java.util.Comparator;
/**
 * Created by chichunchen on 9/4/17.
 */
public class Solver {
    private MinPQ<SearchNode> origPQ;            // original pq
    private MinPQ<SearchNode> twinPQ;            // for detecting whether the swapped board, e.g., twin board is solvable
    private Board initialBoard;
    private Board twin;
    private SearchNode minSearchNode;
    private Stack<Board> solutions;

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        solutions = new Stack<>();
        this.origPQ = new MinPQ<>(new sortByManhattan());
//        this.twinPQ = new MinPQ<>(new sortByManhattan());
        this.initialBoard = initial;
//        this.twin = initialBoard.twin();

        origPQ.insert(new SearchNode(initial, null, 0));
        minSearchNode = origPQ.delMin();

        // if minSearchNode does not in goal state
        // add all the neighbors in queue
        while(!minSearchNode.currBoard.isGoal()) {
//            StdOut.println(minSearchNode.currBoard);
            for (Board b : minSearchNode.currBoard.neighbors()) {
                if (minSearchNode.prevSearchNode == null || (!minSearchNode.prevSearchNode.currBoard.equals(b))) {
                    SearchNode searchNode = new SearchNode(b, minSearchNode, minSearchNode.moves + 1);
                    origPQ.insert(searchNode);
//                    StdOut.println("Add neighbor: " + searchNode.currBoard);
                }
            }
            minSearchNode = origPQ.delMin();
        }

        // reconstruct solution from minSearchNode
        solutions = new Stack<>();
        SearchNode head = minSearchNode;
        while(head.prevSearchNode != null) {
            solutions.push(head.currBoard);
            head = head.prevSearchNode;
        }

//        StdOut.println(minSearchNode.currBoard);
    }

    class sortByHamming implements Comparator<SearchNode>
    {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int this_total = o1.hammingPriority();
            int other_total = o2.hammingPriority();

            if (this_total < other_total) {       //return <0 if than other
//                StdOut.println("moves so far: " + o1.moves + " hamming: " + o1.currBoard.manhattan());
                return -1;
            }
            else if (this_total > other_total) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    class sortByManhattan implements Comparator<SearchNode>
    {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.manhattanPriority() < o2.manhattanPriority()) {       //return <0 if than other
//                StdOut.println("moves so far: " + o1.moves + " manhattan: " + o1.currBoard.manhattan());
                return -1;
            }
            else if (o1.manhattanPriority() > o2.manhattanPriority()) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return true;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return minSearchNode.moves;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return solutions;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);

            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution()) {
                    StdOut.println(board);
                }
            }
        }
    }
}

class SearchNode {
    Board currBoard;
    SearchNode prevSearchNode;
    int moves;      // moves made so far

    public SearchNode(Board board, SearchNode prev, int moves) {
        this.currBoard = board;
        this.prevSearchNode = prev;
        this.moves = moves;
    }

    public String toString() {
        return "moves: " + moves + "\n" + currBoard.toString();
    }

    public int manhattanPriority() {
        return moves + currBoard.manhattan();
    }

    public int hammingPriority() {
        return moves + currBoard.hamming();
    }
}