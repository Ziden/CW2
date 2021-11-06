/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.cards;

import truco.customevents.PutCardInItemEvent;
import truco.customevents.UseCardEvent;
import truco.cards.Card;
import truco.cards.CardRarity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
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

    public Card getBuildingCard() {
        return card;
    }

    public HashSet<Card> GetCards() {
        if (card != null) {
            built.add(card);
        }
        return built;
    }

    public CardBuilder createCard(String name, String... desc) {
        createCard(name, CardRarity.Comum, desc);
        return this;
    }

    public CardBuilder createCard(String name, CardRarity rarity, String... desc) {
        if (card != null) {
            built.add(card);
        }
        card = new Card();
        card.setName(name);
        card.setCardRarity(rarity);
        card.setDescription(desc);
        card.setId(UUID.randomUUID());
        return this;
    }

    public CardBuilder withBackground(Material m) {
        card.getImage().setBackground(m);
        return this;
    }

    public CardBuilder level(int l) {
        card.setLevel(l);
        return this;
    }

    public CardBuilder thatSendsMessage(String message) {
        card.addHook((CardHook<UseCardEvent>) e -> {
            e.getPlayer().sendMessage(message);
            return true;
        });
        return this;
    }

    public CardBuilder withImage(Material m) {
        card.getImage().setItem(m);
        return this;
    }

    public CardBuilder thatGivesItems(Material... m) {
        card.getImage().setItem(m[0]);
        card.addHook((CardHook<UseCardEvent>) e -> {
            for (Material mat : m) {
                e.getPlayer().getInventory().addItem(new ItemStack(mat));
                card.setDescription("Recebe 1 " + mat.name());
            }
            return true;
        });
        return this;
    }

    public CardBuilder dropsFromBlockMultiplied(Material m, int mult) {
        card.getImage().setItem(m);
        card.setDescription(mult + "x drops de " + m.name());
        card.addHook((CardHook<BlockBreakEvent>) e -> {
            if (e.getBlock().getType() == m) {
                for (ItemStack ss : e.getBlock().getDrops()) {
                    for (int x = 0; x < mult - 1; x++) {
                        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), ss.clone());
                    }
                }
            }
            return true;
        });
        return this;
    }

    public CardBuilder thatEnchants(Enchantment e, int level) {
        card.getImage().setItem(Material.ENCHANTING_TABLE);
        card.addDescription("Encanta " + e.getName() + " lvl " + level);
        card.addHook((CardHook<PutCardInItemEvent>) ev -> {
            ev.getItem().addUnsafeEnchantment(e, level);
            return true;
        });
        return this;
    }

    public CardBuilder thatGivesItems(Material mat, int qtd) {
        card.getImage().setItem(mat);
        card.setDescription("Recebe " + qtd + " " + mat.name());
        card.addHook((CardHook<UseCardEvent>) e -> {
            e.getPlayer().getInventory().addItem(new ItemStack(mat, qtd));
            return true;
        });
        return this;
    }

    public CardBuilder withUses(int uses) {
        this.card.setMaxUses(uses);
        return this;
    }

    public CardBuilder withHook(CardHook hook) {
        card.addHook(hook);
        return this;
    }

}
