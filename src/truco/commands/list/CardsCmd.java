package truco.commands.list;

import truco.Cardwars;
import truco.cards.Card;
import truco.cards.CardMapRenderer;
import truco.commands.MainCmd;
import truco.commands.ExecutorType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;
import truco.CardGenerator;
import truco.album.CardAlbum;
import truco.cards.CardRarity;

public class CardsCmd extends MainCmd {

    public CardsCmd() {
        super("card", ExecutorType.OP);

        this.addSubcommand("ver", ExecutorType.OP, (p, args) -> {
            int pagina = 0;
            if (args.length > 0) {
                pagina = Integer.valueOf(args[0]);
            }
            String s = Cardwars.getCardStorage().getAllCards().stream()
                    .map(n -> n.getColoredName()).collect(Collectors.joining("§1 | "));
            int pagSize = 900;
            int pags = s.length()/pagSize+1;
            if(s.length() > pagSize) {
                int from = pagina * pagSize;
                int to = pagina * pagSize + pagSize;
                if(s.length() < from) {
                    return;
                }
                if(s.length() < to) {
                    to = s.length()-1;
                }
                s = s.substring(from, to); 
            }
            p.sendMessage(s);
            p.sendMessage("§2Pagina §a"+pagina+"§2 de "+pags+" - Use ver <pagina>");
        });

        this.addSubcommand("pegarandom", ExecutorType.OP, (p, args) -> {
            Collection<Card> cards = Cardwars.getCardStorage().getAllCards();
            Card random = cards.stream()
                    .skip(ThreadLocalRandom.current().nextInt(cards.size()))
                    .findAny().get();
            p.getInventory().addItem(random.toItemStack(p));
        });

        this.addSubcommand("album", ExecutorType.OP, (p, args) -> {
            p.getInventory().addItem(CardAlbum.createItemStack(p));
        });

        this.addSubcommand("pegar", ExecutorType.OP, (p, args) -> {
            Card c = Cardwars.getCardStorage().getCard(String.join(" ", args));
            if (c != null) {
                p.getInventory().addItem(c.toItemStack(p));
            } else {
                p.sendMessage("Carta inexistente");
            }
        });

    }
}
