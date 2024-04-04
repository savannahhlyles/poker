/* ************************************************
*
* Player.java
*
* This class represents a player in video poker. 
*
* @author: Savannah Lyles
* @date: 03/16/24
* UNI: shl2183
*
***************************************************/
import java.util.Scanner;

public class Player {
    private int bankroll;
    private int bet;
    private Scanner in;
		
    public Player(){		
        bankroll = 100;
        bet = 0;
        in = new Scanner(System.in);
    }
		
    public void bets(int amt){
        //player makes a bet
        boolean valid = false;
        if (amt >= 1 && amt <= 5 && amt < bankroll) {
            valid = true;
            bet = amt;
        }

        while (!valid) {
            if (amt > bankroll) {
                System.out.println("Insufficient tokens. Please bet lower. ");
            }
            else {
                System.out.println
                ("Invalid bet. Please place a bet between 1 and 5. ");
            }
            int newBet = in.nextInt();
            if (newBet >= 1 && newBet <= 5) {
                valid = true;
                bet = newBet;
            }
        }

    }

    public void winnings(int odds){
        //adjust bankroll 
        if (odds > 0) {
            bankroll += (bet * odds);
        }
        else {
            bankroll -= bet;
        }
    }

    public int getBankroll(){
        //return current balance of bankroll
        return bankroll;
    }
}