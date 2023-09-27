# MAZE-BRUTE

This is my solution to a maze game that works with API requests.
You send commands to your player via Rest Api calls.
You can; enter maze, pick up coins, head to the exit, leave the maze (etc).
More information can be found here: https://maze.hightechict.nl/dox/amazing-openapi.html#/

This was coded from scratch in two days.

Here are my notes for version - V0.1(Mvp)
-
Maze-Brute:
MVP complete: All mazes traversed with a score included.

Improvements to make:
* A better maze running algorithm.
* A way to track mazes so nuances within each can be accounted for (i.e., putting the exit on the first tile!)
* More unit tests. Of course!
* Use of Lombok.(because I've been wanting to try it!)
* Extract the different Exception responses in the ApiObject to get more detailed logs so to find other things in the game (for example; the easter egg)
* (Maybe overkill but) - to extract out the hard coded connection data into an applications.properties file
