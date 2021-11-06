/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.toscow;

import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author gabri
 */
public class ToscowStorage {
    
    private HashMap<UUID, ToscowGame> games = new HashMap<UUID, ToscowGame>();
    private HashMap<UUID, ToscowDeck> decks = new HashMap<UUID, ToscowDeck>();
    
    public void registerGame(ToscowGame game) {
        games.put(game.getID(), game);
    }
    
}
