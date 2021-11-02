/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardwars;

import cardwars.cards.Rarity;
import cardwars.cards.CardBuilder;
import cardwars.cards.CardHook;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;


public class Test {
    
    
    public static void main(String [] args) {
        CardBuilder builder = new CardBuilder();
        

        CardHook hook2 = (CardHook<PlayerItemConsumeEvent>) e -> {
            System.out.println("WOLOLO");
        };
        
       // builder.createCard("wololo", Rarity.COMMON).withHook(hook2);
        
    }
}
