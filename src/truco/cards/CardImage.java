/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;

/**
 *
 * @author gabri
 */
public class CardImage {
    
    private Color BackgroundColor = new Color(155, 25, 122, 255);
    private Color Color = new Color(123,25 ,123, 255);
    private Material Background = null;
    private Material Item = null;

    public Color getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(Color BackgroundColor) {
        this.BackgroundColor = BackgroundColor;
    }
    
    public Material getItem() {
        return Item;
    }

    public void setItem(Material Item) {
        this.Item = Item;
    }

    public Color getColor() {
        return Color;
    }

    public void setColor(Color Color) {
        this.Color = Color;
    }

    public Material getBackground() {
        return Background;
    }

    public void setBackground(Material Background) {
        this.Background = Background;
    }
}
