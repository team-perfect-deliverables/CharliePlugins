/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bot.client;

import charlie.card.Card;
import charlie.card.Hid;
import charlie.util.Play;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jacob
 */
public class BotController
{

    public void shuffled()
    {

    }

    public void deal(Hid hid, Card card, int[] values)
    {

    }

    public void startGame(List<Hid> hids, int shoeSize)
    {

    }

    public Play getPlay()
    {
        return Play.STAY; //Todo: Fix. I put play.STAY so it compiles.
    }

    public int getBet()
    {
        Random random = new Random(); //Todo: Fix.
        int bet = random.nextInt(200) + 5;
        System.out.println("BET AMOUNT = " + bet);
        return bet;
    }
}
