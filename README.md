## N Puzzles

### Notes
- Use a class SearchNode to record Board, previous SearchNode, and the
number of moves to its current state, and which should be the previous
`moves+1`
- The timing to break the tie is important
- Immunity improves the performance of the program

### Test Case that has passed
```
puzzle00~03
puzzle2x2-**~06
puzzle3x3-**.txt
puzzle4x4-00~-45.txt
    (out of memory in test puzzle4x4-50.txt)
puzzle00~50.txt
all unsolvable
```

### References
[Spec](http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html)

[Check this to see how to improve much further](http://coursera.cs.princeton.edu/algs4/checklists/8puzzle.html)