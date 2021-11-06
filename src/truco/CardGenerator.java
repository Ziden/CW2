package truco;

import java.util.ArrayList;
import java.util.HashMap;
import truco.cards.Card;
import truco.cards.CardBuilder;
import truco.cards.CardHook;
import truco.cards.CardRarity;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import truco.cards.CardSerializer;

public class CardGenerator {

    public static Material[] BGS_COMMON = new Material[]{
        Material.DIRT, 
        Material.STONE,
        Material.WHITE_WOOL,
        Material.OAK_LOG,
        Material.OAK_PLANKS,
        Material.STONE_BRICKS, 
        Material.COBBLESTONE
    };
    
    public static String [] SUFIXOS = new String [] {
         "Terrada", 
        "Pedrada",
        "Lanzada",
        "Torada",
        "Tabuada",
        "Tijolada", 
        "Rochada"
    };

    public static HashSet<Card> GenerateFromMaterials(PluginConfig cfg) {
        CardBuilder builder = new CardBuilder();
        HashSet<String> images = PluginConfig.getAllImageNames();
        List<Material> icons = new ArrayList<Material>();
        List<Material> backgrounds = new ArrayList<Material>();
        HashMap<String, Boolean> names = new HashMap<String, Boolean>();

        for (Material m : Material.values()) {
            if (images.contains(m.name().toLowerCase())) {
                if (!m.isSolid()) {
                    icons.add(m);
                } else {
                    backgrounds.add(m);
                }
            }
        }

        // Commons, limited backgrounds
        for (Material m : icons) {
            for (int bgi = 0; bgi < BGS_COMMON.length; bgi++) {
                Material bg = BGS_COMMON[bgi];
                String name = cfg.getTranslatedName(m) + " "+SUFIXOS[bgi];
                if (names.containsKey(name)) {
                    name = m.name()+" "+bgi;
                }

                builder.createCard(name, "Teste")
                        .withImage(m)
                        .withBackground(bg);

                CardSerializer.SerializeCard(builder.getBuildingCard(), cfg);
                names.put(name, true);
                System.out.println("Gerada carta comum " + name);
            }
        }

        // Uncommons 
        for (Material m : icons) {
            String name = cfg.getTranslatedName(m) + " Especial";
            if (names.containsKey(name)) {
                name = m.name() + " Especial";
            }
            if (names.containsKey(name)) {
                continue;
            }

            Material bg = backgrounds
                    .stream()
                    .skip(ThreadLocalRandom.current()
                            .nextInt(backgrounds.size()))
                    .findAny().get();

            builder.createCard(name, CardRarity.Incomum, "Teste")
                    .withImage(m)
                    .withBackground(bg);
            names.put(name, true);
            CardSerializer.SerializeCard(builder.getBuildingCard(), cfg);
            System.out.println("Gerada carta incomum " + name);
        }

        // Rares
        for (Material m : icons) {
            String name = cfg.getTranslatedName(m) + " Fenomenal";
            if (names.containsKey(name)) {
                name = m.name() + " Fenomenal";
            }
            if (names.containsKey(name)) {
                continue;
            }

            Material bg = backgrounds
                    .stream()
                    .skip(ThreadLocalRandom.current()
                            .nextInt(backgrounds.size()))
                    .findAny().get();

            builder.createCard(name, CardRarity.Rara, "Teste")
                    .withImage(m)
                    .withBackground(bg);
            names.put(name, true);
            CardSerializer.SerializeCard(builder.getBuildingCard(), cfg);
            System.out.println("Gerada carta rara " + name);
        }

        return builder.GetCards();
    }

    public CardRarity getRarity() {
        return null;
    }

    // Como um outro plugin geraria as cartas
    public static HashSet<Card> Generate() {
        return new CardBuilder()
                .createCard("Ferreiro", CardRarity.Comum).thatGivesItems(Material.IRON_ORE, 64)
                .createCard("Minerador", CardRarity.Incomum).thatGivesItems(Material.DIAMOND_PICKAXE, 1)
                .createCard("Lenhador", CardRarity.Comum).thatGivesItems(Material.OAK_LOG, 64).withUses(3)
                .createCard("Bolas de Fogo", CardRarity.Comum).thatGivesItems(Material.FIRE_CHARGE).withUses(20)
                .createCard("Pedras Preciosas Brutas", CardRarity.Comum).thatGivesItems(Material.DIAMOND, 3)
                .createCard("Pedregulhos", CardRarity.Comum).thatGivesItems(Material.BRICK, 64).withUses(2)
                .createCard("Catador de Latinha", CardRarity.Comum).dropsFromBlockMultiplied(Material.IRON_ORE, 3)
                .createCard("Barbaro", CardRarity.Comum).thatGivesItems(Material.IRON_SWORD).withUses(3)
                .GetCards();
    }
}
