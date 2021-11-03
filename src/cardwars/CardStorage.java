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
    
    private Map<String, Card> Cards = new HashMap<String, Card>();

    public CardStorage(HashSet<Card> cards) {
        Cards = cards.stream().collect(Collectors.toMap(x -> x.getName(), x -> x));
    }
    
    public boolean hasCard(String name) {
        return Cards.containsKey(name);
    }
    
    public Card getCard(String name) {
        return Cards.getOrDefault(name, null);
    }
    
    public Collection<Card> getAllCards() {
        return Cards.values();
    }
}
