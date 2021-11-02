package cardwars.cards;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class UseCardEvent extends Event {
    
    private static HandlerList handlers = new HandlerList();

    private Player p;
    private Card c;
    
    public Player getPlayer() {
        return p;
    }
    
    public Card getCard() {
        return c;
    }
    
    public UseCardEvent(Player p, Card c) {
        this.p = p;
        this.c = c;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
}
