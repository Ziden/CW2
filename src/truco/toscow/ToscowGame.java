/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.toscow;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import truco.cards.Card;

/**
 *
 * @author gabri
 */
public class ToscowGame {
    
    private Player [] players = new Player[2];
    private UUID id;
    private ToscowBlocks blocks;
    private Card [][] Cards = new Card[5][5];
    
    public ToscowGame(Location l) {
        id = UUID.randomUUID();
        blocks = new ToscowBlocks(l);
        blocks.build();
    }
    
    public void setPlayers(Player ... players) {
        players = players;
    }
    
    public UUID getID() {
        return id;
    }
    
    public ToscowBlocks getBlocks() {
        return blocks;
    }
    
}
