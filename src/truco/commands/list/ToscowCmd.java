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
import truco.album.Album;
import truco.album.TableGenerator;
import truco.album.TableGenerator.Alignment;
import truco.cards.CardRarity;
import truco.toscow.ToscowGame;

public class ToscowCmd extends MainCmd {

    public static final int PAG_SIZE = 20;

    public ToscowCmd() {
        super("toscow", ExecutorType.OP);

        this.addSubcommand("criar", ExecutorType.OP, (p, args) -> {
            ToscowGame game = new ToscowGame(p.getLocation());
            Cardwars.getToscowStorage().registerGame(game);
            
        });

    }

  

}
