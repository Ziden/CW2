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

public class AlbumCmd extends MainCmd {

    public static final int PAG_SIZE = 20;

    public AlbumCmd() {
        super("album", ExecutorType.OP);

        this.addSubcommand("ver", ExecutorType.OP, (p, args) -> {
            CardAlbum album = Cardwars.getAlbum(p);
            if (args.length == 0) {
                p.sendMessage("§2Cartas: §a" + album.getCardIds().size() + "§2/§a" + Cardwars.getCardStorage().getAllCards().size());
                p.sendMessage("§2Seu album de cartas tem §a" + album.getCardIds().size() / PAG_SIZE + 1 + " §2paginas");
                p.sendMessage("§2Use §aver <pagina>§2 para visualizar a pagina");
                return;
            }
            mostraAlbum(p, args, album);
        });

        this.addSubcommand("colocar", ExecutorType.OP, (p, args) -> {
            Card c = Card.fromItemStack(p.getItemInHand());
            if (c == null) {
                p.sendMessage("§2Coloque uma carta na sua mao para adicionar no album");
                return;
            }
            p.setItemInHand(null);
            CardAlbum album = Cardwars.getAlbum(p);
            album.addCard(c);
            p.sendMessage("§2Carta adicionada no album. Agora voce tem "+album.getCardIds().size()+" cartas");
        });
        
        this.addSubcommand("pegar", ExecutorType.OP, (p, args) -> {
            if (args.length == 0) {
                p.sendMessage("§2Use pegar <nome do card>");
                return;
            }
            String nome = String.join(" ", args).toLowerCase();
            Card c = Cardwars.getCardStorage().getCard(nome);
            if(c == null) {
                p.sendMessage("§2Esta carta nao existe");
                return;
            }
            CardAlbum album = Cardwars.getAlbum(p);
            if(album.removeCard(c)) {
                p.getInventory().addItem(c.toItemStack(p));
                p.sendMessage("§2Voce pegou a carta "+c.getName());
            }
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
