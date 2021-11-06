package truco.commands.list;

import java.awt.TextComponent;
import java.util.ArrayList;
import truco.Cardwars;
import truco.cards.Card;
import truco.cards.CardMapRenderer;
import truco.commands.MainCmd;
import truco.commands.ExecutorType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import net.md_5.bungee.api.chat.ClickEvent;
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
import truco.album.TableGenerator;
import truco.album.TableGenerator.Alignment;
import truco.cards.CardRarity;

public class ToscoCmd extends MainCmd {

    public static final int PAG_SIZE = 20;

    public ToscoCmd() {
        super("toscow", ExecutorType.OP);

        this.addSubcommand("criar", ExecutorType.OP, (p, args) -> {
            
        });

    }

    private void mostraAlbum(Player p, String[] args, CardAlbum album) {

        TableGenerator tg = new TableGenerator(Alignment.LEFT, Alignment.LEFT);
        int pagina = 0;
        pagina = Integer.valueOf(args[0]);

        List<UUID> cards = new ArrayList<UUID>(album.getCardIds());
        int pags = cards.size() / PAG_SIZE + 1;
        if (cards.size() > PAG_SIZE) {
            int from = pagina * PAG_SIZE;
            int to = pagina * PAG_SIZE + PAG_SIZE;
            if (cards.size() < from) {
                return;
            }
            if (cards.size() < to) {
                to = cards.size() - 1;
            }
            cards = cards.subList(from, to);
        }
        for (UUID u : cards) {
            Card c = Cardwars.getCardStorage().getCard(u);
            tg.addRow(c.getColoredName());
        }

        for (String s : tg.generate()) {
            p.sendMessage(s);
        }
    }

}
