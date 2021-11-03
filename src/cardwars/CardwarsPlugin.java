/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars;

import cardwars.cards.Card;
import cardwars.commands.MainCmd;
import cardwars.commands.list.CardsCmd;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author gabri
 */
public class CardwarsPlugin extends JavaPlugin {

    public static JavaPlugin _i;
    public static CardStorage storage;
    private static CommandMap cmap;
    
    public void onEnable() {
        _i = this;
        cmap = (CommandMap) ((CraftServer) Bukkit.getServer()).getCommandMap();  
        addCommand(new CardsCmd());
        Bukkit.getServer().getPluginManager().registerEvents(new CardListener(), this);
        
        // TODO: Move the cardlist to the minigame plugin
        storage = new CardStorage(CardList.Generate());
        System.out.println("Loaded " + storage.getAllCards().size() + " cards");
    }
    
      public void addCommand(MainCmd cmd) {
        cmap.register(cmd.getName(), cmd);
        cmd.setExecutor(this);
    }
    
}
