/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.album;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 *
 * @author gabri
 */
public class AlbumStorage {

    public HashMap<UUID, Album> _albums = new HashMap<UUID, Album>();

    public Album getAlbum(Player p) {
        if(!_albums.containsKey(p.getUniqueId()))
            _albums.put(p.getUniqueId(), new Album());
        return _albums.get(p.getUniqueId());
    }
    
    public void saveAlbum(Player p, Album b) {
         _albums.put(p.getUniqueId(), b);
    }
}
