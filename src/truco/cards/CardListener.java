package truco.cards;

import truco.cards.Card;
import truco.customevents.PutCardInItemEvent;
import truco.customevents.UseCardEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;


public class CardListener implements Listener {
    
    @EventHandler
    public void interact(PlayerInteractEvent ev) {
        if(ev.getItem() == null)
            return;
        
        Card c = Card.fromItemStack(ev.getItem());
        if(c==null)
            return;
        
        UseCardEvent useEv = new UseCardEvent(ev.getPlayer(), c, ev.getItem());
        if(c.runHook(useEv)) {
             Bukkit.getServer().getPluginManager().callEvent(useEv);
        }
    }
    
    @EventHandler
    public void inventory(InventoryClickEvent ev) {
        if(ev.getCursor() == null) return;
        
        Card c = Card.fromItemStack(ev.getCursor());
        PutCardInItemEvent newEv = new PutCardInItemEvent((Player)ev.getWhoClicked(), c, ev.getCurrentItem());
        if(c != null) {
            if(c.runHook(ev)) {
                 Bukkit.getServer().getPluginManager().callEvent(newEv);
            }
        }
    }
    
    @EventHandler
    public void blockBreak(BlockBreakEvent ev) {
        Card.runEventHooks(ev, ev.getPlayer());
    }
    
    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent ev) {
        Card.runEventHooks(ev, ev.getPlayer());
    }
    
    @EventHandler
    public void damage(EntityDamageEvent ev) {
        if(ev.getEntity() instanceof Player) {
            Card.runEventHooks(ev, (Player)ev.getEntity());
        }
    }
    
     @EventHandler
     public void onUseCard(UseCardEvent ev) {
         int uses = Card.getUsesLeft(ev.getItem());
         if(uses == -1)
             return;
         
         uses += 1;
         if(uses >= ev.getCard().getMaxUses()) {
             ev.getPlayer().setItemInHand(null);
         } else {
             Card.setUses(ev.getItem(), uses);
         }
     }
}
