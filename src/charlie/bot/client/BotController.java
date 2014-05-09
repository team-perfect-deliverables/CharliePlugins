/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bot.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugins.Advisor;
import charlie.util.Play;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author jacob
 */
public class BotController
{
    private boolean shuffled = false;
    private int count = 0;
    private int shoeSize = 0;
    private Card dealerCard;
    private ClientHand hand;
    private ArrayList<Card> cards;
    private int CARDS_IN_DECK = 52;
    
    public void shuffled()
    {
        this.count = 0;
    }

    public void deal(Hid hid, Card card, int[] values)
    {
        if (card.getRank() > 9) {
            this.count--;
        } else if (card.getRank() < 7) {
            this.count++;
        }
        
        if (hid.getSeat() == Seat.YOU) {
          this.hand.add(card);  
        } else if (hid.getSeat() == Seat.DEALER) {
            this.dealerCard = card;
        }
        
    }

    public void startGame(List<Hid> hids, int shoeSize)
    {
        // just to initialize
        Hid myHid = hids.get(0);
        for (Hid hid : hids) {
            if (hid.getSeat() == Seat.YOU) {
                myHid = hid;
                break;
            }
        }
        this.shoeSize = shoeSize;

        this.hand = new ClientHand(myHid);

    }

    public Play getPlay()
    {
        return new Advisor().advise(this.hand, this.dealerCard);
    }

    public int getBet()
    {
        // return mininum bet if the game is just starting out
        if (this.shoeSize <= 0) {
            return 5;
        }
        return max(5, 5 * this.count / (this.shoeSize / CARDS_IN_DECK));
    }
}
