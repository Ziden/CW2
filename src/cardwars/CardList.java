/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
                .createCard("Barbaro", Rarity.COMMON).thatGivesItem(Material.IRON_SWORD)
                .createCard("Bolas de Fogo", Rarity.COMMON).thatGivesItem(Material.FIRE_CHARGE)
                .GetCards();
    }
}
