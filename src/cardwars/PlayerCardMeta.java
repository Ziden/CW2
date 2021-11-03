/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars;

import cardwars.cards.Card;
import java.util.HashMap;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;


public class PlayerCardMeta {

    public static PlayerCardMeta get(Metadatable e) {
        if (!e.hasMetadata("c")) {
            return null;
        }
        return (PlayerCardMeta) e.getMetadata("c").get(0).value();
    }

    public void save(Metadatable e) {
        if (e.hasMetadata("c")) {
            e.removeMetadata("c", CardwarsPlugin._i);
        }
        e.setMetadata("c", new FixedMetadataValue(CardwarsPlugin._i, this));
    }

}
