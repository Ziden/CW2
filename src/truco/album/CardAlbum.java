/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.album;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import truco.Cardwars;
import truco.cards.Card;

/**
 *
 * @author gabri
 */
public class CardAlbum {

    // inventory slot - card
    private HashMap<UUID, Integer> cards = new HashMap<UUID, Integer>() {
    };

    public void addCard(Card c) {
        int amt = cards.getOrDefault(c.getId(), 0);
        amt++;
        cards.put(c.getId(), amt);
    }

    public boolean hasCard(Card c) {
        return cards.containsKey(c.getId());
    }

    public boolean removeCard(Card c) {
        int amt = cards.getOrDefault(c.getId(), 0);
        if (amt == 0) {
            cards.remove(c.getId());
            return false;
        }
        amt--;
        if (amt <= 0) {
            cards.remove(c.getId());
        } else {
            cards.put(c.getId(), amt);
        }
        return true;
    }

    public Set<UUID> getCardIds() {
        return cards.keySet();
    }

    public static ItemStack createItemStack(Player p) {
        ItemStack ss = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName("§a§lAlbum de Cartas");
        List<String> lore = new ArrayList<String>();
        lore.add("§2Pertence a: §a" + p.getName());
        lore.add(" ");
        lore.add("§2§lClique para ver o album");
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }

}
