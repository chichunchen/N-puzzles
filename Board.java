import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by chichunchen on 9/4/17.
 */
public class Board {

    private final int[][] blocks;
    private final int dimension;
    private final int hamming;
    private final int blank;
    private final int manhattan;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }

        this.dimension = blocks.length;
        // defense copy of blocks
        int[][] blockCopy = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension;  i++) {
            for (int j = 0; j < this.dimension; j++) {
                blockCopy[i][j] = blocks[i][j];
            }
        }
        this.blocks = blockCopy;

        // local variables for computing blank, hamming, and manhattan
        int ham = 0, man = 0;
        int index = 1;
        int zeroPos = 0;
        int total = dimension * dimension;

        // compute hamming and find blank position
        for (int[] arr : blocks) {
            for (int currElem: arr) {

                // compute hamming
                if (index != currElem && index < total) {
                    ham++;
                }

                // compute manhattan
                int dest = currElem,
                    curr = index;

                // don't compute 0
                if (currElem != 0) {
                    int currY = (int) Math.ceil((double) curr / this.dimension);
                    int destY = (int) Math.ceil((double) dest / this.dimension);
                    int currX = curr % this.dimension == 0 ? this.dimension : curr % this.dimension;
                    int destX = dest % this.dimension == 0 ? this.dimension : dest % this.dimension;

                    // y
                    while (currY != destY) {
                        // go up
                        if (currY > destY) {
                            // StdOut.println("curr " + dest + " goes up");
                            currY--;
                            man++;
                        }
                        // go down
                        else if (currY < destY) {
                            // StdOut.println("curr " + dest + " goes down");
                            currY++;
                            man++;
                        }
                    }
                    // x
                    while (currX != destX) {
                        // go left
                        if (currX > destX) {
                            // StdOut.println("curr " + dest + " goes left");
                            currX--;
                            man++;
                        }
                        // go right
                        else if (currX < destX) {
                            // StdOut.println("curr " + dest + " goes right");
                            currX++;
                            man++;
                        }
                    }
                }

                // find blank
                if (currElem == 0) {
                    zeroPos = index;
                }
                index++;
            }
        }

        this.blank = zeroPos;
        this.hamming = ham;
        this.manhattan = man;
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
        return dimension;
    }

    public int hamming()                   // number of blocks out of place
    {
        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return hamming == 0;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] tBlocks = new int[this.dimension][this.dimension];

        // copy all block elements
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                tBlocks[i][j] = this.blocks[i][j];
            }
        }

        // swap elements that is neighbor also both not 0
        for (int j = 0; j < this.dimension; j++) {
            if (tBlocks[0][j] != 0) {
                int destX = j, destY = 0;

                if ((destX+1) < dimension && (tBlocks[0][j+1] != 0)) {
                    destX++;
                }
                else if ((destX-1) > 0 && (tBlocks[0][j-1] != 0)) {
                    destX--;
                }
                else if ((destY+1) < dimension && (tBlocks[1][j] != 0)) {
                    destY++;
                }
//                StdOut.println("dest_x " + dest_x + " dest_y " + dest_y);

                int temp;
                temp = tBlocks[0][j];
                tBlocks[0][j] = tBlocks[destY][destX];
                tBlocks[destY][destX] = temp;

                break;
            }
        }
        return new Board(tBlocks);
    }

    public boolean equals(Object other)        // does this board equal y?
    {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;

        Board that = (Board) other;

        if (that.dimension != this.dimension) return false;
        if (that.blank != this.blank) return false;
        if (that.hamming != this.hamming) return false;
        if (that.manhattan != this.manhattan) return false;

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (that.blocks[i][j] != this.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /* deep copy 2-dimension block */
    private int[][] copyBlocks(int [][]tiles) {
        int[][] result = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                result[i][j] = tiles[i][j];
            }
        }
        return result;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> stack = new Stack<>();
        int blankX = (this.blank % this.dimension == 0 ? this.dimension : this.blank % this.dimension)-1;
        int blankY = (int) Math.ceil((double) this.blank / this.dimension)-1;

        // StdOut.println("blank_x: " + blank_x + " blank_y: " + blank_y);

        // add Board that swap with right
        if ((blankX+1) < this.dimension) {

            int[][] tiles = copyBlocks(this.blocks);
            int temp = tiles[blankY][blankX];
            tiles[blankY][blankX] = tiles[blankY][blankX+1];
            tiles[blankY][blankX+1] = temp;

            stack.push(new Board(tiles));
        }

        // add Board that swap with left
        if ((blankX-1) >= 0) {

            int[][] tiles = copyBlocks(this.blocks);
            int temp = tiles[blankY][blankX];
            tiles[blankY][blankX] = tiles[blankY][blankX-1];
            tiles[blankY][blankX-1] = temp;

            stack.push(new Board(tiles));
        }

        // add Board that swap with below
        if ((blankY+1) < this.dimension) {

            int[][] tiles = copyBlocks(this.blocks);
            int temp = tiles[blankY][blankX];
            tiles[blankY][blankX] = tiles[blankY+1][blankX];
            tiles[blankY+1][blankX] = temp;

            stack.push(new Board(tiles));
        }

        // add Board that swap with up
        if ((blankY-1) >= 0) {

            int[][] tiles = copyBlocks(this.blocks);
            int temp = tiles[blankY][blankX];
            tiles[blankY][blankX] = tiles[blankY-1][blankX];
            tiles[blankY-1][blankX] = temp;

            stack.push(new Board(tiles));
        }

        return stack;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder sb = new StringBuilder(this.dimension + "\n");
        // sb.append("Manhattan: " + manhattan() + "\n");
        // sb.append("Hamming: " + hamming() + "\n");
        for (int[] arr : this.blocks) {
            for (int i: arr) {
                sb.append(String.format("%2d ", i));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
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

            testNeighbor(tiles);
            testTwin(tiles);
        }
    }

    private static void testNeighbor(int[][] tiles) {
        StdOut.println("testNeighbor");
        Board b1 = new Board(tiles);
        StdOut.println("b1: \n" + b1 + "\n");

        for (Board b: b1.neighbors()) {
            StdOut.println(b);
        }
    }

    private static void testTwin(int[][] tiles) {
        StdOut.println("testTwin");
        Board b1 = new Board(tiles);
        Board b2 = b1.twin();
        StdOut.println(b1);
        StdOut.println(b2);

        assert !b1.equals(b2);
    }
}