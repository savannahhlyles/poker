## Poker Game

### Card.java
- Represents playing cards.
- Allows easy comparison and string conversion.
- Encodes suit and rank using integers 1-4 and 1-13 respectively.

### Deck.java
- Represents a deck of playing cards.
- Includes functionality for shuffling and dealing cards.
- Provides methods for converting the deck to a string for display.

### Game.java
- Manages a game of video poker.
- Facilitates player interactions such as betting and exchanging cards.
- Determines hand rankings and updates player bankroll accordingly.
- Includes methods for playing the game, displaying cards, and processing results.

### Player.java
- Represents a player in video poker.
- Manages the player's bankroll and betting actions.
- Validates bets and adjusts the bankroll based on winnings or losses.

### Implementation Details:
- The `Card` class encodes playing cards with suit and rank attributes, allowing for easy comparison and string representation.
- In `Deck` class, the Fisher-Yates shuffle algorithm is implemented for shuffling the deck. The class also includes methods for dealing cards and converting the deck to a string for display.
- `Game` class manages the gameplay of video poker, including player interactions and hand evaluation. It enables players to place bets, exchange cards, and determines hand rankings to update player bankrolls accordingly.
- `Player` class manages the player's bankroll and betting actions. It validates bets and adjusts the bankroll based on winnings or losses during the game.
