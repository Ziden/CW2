package cardwars.cards;

import cardwars.CardwarsPlugin;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.function.Function;
import net.jodah.typetools.TypeResolver;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Card {

    private int level = 1;
    private String name;
    private Rarity cardRarity;
    private int art;
    private List<String> desc;
    private int uses = 1;
    private int cooldownSeconds = 0;

    public void setLevel(int level) {
        this.level = level;
    }
    private HashMap<Class, CardHook> eventHooks;

    public Card(String name, Rarity rarity) {
        this.name = name;
        this.cardRarity = rarity;
        desc = new ArrayList<String>();
        eventHooks = new HashMap<Class, CardHook>();
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

    public List<String> getDesc() {
        return desc;
    }

    public int getMaxUses() {
        return uses;
    }

    public void setMaxUses(int v) {
        uses = v;
    }
    
    public int getCooldownSeconds() {
        return cooldownSeconds;
    }
    
    public void setDescription(String... desc) {
        this.desc = Arrays.asList(desc);
    }
    
    public void addDescription(String desc) {
        this.desc.add(desc);
    }
    public void addHook(CardHook hook) {
        Class<?> typeArgs = TypeResolver.resolveRawArguments(CardHook.class, hook.getClass())[0];
        eventHooks.put(typeArgs, hook);
    }

    public boolean runHook(Event e) {
        CardHook hook = eventHooks.getOrDefault(e.getClass(), null);
        if (hook != null) {
            hook.execute(e);
            return true;
        }
        return false;
    }



    public static Card fromItemStack(ItemStack ss) {
        if(ss.getType() != Material.MAP)
            return null;
        
        ItemMeta meta = ss.getItemMeta();
        String name = meta.getDisplayName();
        if(name.contains(Rarity.getIcon())) {
            return CardwarsPlugin.storage.getCard(name.split("§a§l")[1]);
        }
        return null;
    }
    
    public static void runEventHooks(Event e, Player p) {
        for(ItemStack ss : p.getInventory().getContents()) {
            if(ss==null)
                continue;
            Card c = Card.fromItemStack(ss);
            if(c == null)
                continue;
            c.runHook(e);
        }
    }

    public static int getUses(ItemStack ss) {
        ItemMeta meta = ss.getItemMeta();
        String fourth = meta.getLore().get(4);
        if (fourth.contains("Usos")) {
            return Integer.valueOf(fourth.split(": ")[1]);
        }
        return 0;
    }

    public static void setUses(ItemStack ss, int uses) {
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(4, "§a| §2Usos: " + uses);
        meta.setLore(lore);
        ss.setItemMeta(meta);
    }
    
    public String getColoredName() {
        return this.cardRarity.getColoredIcon()+ " §a§l"+name;
    }
    
    public String getBracketName() {
          return "§a§l[ "+getColoredName()+" §a§l]";
    }

    public ItemStack toItemStack() {
        ItemStack ss = new ItemStack(Material.MAP);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName(getColoredName());
        List<String> lore = new ArrayList<String>();
        lore.add("§a|");
        lore.add("§a| §2Level: " + this.level);
        lore.add("§a| §2Raridade: " + this.cardRarity.color + this.cardRarity.name());
        lore.add("§a| ");
        if (this.eventHooks.containsKey(UseCardEvent.class)) {
            lore.add("§a| §2Usos: " + this.uses);
        }
        lore.add("§a|   ");
        for (String d : desc) {
            lore.add("§a| §2" + d);
        }
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }
}
