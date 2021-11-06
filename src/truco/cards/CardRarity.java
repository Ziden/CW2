/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.cards;

/**
 *
 * @author gabri
 */
public enum CardRarity {

    Comum("§f"),
    Incomum("§a"),
    Rara("§9"),
    Epica("§d"),
    Lendaria("§e");

    public String color;

    private CardRarity(String cor) {
        this.color = cor;
    }

    public static String getIcon() {
        return "♦";
    }
    
    public String getColoredIcon() {
        return color + getIcon();
    }
}
