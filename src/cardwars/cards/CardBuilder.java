/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars.cards;

import cardwars.cards.Card;
import cardwars.cards.Rarity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author gabri
 */
public class CardBuilder {
    
    private HashSet<Card> built = new HashSet<Card>();
    
    private Card card;
    
    public HashSet<Card> GetCards() {
        if(card != null) 
            built.add(card);
        return built;
    }
    public CardBuilder createCard(String name, Rarity rarity, String ... desc) {
        if(card != null) 
            built.add(card);
        card = new Card(name, rarity);
        card.setDescription(desc);
        return this;
    }
    
    public CardBuilder thatGivesItem(Material m) {
        card.AddHook((CardHook<UseCardEvent>) e -> e.getPlayer().getInventory().addItem(new ItemStack(m)));
        card.setDescription("Gives you a "+m.name());
        return this;
    }
    
    public CardBuilder withHook(CardHook hook) {
        card.AddHook(hook);
        return this;
    }
    
}
