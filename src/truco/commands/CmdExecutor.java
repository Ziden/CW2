/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.commands;

import org.bukkit.entity.Player;

/**
 *
 * @author gabri
 */
public interface CmdExecutor {
    void execute(Player p, String [] args);
}

