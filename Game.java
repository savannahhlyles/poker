/* ************************************************
*
* Game.java
*
* This class represents a game of video poker. 
*
* @author: Savannah Lyles
* @date: 03/19/24
* UNI: shl2183
*
***************************************************/

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
    private Player p;
    private Deck cards;
    private ArrayList<Card> hand;
    private Scanner in;
    private String playStatus;
	
    public Game(String[] testHand){ 
        p = new Player();
        cards = new Deck();
        hand = new ArrayList<>();
        in = new Scanner(System.in);

        parseTestHand(testHand);
    }

    public Game(){
        p = new Player();
        cards = new Deck();
        hand = new ArrayList<Card>();
        in = new Scanner(System.in);
        playStatus = "Y";
    }
	
    public void play(){
        System.out.println("Welcome to Video Poker!");
        System.out.println("Current tokens: " + p.getBankroll());

        while (playStatus.equalsIgnoreCase("Y") && (p.getBankroll() > 0)) {
            newHand();

            int bet = getBet();
            p.bets(bet);

            System.out.println("Your cards: ");
            displayCards(hand);

            exchangeCards(hand, getExchangeCount());
            System.out.println("Your cards: ");
            displayCards(hand);

            int multiplier = checkHand(hand);
            p.winnings(multiplier);
            
            playStatus = results((bet * multiplier), p);
        }
    }
    
    public void testPlay(){
        // this method is used for testing the checkHand method
        // it should evaluate the hand that was passed
        // as a command-line argument and print the result
        System.out.println("Test hand: ");
        displayCards(hand);
        System.out.print("Result: ");
        checkHand(hand);
    }
	
	public int checkHand(ArrayList<Card> hand){
        return checkRoyalFlush(hand) ? royalFlushResults() : 
               checkStraightFlush(hand) ? straightFlushResults() :
               checkKind(hand) == 4 ? fourKindResults() :
               checkFullHouse(hand) ? fullHouseResults() :
               checkFlush(hand) ? flushResults() :
               checkStraight(hand) ? straightResults() :
               checkKind(hand) == 3 ? threeKindResults() :
               pairResults(hand);
    }



    // helper methods

    // helper method for test constructor
    private void parseTestHand(String[] testHand) {
        for (String cardString : testHand) {
            char suitChar = cardString.charAt(0);
            int rank = Integer.parseInt(cardString.substring(1));
            int suit;
            if (suitChar == 'c') {
                suit = 1;
            }
            else if (suitChar == 'd') {
                suit = 2;
            }
            else if (suitChar == 'h') {
                suit = 3;
            }
            else {
                suit = 4;
            }
            hand.add(new Card(suit, rank));
        }
    }

    // helper methods for play method
    private int getBet() {
        System.out.print("Please place a bet between 1 and 5. ");
        return in.nextInt();
    }

    private int getExchangeCount() {
        System.out.print("How many cards (0-5) would you like to exchange? ");
        return in.nextInt();
    }

    private String results(int payout, Player p) {
        System.out.println("Payout: " + payout);
        System.out.println("Current tokens: " + p.getBankroll());
        System.out.print("Would you like to play another round? (Y/N) ");
        in.nextLine();
        return in.nextLine();
    }

    private void newHand() {
        hand.clear();
        cards.shuffle(); // shuffle after every hand
        for (int i = 0; i < 5; i++) {
            hand.add(cards.deal());
        }
    }

    private void displayCards(ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i));
        }
    }       

    private void exchangeCards(ArrayList<Card> hand, int count) {
        if (count == 0) {
            return;
        }

        while (count > 0) {
            System.out.print("Which card would you like to exchange (1-5)? ");
            int cardNum = in.nextInt();
            hand.set(cardNum - 1, cards.deal());
            count--;
        }
    }

    // helper methods for checkHand
    private int checkPairs(ArrayList<Card> hand) {
        int pairCount = 0;
        int[] rankCount = new int[14];

        for (int i = 0; i < hand.size(); i++) {
            int currentRank = hand.get(i).getRank();
            rankCount[currentRank]++;
        }

        for (int count : rankCount) {
            if (count >= 2) {
                pairCount += count / 2; 
            }
        }

        return pairCount;
    }

    private int checkKind(ArrayList<Card> hand) {
        int[] rankFrequency = new int[14];

        // count the frequency of each rank
        for (Card card : hand) {
            int rank = card.getRank();
            rankFrequency[rank]++;
        }

        // check for four of a kind
        for (int i = 1; i < rankFrequency.length; i++) {
            if (rankFrequency[i] == 4) {
                return 4;
            }
        }

        // check for three of a kind
        for (int i = 1; i < rankFrequency.length; i++) {
            if (rankFrequency[i] == 3) {
                return 3;
            }
        }

        return 0; 
    }

    private boolean checkStraight(ArrayList<Card> hand) {
        // special cases: ace
        if (straightWithAce(hand)) {
            return true;
        }
        else if (checkIncreasing(hand)) {
            return true;
        }
        else if (checkDecreasing(hand)) {
            return true;
        }
        else {
            return false;
        }
    }
        
    // helper method for checkStraight method for special case
    private boolean straightWithAce(ArrayList<Card> hand) {
        if (hand.get(0).getRank() == 10 && hand.get(1).getRank() 
        == 11 && hand.get(2).getRank() == 12 &&
                hand.get(3).getRank() == 13 && 
                hand.get(4).getRank() == 1) {
            return true;
        }
        else if (hand.get(0).getRank() == 1 && hand.get(1).getRank() 
        == 13 && hand.get(2).getRank() == 12 &&
                hand.get(3).getRank() == 11 && 
                hand.get(4).getRank() == 10) {
            return true;
        }
        return false;
    }

    // helper methods for checkStraight method
    private boolean checkIncreasing(ArrayList<Card> hand) {
        int currentRank = hand.get(0).getRank();
        for (int j = 1; j < hand.size(); j++) {
            int compareRank = hand.get(j).getRank();

            if (compareRank != currentRank + 1) {
                return false;
            }
            currentRank = compareRank;
        }
        return true; 
    }

    private boolean checkDecreasing(ArrayList<Card> hand) {
        int currentRank = hand.get(0).getRank();
        for (int j = 1; j < hand.size(); j++) {
            int compareRank = hand.get(j).getRank();

            if (compareRank != currentRank - 1) {
                return false;
            }
            currentRank = compareRank;
        }
        return true; 
    }

    // helper methods for checkHand continued
    private boolean checkFlush(ArrayList<Card> hand) {
        int currentSuit = hand.get(0).getSuit();

        for (int j = 1; j < hand.size(); j++) {
            int compareSuit = hand.get(j).getSuit();

            if (compareSuit != currentSuit) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFullHouse(ArrayList<Card> hand) {
    boolean threeOfAKind = false;
    boolean pair = false;

    int[] rankCount = new int[14]; 

    for (int i = 0; i < hand.size(); i++) {
        int currentRank = hand.get(i).getRank();
        rankCount[currentRank]++;
    }

    for (int count : rankCount) {
        if (count == 3) {
            threeOfAKind = true;
        } else if (count == 2) {
            pair = true;
        }
    }

    return threeOfAKind && pair; 
    }

    private boolean checkStraightFlush(ArrayList<Card> hand) {
        return checkStraight(hand) && checkFlush(hand);
    }

    private boolean checkRoyalFlush(ArrayList<Card> hand) { 
    boolean straightFlush = checkStraightFlush(hand);
    if (straightFlush) {
        if (startsWith10(hand)) {
            return true;
        }
        else if (startsWithAce(hand)) {
            return true;
        }
    }
    return false; 
    }

    // helper methods for checkRoyalFlush
    private boolean startsWith10(ArrayList<Card> hand) {
        if (hand.get(0).getRank() == 10) {
            boolean hasJack = false;
            boolean hasQueen = false;
            boolean hasKing = false;
            boolean hasAce = false;
            
            if (hand.get(1).getRank() == 11) {
                hasJack = true;
            }
            if (hand.get(2).getRank() == 12) {
                hasQueen = true;
            }
            if (hand.get(3).getRank() == 13) {
                hasKing = true;
            }
            if (hand.get(4).getRank() == 1) { 
                hasAce = true;
            }
            return hasJack && hasQueen && hasKing && hasAce;
        }
        return false;
    }

    private boolean startsWithAce(ArrayList<Card> hand) {
        if (hand.get(0).getRank() == 1) {
            boolean hasTen = false;
            boolean hasJack = false;
            boolean hasQueen = false;
            boolean hasKing = false;
            
            if (hand.get(1).getRank() == 13) {
                hasKing = true;
            }
            if (hand.get(2).getRank() == 12) {
                hasQueen = true;
            }
            if (hand.get(3).getRank() == 11) {
                hasJack = true;
            }
            if (hand.get(4).getRank() == 10) {
                hasTen = true;
            }
            return hasTen && hasJack && hasQueen && hasKing;
        }
        return false;
    }


    // helper methods for printing final results in checkHand
    private int royalFlushResults() {
        System.out.println("Royal Flush");
        return 250;
    }

    private int straightFlushResults() {
        System.out.println("Straight Flush");
        return 50;
    }

    private int fourKindResults() {
        System.out.println("Four of a kind");
        return 25;
    }

    private int fullHouseResults() {
        System.out.println("Full House");
        return 6;
    }

    private int flushResults() {
        System.out.println("Flush");
        return 5;
    }

    private int straightResults() {
        System.out.println("Straight");
        return 4;
    }

    private int threeKindResults() {
        System.out.println("Three of a kind");
        return 3;
    }

    private int pairResults(ArrayList<Card> hand) {
        if (checkPairs(hand) == 2) {
            System.out.println("Two pairs");
            return 2;
        }
        else if (checkPairs(hand) == 1) {
            System.out.println("One pair");
            return 1;
        }
        else {
            System.out.println("No pair");
            return 0;
        }
    }

}