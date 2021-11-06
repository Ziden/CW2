/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.cards;

import truco.cards.Card;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class CardStorage {
    
    private Map<String, Card> CardsByName = new HashMap<String, Card>();
    private Map<UUID, Card> CardsByID = new HashMap<UUID, Card>();
    
    public void setCards(HashSet<Card> cards) {
        for(Card c: cards) {
            CardsByName.put(c.getName().toLowerCase(), c);
            CardsByID.put(c.getId(), c);
        }
    }
    
    public boolean hasCard(String name) {
        return CardsByName.containsKey(name.toLowerCase());
    }
    
    public Card getCard(String name) {
        return CardsByName.getOrDefault(name.toLowerCase(), null);
    }
    
    public Card getCard(UUID id) {
        return CardsByID.getOrDefault(id, null);
    }
    
    public Collection<Card> getAllCards() {
        return CardsByName.values();
    }
}
