package cardwars.cards;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class CardRenderer extends MapRenderer {

    private Card card;
    
    public CardRenderer(Card c) {
        card = c;
    }
    
    @Override
    public void render(MapView mv, MapCanvas mc, Player player) {
        try {
            File f = new File(getClass().getResource("minecraft/textures/item/apple.png").getFile());
            mc.drawImage(0, 0, ImageIO.read(f));  
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
    }

}
