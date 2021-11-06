/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.cards;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Material;
import truco.Cardwars;
import truco.PluginConfig;

/**
 *
 * @author gabri
 */
public class CardSerializer {

    public static void SerializeCard(Card c, PluginConfig cfg) {
        cfg.set(c, "name", c.getName());
        cfg.set(c, "level", c.getLevel());
        cfg.set(c, "rarity", c.getCardRarity().name());
        cfg.set(c, "desc", c.getDesc());
        cfg.set(c, "uses", c.getMaxUses());
        cfg.set(c, "image.item", c.getImage().getItem().name());
        cfg.set(c, "image.background", c.getImage().getBackground().name());
    }

    public static HashSet<Card> DeserializeCards(PluginConfig cfg) {
        HashSet<Card> cards = new HashSet<Card>();
        for (String id : cfg.getKeys()) {
            Card c = new Card();
            c.setId(UUID.fromString(id));
            c.setName((String)cfg.get(c, "name"));
            Cardwars.log.info("Deserializing card "+c.getName());
            c.setLevel((int) cfg.get(c, "level"));
            c.setMaxUses((int) cfg.get(c, "uses"));
            c.setCardRarity(CardRarity.valueOf((String) cfg.get(c, "rarity")));
            List<String> desc = (List<String>)cfg.getList(c, "desc");
            c.setDescription(desc.toArray(new String[desc.size()]));
            c.getImage().setItem(Material.valueOf((String) cfg.get(c, "image.item")));
            c.getImage().setBackground(Material.valueOf((String) cfg.get(c, "image.background")));
            cards.add(c);
        }
        return cards;
    }

}
