package cardwars;

import cardwars.cards.Card;
import cardwars.cards.CardBuilder;
import cardwars.cards.CardHook;
import cardwars.cards.Rarity;
import java.util.HashSet;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class CardList {

    public static HashSet<Card> Generate() {
        return new CardBuilder()
                
                // LVL 1
                .createCard("Ferreiro", Rarity.Comum).thatGivesItems(Material.IRON_ORE, 64)
                .createCard("Minerador", Rarity.Incomum).thatGivesItems(Material.DIAMOND_PICKAXE, 1)
                .createCard("Lenhador", Rarity.Comum).thatGivesItems(Material.OAK_LOG, 64).withUses(3)
                .createCard("Bolas de Fogo", Rarity.Comum).thatGivesItems(Material.FIRE_CHARGE).withUses(20)
                .createCard("Pedras Preciosas Brutas", Rarity.Comum).thatGivesItems(Material.DIAMOND, 3)
                .createCard("Pedregulhos", Rarity.Comum).thatGivesItems(Material.BRICK, 64).withUses(2)
                .createCard("Catador de Latinha", Rarity.Comum).dropsFromBlockMultiplied(Material.IRON_ORE, 3)  

                .createCard("Barbaro", Rarity.Comum).thatGivesItems(Material.IRON_SWORD).withUses(3)
         
                
                .GetCards();
    }
}
