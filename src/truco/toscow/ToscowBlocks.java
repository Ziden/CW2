/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.toscow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author gabri
 */
public class ToscowBlocks {

    Block root;
    Set<Block> panel;

    public ToscowBlocks(Location loc) {
        panel = new HashSet<Block>();
        root = loc.getBlock();
        for (int y = 1; y < 4; y++) {
            for (int x = -1; x <= 1; x++) {
                Block panelBlock = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ());
                panel.add(panelBlock);
            }
        }
    }

    public void build() {
        root.setType(Material.OAK_LOG);
        for (Block panelBlock : panel) {
            panelBlock.setType(Material.ACACIA_PLANKS);
            panelBlock.getRelative(BlockFace.NORTH).setType(Material.GLOW_ITEM_FRAME);
            panel.add(panelBlock);
        }
    }

    public void destroy() {
        root.setType(Material.AIR);
        for (Block panelBlock : panel) {
            panelBlock.setType(Material.AIR);
        }
    }

}
