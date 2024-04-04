/* ************************************************
*
* Card.java
*
* This class represents playing cards.
*
* @author: Savannah Lyles
* @date: 03/09/24
* UNI: shl2183
*
***************************************************/

public class Card implements Comparable<Card>{
	
    private int suit; // use integers 1-4 to encode the suit
    private int rank; // use integers 1-13 to encode the rank

    // suit
    private int clubs = 1;
    private int diamonds = 2;
    private int hearts = 3;
    private int spades = 4;

    // rank
    private int jack = 11;
    private int queen = 12;
    private int king = 13;
    private int ace = 1;

    // constructor
    public Card(int s, int r){
        this.suit = s;
        this.rank = r;
    }
	
    public int compareTo(Card c) {
        // this method compares cards so they
        // may be easily sorted
        int answer = 0;
        if (this.rank > c.rank) {
            answer = 1;
        }
        else if (this.rank == c.rank) {
            if (this.suit > c.suit) {
                answer = 1;
            }
            else if (this.suit < c.suit) {
                answer = -1;
            }
        }
        else {
            answer = -1;
        }
        return answer;
    }
	
    public String toString(){
        //use this method to easily print a Card object
        String rankString;
        String cardString;

        rankString = setRankString(rank);
        cardString = setCardString(rankString, suit);
        return cardString;
    }

    // Leave this method alone, it's to help us test your work
    public int getSuit(){
        return suit;
    }

    // Leave this method alone, it's to help us test your work
    public int getRank(){
        return rank;
    }

    // helper methods for toString method
    public String setRankString(int rank) {
        String rankString;
        if (rank == 1) {
            rankString = "Ace";
        }
        else if (rank == 11) {
            rankString = "Jack";
        }
        else if (rank == 12) {
            rankString = "Queen";
        }
        else if (rank == 13) {
            rankString = "King";
        }
        else {
            rankString = String.valueOf(rank);
        }
        return rankString;
    }

    public String setCardString(String rankString, int suit) {
        String cardString;
        if (suit == clubs) {
            cardString = rankString + " of Clubs";
        }
        else if (suit == diamonds) {
            cardString = rankString + " of Diamonds";
        }
        else if (suit == hearts) {
            cardString = rankString + " of Hearts";
        }
        else {
            cardString = rankString + " of Spades";
        }
        return cardString;
    }


}