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
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author gabri
 */
public class ToscowBlocks {

    private Block root;
    private Set<Block> all;
    private Set<Block> panel;
    private Set<Block> border;
    private Set<Block> playerroom;
    private Set<Block> fence;
    private Set<Block> frames;

    public Location getPlayerLocation() {
        return root.getRelative(BlockFace.SOUTH).getLocation();
    }
    
    public ToscowBlocks(Location loc) {
        panel = new HashSet<Block>();
        border = new HashSet<Block>();
        playerroom = new HashSet<Block>();
        fence = new HashSet<Block>();
        frames = new HashSet<Block>();
        root = loc.getBlock();
        for (int y = 0; y < 5; y++) {
            for (int x = -2; x <= 2; x++) {
                Block panelBlock = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ());
                if (x == -2 || x == 2 || y == 0 || y == 4) {
                    border.add(panelBlock);
                } else {
                    panel.add(panelBlock);
                }
                frames.add(panelBlock.getRelative(BlockFace.SOUTH));
            }
        }
        for (int z = 1; z < 4; z++) {
            for (int x = -3; x <= 3; x++) {
                Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY(), loc.getBlockZ() + z);
                playerroom.add(b);
                playerroom.add(b.getRelative(BlockFace.UP));
                if (z == 0 || z == 3 || x == -3 || x == 3) {
                    fence.add(b);
                }
            }
        }
        all = new HashSet<Block>();
        all.addAll(panel);
        all.addAll(fence);
        all.addAll(border);
        all.addAll(playerroom);
        all.addAll(frames);
    }

    public boolean canBuild() {
        for (Block b : all) {
            if (b.getType().isSolid()) {
                return false;
            }
        }
        return true;
    }

    public void build() {
        for (Block b : panel) {
            b.setType(Material.ACACIA_PLANKS);
        }
        for (Block b : border) {
            b.setType(Material.BEDROCK);
        }
        for (Block b : fence) {
            b.setType(Material.OAK_FENCE);
        }
        for (Block b : frames) {
            ItemFrame frame = root.getWorld().spawn(b.getLocation(), ItemFrame.class);    
        }
    }

    public void destroy() {
        root.setType(Material.AIR);
        for (Block panelBlock : panel) {
            panelBlock.setType(Material.AIR);
        }
    }

}
