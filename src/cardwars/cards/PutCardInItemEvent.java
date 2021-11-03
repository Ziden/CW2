package cardwars.cards;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;


public class PutCardInItemEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();

    private Player p;
    private Card c;
    private ItemStack i;
    
    public Player getPlayer() {
        return p;
    }
    
    public Card getCard() {
        return c;
    }
    
    public ItemStack getItem() {
        return i;
    }
    
    public PutCardInItemEvent(Player p, Card c, ItemStack i) {
        this.p = p;
        this.c = c;
        this.i = i;
    }
    
     @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;

    }
    
}
