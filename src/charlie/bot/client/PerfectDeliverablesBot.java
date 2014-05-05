/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charlie.bot.client;

import charlie.actor.Courier;
import charlie.card.Card;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.util.Play;
import charlie.view.AMoneyManager;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jacob
 */
public class PerfectDeliverablesBot implements IGerty
{

    private final BotController controller = new BotController();
    private AMoneyManager moneyManager;
    private Courier courier;
    private boolean turn = false;

    private int wins = 0;
    private int losses = 0;
    private int pushes = 0;
    private int blackjacks = 0;
    private int charlies = 0;
    private int breaks = 0;

    @Override
    public void go()
    {
        int bet = controller.getBet();
        int currentBet = bet;

        //If we want to bet less than is on the table, clear the table.
        if (bet < moneyManager.getWager())
        {
            moneyManager.clearBet();
        }
        
        //Adjusts what is left to bet for what is on the table
        currentBet -= moneyManager.getWager();
        
        while (currentBet > 4)
        {
            try
            {
                Random random = new Random();
                Thread.sleep(random.nextInt(2000) + 1000);
            } catch (InterruptedException ex)
            {
                //Just swallow it.
            }
            if (currentBet > 100)
            {
                moneyManager.upBet(100);
                currentBet -= 100;
            }
            else if (currentBet > 25)
            {
                moneyManager.upBet(25);
                currentBet -= 25;
            }
            else
            {
                moneyManager.upBet(5);
                currentBet -= 5;
            }
        }
        
        //Send the bet to the server
        courier.bet(bet, 0);
    }

    @Override
    public void setCourier(Courier courier)
    {
        this.courier = courier;
    }

    @Override
    public void setMoneyManager(AMoneyManager moneyManager)
    {
        this.moneyManager = moneyManager;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(Graphics2D g)
    {
        //TODO: Display stats
    }

    @Override
    public void startGame(List<Hid> hids, int shoeSize)
    {
        controller.startGame(hids, shoeSize);
    }

    @Override
    public void endGame(int shoeSize)
    {

    }

    @Override
    public void deal(Hid hid, Card card, int[] values)
    {
        controller.deal(hid, card, values);

        //This is our card
        if (hid.getSeat() == Seat.YOU)
        {
            //It's our turn, so we should play
            if (turn)
            {
                doPlay(hid, controller.getPlay());
            }
        }
        //Not our turn so make sure we don't think it is
        else
        {
            turn = false;
        }
    }

    @Override
    public void insure()
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void bust(Hid hid)
    {
        if (hid.getSeat() == Seat.YOU)
        {
            breaks++;
        }
    }

    @Override
    public void win(Hid hid)
    {
        if (hid.getSeat() == Seat.YOU)
        {
            wins++;
        }
    }

    @Override
    public void blackjack(Hid hid)
    {
        if (hid.getSeat() == Seat.YOU)
        {
            blackjacks++;
        }
    }

    @Override
    public void charlie(Hid hid)
    {
        if (hid.getSeat() == Seat.YOU)
        {
            charlies++;
        }
    }

    @Override
    public void lose(Hid hid)
    {
        if (hid.getSeat() == Seat.YOU)
        {
            losses++;
        }
    }

    @Override
    public void push(Hid hid)
    {
        if (hid.getSeat() == Seat.YOU)
        {
            pushes++;
        }
    }

    @Override
    public void shuffling()
    {
        controller.shuffled();
    }

    @Override
    public void play(Hid hid)
    {
        turn = true;

        if (hid.getSeat() == Seat.YOU)
        {
            doPlay(hid, controller.getPlay());
        }
    }

    public void doPlay(Hid hid, Play play)
    {
        final Hid myHid = hid;
        final Play myPlay = play;

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(2000) + 1000);
                } catch (InterruptedException ex)
                {
                    //Just swallow it.
                }
                if (myPlay == Play.HIT)
                {
                    courier.hit(myHid);
                }
                else if (myPlay == Play.STAY)
                {
                    courier.stay(myHid);
                }
                else if (myPlay == Play.DOUBLE_DOWN)
                {
                    courier.dubble(myHid);
                    turn = false;
                }
                else if (myPlay == Play.NONE)
                {
                    //Do nothing, not my turn.
                    turn = false;
                }
                else if (myPlay == Play.SPLIT)
                {
                    //Can't split, so stay.
                    courier.stay(myHid);
                }
            }
        }).start();
    }
}
