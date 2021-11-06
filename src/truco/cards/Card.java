package truco.cards;

import truco.customevents.UseCardEvent;
import truco.Cardwars;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;
import net.jodah.typetools.TypeResolver;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Card {

    private int level = 1;
    private String name;
    private int[] powers = new int[]{0, 0, 0, 0};
    private UUID id;
    private CardRarity cardRarity;
    private List<String> desc;
    private int uses = 1;
    private int cooldownSeconds = 0;
    private CardImage image;
    private HashMap<Class, CardHook> eventHooks;

    public Card() {

        desc = new ArrayList<String>();
        eventHooks = new HashMap<Class, CardHook>();
        image = new CardImage();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public void setCardRarity(CardRarity cardRarity) {
        this.cardRarity = cardRarity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public CardRarity getCardRarity() {
        return cardRarity;
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

    public void setPower(BlockFace face, int p) {
        if (face == BlockFace.UP) {
            powers[0] = p;
        } else if (face == BlockFace.WEST) {
            powers[1] = p;
        } else if (face == BlockFace.EAST) {
            powers[2] = p;
        } else if (face == BlockFace.DOWN) {
            powers[3] = p;
        }
    }

    public void setPower(int i, int p) {
        powers[i] = p;
    }

    public int[] getPowers() {
        return powers;
    }

    public CardImage getImage() {
        return image;
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

    public static void runEventHooks(Event e, Player p) {
        for (ItemStack ss : p.getInventory().getContents()) {
            if (ss == null) {
                continue;
            }
            Card c = Card.fromItemStack(ss);
            if (c == null) {
                continue;
            }
            c.runHook(e);
        }
    }

    public String getColoredName() {
        return this.cardRarity.getColoredIcon() + " §a§l" + name;
    }

    public String getBracketName() {
        return "§a§l[ " + getColoredName() + " §a§l]";
    }

    public static int getUsesLeft(ItemStack ss) {
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
        lore.set(5, "§a| §2Usos: " + uses);
        meta.setLore(lore);
        ss.setItemMeta(meta);
    }

    public static Card fromItemStack(ItemStack ss) {
        if (ss == null || ss.getType() != Material.FILLED_MAP) {
            return null;
        }

        ItemMeta meta = ss.getItemMeta();
        String name = meta.getDisplayName();
        if (meta.getLore() == null || meta.getLore().size() < 2 || !meta.getLore().get(0).contains("Carta")) {
            return null;
        }

        if (name.contains(CardRarity.getIcon())) {
            return Cardwars.getCardStorage().getCard(name.split("§a§l")[1]);
        }
        return null;
    }

    public ItemStack toItemStack(Player p) {
        ItemStack ss = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) ss.getItemMeta();
        meta.setDisplayName(getColoredName());
        List<String> lore = new ArrayList<String>();
        lore.add("§a| §2Carta " + getName());
        lore.add("§a| §2Raridade: " + this.cardRarity.color + this.cardRarity.name());
        if (this.eventHooks.containsKey(UseCardEvent.class) && this.uses > 0) {
            lore.add("§a| §2Usos: " + this.uses);
        }
        lore.add("§a| §2  " + powers[0]);
        lore.add("§a| §2" + powers[1] + "   " + powers[2]);
        lore.add("§a| §2  " + powers[3]);
        for (String d : this.desc) {
            lore.add("§a| §2" + d);
        }
        meta.setLore(lore);

        MapView view = Bukkit.createMap(p.getWorld());
        for (MapRenderer mr : view.getRenderers()) {
            view.removeRenderer(mr);
        }
        view.addRenderer(CardMapRenderer.getRenderer(this));
        view.setUnlimitedTracking(false);
        view.setCenterZ(0);
        view.setCenterX(0);
        view.setScale(MapView.Scale.FARTHEST);
        view.setTrackingPosition(false);
        view.setLocked(true);
        meta.setMapView(view);

        if (cardRarity == CardRarity.Incomum) {
            meta.setColor(Color.GREEN);
        } else if (cardRarity == CardRarity.Rara) {
            meta.setColor(Color.BLUE);
        } else {
            meta.setColor(Color.WHITE);
        }
        meta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
        ss.setItemMeta(meta);
        return ss;
    }

}
