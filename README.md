# Overview

The JackpotGame application is a simple game where users place bets and check whether they win based on a randomly generated matrix. It allows users to configure the game (like symbol probabilities, matrix size, winning conditions, etc.) through a JSON configuration file.

## Installation

Make sure you have Java 11 or higher installed. You can build the application using [Maven](https://maven.apache.org/).

```bash
mvn clean package
```

## Usage

```bash
java -jar target/jackpotGame-1.0-SNAPSHOT.jar --config:config/config.json --betting-amount:100  
```

## Output WINING
```log
2025-07-04 21:31:22 [main] INFO  am.devvibes.matrix.MatrixGenerator - Matrix:
2025-07-04 21:31:22 [main] INFO  am.devvibes.matrix.MatrixGenerator - [ D, B, 10x ]
2025-07-04 21:31:22 [main] INFO  am.devvibes.matrix.MatrixGenerator - [ E, E, F ]
2025-07-04 21:31:22 [main] INFO  am.devvibes.matrix.MatrixGenerator - [ +500, F, F ]
2025-07-04 21:31:22 [main] INFO  am.devvibes.matrix.MatrixGenerator - Game overview: {
  "applied_bonus_symbol" : [ "10x", "+500" ],
  "applied_winning_combinations" : {
    "F" : [ "same_symbol_3_times" ]
  },
  "bet amount" : 100.0,
  "reward" : 1500.0
}

```
## Output LOSE
```log
2025-07-04 21:34:03 [main] INFO  am.devvibes.matrix.MatrixGenerator - Matrix:
2025-07-04 21:34:03 [main] INFO  am.devvibes.matrix.MatrixGenerator - [ MISS, A, B ]
2025-07-04 21:34:03 [main] INFO  am.devvibes.matrix.MatrixGenerator - [ B, E, +1000 ]
2025-07-04 21:34:03 [main] INFO  am.devvibes.matrix.MatrixGenerator - [ 5x, D, D ]
2025-07-04 21:34:03 [main] ERROR am.devvibes.gameutils.GameUtils - You Lose your Bet: 0.0


```