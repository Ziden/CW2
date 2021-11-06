package truco;

import truco.cards.CardListener;
import truco.cards.CardStorage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import truco.cards.Card;
import truco.commands.MainCmd;
import truco.commands.list.CardsCmd;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import truco.album.AlbumStorage;
import truco.album.Album;
import truco.cards.CardSerializer;
import truco.commands.list.AlbumCmd;
import truco.commands.list.ToscowCmd;
import truco.toscow.ToscowStorage;

public class Cardwars extends JavaPlugin {

    public static Logger log;
    private static JavaPlugin _i;
    
    private static ToscowStorage games;
    private static CardStorage cards;
    private static AlbumStorage albums;
    
    private static CommandMap cmap;
    private static PluginConfig config;

    public void onEnable() {
        _i = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        config = new PluginConfig(this);
        log = getLogger();;
        cmap = (CommandMap) ((CraftServer) Bukkit.getServer()).getCommandMap();
        addCommand(new CardsCmd());
        addCommand(new AlbumCmd());
        addCommand(new ToscowCmd());
        Bukkit.getServer().getPluginManager().registerEvents(new CardListener(), this);

        cards = new CardStorage();
        albums = new AlbumStorage();
        games = new ToscowStorage();
        HashSet<Card> savedCards = CardSerializer.DeserializeCards(config);
        if (savedCards.size() == 0) {
            savedCards = CardGenerator.GenerateFromMaterials(config);
            config.saveConfig();
        }
        cards.setCards(savedCards);
        log.info("Loaded " + cards.getAllCards().size() + " cards");
    }
    
    public static CardStorage getCardStorage() {
        return cards;
    }
    
    public static Album getAlbum(Player p) {
        return albums.getAlbum(p);
    }
    
    public static ToscowStorage getToscowStorage() {
        return games;
    }
    
    public static File getPluginFolder() {
        return _i.getDataFolder();
    }

    public void addCommand(MainCmd cmd) {
        cmap.register(cmd.getName(), cmd);
        cmd.setExecutor(this);
    }

}
