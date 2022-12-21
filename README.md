# The game of Chomp with an AI player

This is the game [Chomp](https://en.wikipedia.org/wiki/Chomp) for Java. At the beginning the player chooses the width
and height for the board (not more than 16 possible atm). He then starts with the first move followed by which the AI
player makes his turn until the game is over (no more chocolate left).

The AI uses a simple minimax algorithm. For each level in the game tree the maximizing player takes the first winning
move and the minimizing player takes the first losing move. If no such turn is found the first possible turn is chosen.
Whenever a winning move for a particular chocolate bar is found it is stored into a HashMap.