# MAZEBRUTE

This is my solution to a maze game that works with API requests.
You send commands to your player via Rest Api calls.
You can; enter maze, pick up coins, head to the exit, leave the maze (etc).

This was coded from scratch in two days.

Here are my notes for the project so far. Let's call this version - V0.1(Mvp)
-
MazeBrute:
MVP complete: All mazes traversed with a score included.

Improvements to make:
* An ApiCall (Object) to remove repeat code and handle all the params necessary for the HttpURLConnection. 
  (It should also handle its try/catch clauses too - making this much neater!)
* A better maze running algorithm.
* A way to track mazes so nuances within each can be accounted for (i.e., putting the exit on the first tile!)
* Unit tests. Of course!
* Use of Lombok.(because I've been wanting to try it!)
