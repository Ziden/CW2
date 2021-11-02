package cardwars.cards;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import net.jodah.typetools.TypeResolver;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Card {

    private int id;
    private String name;
    private Rarity cardRarity;
    private int art;
    private String[] desc;
    private int uses = 1;
    private int cooldownSeconds = 0;
    private HashMap<Class, CardHook> eventHooks;

    public Card(String name, Rarity rarity) {
        this.id = name.hashCode();
        this.name = name;
        this.cardRarity = rarity;
        eventHooks = new HashMap<Class, CardHook>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rarity getCardRarity() {
        return cardRarity;
    }

    public int getArt() {
        return art;
    }

    public String[] getDesc() {
        return desc;
    }

    public int getUses() {
        return uses;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public void AddHook(CardHook hook) {
        Class<?> typeArgs = TypeResolver.resolveRawArguments(CardHook.class, hook.getClass())[0];
        eventHooks.put(typeArgs, hook);
    }

    public void setDescription(String... desc) {
        this.desc = desc;
    }

    public ItemStack getItemStack() {
        ItemStack ss = new ItemStack(Material.MAP);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add("Raridade: "+this.cardRarity.name());
        lore.add("");
        lore.addAll(Arrays.asList(desc));
        meta.setLore(Arrays.asList(desc));
        ss.setItemMeta(meta);
        return ss;
    }
}
