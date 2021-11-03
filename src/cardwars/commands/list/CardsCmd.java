package cardwars.commands.list;

import cardwars.CardwarsPlugin;
import cardwars.cards.Card;
import cardwars.cards.CardRenderer;
import cardwars.commands.MainCmd;
import cardwars.commands.ExecutorType;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class CardsCmd extends MainCmd {

    public CardsCmd() {
        super("cartas", ExecutorType.OP);

        this.addSubcommand("ver", ExecutorType.OP, (p, args) -> {
            p.sendMessage(CardwarsPlugin.storage.getAllCards().stream()
                    .map(n -> n.getName()).collect(Collectors.joining(" | ")));
        });
        
        
        this.addSubcommand("teste", ExecutorType.OP, (p, args) -> {
             ItemStack map = new ItemStack(Material.MAP);
             MapMeta meta = (MapMeta)map.getItemMeta();
             MapView view = meta.getMapView();
             view.getRenderers().clear();
             view.getRenderers().add(new CardRenderer(CardwarsPlugin.storage.getCard("Barbaro")));
             map.setItemMeta(meta);
             p.getInventory().addItem(map);
        });

        this.addSubcommand("pegar", ExecutorType.OP, (p, args) -> {
            Card c = CardwarsPlugin.storage.getCard(String.join(" ", args));
            if (c != null) {
                p.getInventory().addItem(c.toItemStack());
            } else {
                p.sendMessage("Carta inexistente");
            }
        });
    }

}
