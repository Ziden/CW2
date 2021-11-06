/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.toscow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import truco.cards.Card;

/**
 *
 * @author gabri
 */
public class ToscowDeck {

    private static final int MAX_SIZE = 20;

    public List<Card> cards = new ArrayList<Card>();

    public void addCard(Card c) {
        if (cards.size() >= MAX_SIZE) {
            return;
        }
        cards.add(c);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Inventory toInventory(Player p) {
        Inventory i = Bukkit.createInventory(p, InventoryType.CHEST, "Deck");
        for (Card c : cards) {
            i.addItem(c.toItemStack(p));
        }
        return i;
    }
    
    public void fromInventory(Inventory i) {
        cards.clear();
        for(ItemStack ss : i.getContents()) {
            Card c = Card.fromItemStack(ss);
            if(c != null) {
                cards.add(c);
            }
        }
    }
}
