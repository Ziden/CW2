/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars.cards;

import org.bukkit.event.Event;

/**
 *
 * @author gabri
 */
public interface CardHook<E extends Event> {
    void execute(E e);
}
