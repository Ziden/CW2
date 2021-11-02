/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars;

import cardwars.cards.Card;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;


public class CardStorage {
    
    private Map<Integer, Card> Cards = new HashMap<Integer, Card>();

    public CardStorage(HashSet<Card> cards) {
        Cards = cards.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
    }
    
    public Card getCard(int id) {
        return Cards.get(id);
    }
    
    public Card getCardDebug(String name) {
        for(Card c : Cards.values())
            if(c.getName().equalsIgnoreCase(name))
                return c;
        return null;
    }
    
    public Collection<Card> getAllCards() {
        return Cards.values();
    }
}
