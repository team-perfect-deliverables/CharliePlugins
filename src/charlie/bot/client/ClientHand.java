/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.bot.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;

/**
 *
 * @author travis
 */
public class ClientHand extends Hand {
    
    public ClientHand(Hid hid) {
        super(hid);
    }
    
    public void add(Card card) {
        this.cards.add(card);
    }
    
    
    
}
