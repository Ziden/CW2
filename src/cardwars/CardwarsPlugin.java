/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars;

import cardwars.cards.Card;
import cardwars.commands.Comando;
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

    public static CardStorage storage;
    private static CommandMap cmap;
    
    public void onEnable() {
        cmap = (CommandMap) ((CraftServer) Bukkit.getServer()).getCommandMap();
        addCommand(new CardsCmd());
        
        storage = new CardStorage(CardList.Generate());
        System.out.println("Loaded " + storage.getAllCards().size() + " cards");
    }
    
      public void addCommand(Comando cmd) {
        cmap.register(cmd.cmd, cmd);
        cmd.setExecutor(this);
    }
    
}
