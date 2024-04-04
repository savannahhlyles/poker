/* ************************************************
*
* Deck.java
*
* This class represents a deck of playing cards. 
*
* @author: Savannah Lyles
* @date: 03/09/24
* UNI: shl2183
*
***************************************************/

import java.util.Random;

public class Deck {
	
    private Card[] cards;
    private int top; // the index of the top of the deck

    public Deck(){
        // initialize instance variables
        cards = new Card[52];
        top = 0;

        // fill the deck
        int deckIndex = 0;
        for (int s = 1; s <= 4; s++) {
            for (int r = 1; r <= 13; r++) {
                cards[deckIndex] = new Card(s, r); 
                deckIndex++;
            }
        }
    }
	
    public void shuffle() {
        // Fisher-Yates shuffle algorithm
        Random random = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }
    
    public Card deal(){
        // deal a single card
        if (top < cards.length) {
            return cards[top++];
        }
        else {
            shuffle();
            top = 0;
            return cards[top++];
        }
    }

    public String toString(){
        // prints the entire deck in its current order
        String deck = "";
        for (Card card : cards) {
            deck += String.format(card + "\n");
        }
        return deck;
    }
}