package cardwars;

import cardwars.cards.Card;
import cardwars.cards.PutCardInItemEvent;
import cardwars.cards.UseCardEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        
        // se a carta tiver hook de onUse ela eh uma carta ativa
        UseCardEvent useEv = new UseCardEvent(ev.getPlayer(), c, ev.getItem());
        if(c.runHook(useEv)) {
             Bukkit.getServer().getPluginManager().callEvent(useEv);
        }
    }
    
    @EventHandler
    public void onMapInitialize(MapInitializeEvent e) {
        MapView mapView = e.getMap();
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
    
    // exemplo de listener
    @EventHandler
    public void damage(EntityDamageEvent ev) {
        if(ev.getEntity() instanceof Player) {
            Card.runEventHooks(ev, (Player)ev.getEntity());
        }
    }
    
     @EventHandler
     public void onUseCard(UseCardEvent ev) {
         ev.getPlayer().sendMessage("§aUsou a carta "+ev.getCard().getBracketName());
         int uses = Card.getUses(ev.getItem());
         uses += 1;
         if(uses >= ev.getCard().getMaxUses()) {
             ev.getPlayer().setItemInHand(null);
         } else {
             Card.setUses(ev.getItem(), uses);
         }
     }
}
