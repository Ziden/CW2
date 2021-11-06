package truco.cards;

import com.google.common.math.Quantiles.Scale;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.map.CraftMapCanvas;
import org.bukkit.craftbukkit.v1_17_R1.map.CraftMapRenderer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
import truco.Cardwars;

public class CardMapRenderer extends MapRenderer {

    private static HashMap<Card, CardMapRenderer> _renderers = new HashMap<Card, CardMapRenderer>();
    private Card card;
    private MapView bakedView;

    private CardMapRenderer(Card c) {
        card = c;
    }

    @Override
    public void render(MapView mv, MapCanvas mc, Player player) {
        if (bakedView != null) {
            return;
        }
        /*
        if(loadCardImageFromFile(mc)) {
            Cardwars.log.info("Lendo carta de " + player.getName());
            bakedView = mv;
            return;
        }
         */
        Cardwars.log.info("Renderizando carta " + card.getName());
        drawFrame(mc);
        drawBackground(mc);
        drawRarityIcon(mc);
        drawPowers(mc);
        drawTitle(mc);
        drawItems(mc);
        bakedView = mv;
        // saveCardImageToFile(mc);
    }

    public static CardMapRenderer getRenderer(Card c) {
        return new CardMapRenderer(c);
        /*
        CardMapRenderer r = _renderers.getOrDefault(c, null);
        if(r == null)
            r = new CardMapRenderer(c);
        return r;
         */
    }

    public static MapView GetView(Card c) {
        return getRenderer(c).getView();
    }

    public MapView getView() {
        return bakedView;
    }

    private void drawPowers(MapCanvas mc) {
        drawItem(6, 64 - 8, mc, Material.SMOOTH_STONE, 16); // left
        drawItem(128 - 16 - 6, 64 - 8, mc, Material.SMOOTH_STONE, 16); // right
        drawItem(64 - 8, 16, mc, Material.SMOOTH_STONE, 16); // top
        drawItem(64 - 8, 128 - 16 - 8, mc, Material.SMOOTH_STONE, 16); // bot

        mc.drawText(6+5, 64 - 8+5, MinecraftFont.Font, ""+card.getPowers()[1]);
        mc.drawText(128 - 16 - 6+5, 64 - 8+5, MinecraftFont.Font, ""+card.getPowers()[2]);
        mc.drawText(64 - 8+5, 16+5, MinecraftFont.Font, ""+card.getPowers()[0]);
        mc.drawText(64 - 8+5, 128 - 16 - 8+5, MinecraftFont.Font, ""+card.getPowers()[3]);
    }

    private void drawBackground(MapCanvas mc) {
        if (card.getImage().getBackground() == null) {
            return;
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                drawImage(x * 29 + 6, y * 26 + 16, mc, "item" + File.separator + card.getImage().getBackground().name().toLowerCase() + ".png", 29, 26,
                        card.getImage().getBackgroundColor()
                );
            }
        }
    }

    private void drawRarityIcon(MapCanvas mc) {
        if (card.getCardRarity() == CardRarity.Comum) {
            drawItem(63, 128 - 6, mc, Material.IRON_BLOCK, 5);
        } else if (card.getCardRarity() == CardRarity.Incomum) {
            drawItem(63, 128 - 6, mc, Material.EMERALD_BLOCK, 5);
        } else if (card.getCardRarity() == CardRarity.Rara) {
            drawItem(63, 128 - 6, mc, Material.LAPIS_BLOCK, 5);
        }
    }

    private void drawItems(MapCanvas mc) {
        if (card.getCardRarity() == CardRarity.Rara) {
            drawItem(32 - 25, 55, mc, card.getImage().getItem());
            drawItem(32 + 25, 55, mc, card.getImage().getItem());
        }
        drawItem(32, 32, mc, card.getImage().getItem());

    }

    private void drawTitle(MapCanvas mc) {
        mc.drawText(5, 5, MinecraftFont.Font, card.getName());
    }

    private void drawFrame(MapCanvas mc) {
        drawImage(0, 0, mc, "card.png", 128, 128, card.getImage().getColor());
    }

    private void drawItem(int x, int y, MapCanvas mc, Material m, int size) {

        drawImage(x, y, mc, "item" + File.separator + m.name().toLowerCase() + ".png", size, size, Color.WHITE);
    }

    private void drawItem(int x, int y, MapCanvas mc, Material m) {

        drawItem(x, y, mc, m, 64);
    }

    private void drawImage(int x, int y, MapCanvas mc, String imagePath, int scaleX, int scaleY, Color c) {
        BufferedImage img = null;
        try {
            File f = new File(Cardwars._i.getDataFolder(), File.separator + "textures" + File.separator + imagePath);
            img = ImageIO.read(f);
            img = scale(img, scaleX, scaleY, c);
            drawImageWithAlpha(x, y, mc, img);

            //mc.drawImage(x, y, i);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (img != null) {
                img.flush();
            }
        }
    }

    private void drawImageWithAlpha(int x, int y, MapCanvas mc, BufferedImage img) {
        for (int posX = 0; posX < img.getWidth(); posX++) {
            for (int posY = 0; posY < img.getHeight(); posY++) {
                Color color = new Color(img.getRGB(posX, posY), true);
                if (color.getAlpha() == 0) {
                    continue;
                }
                final byte mapColorByte = MapPalette.matchColor(color);
                mc.setPixel(x + posX, y + posY, mapColorByte);
            }
        }

    }

    private void saveCardImageToFile(MapCanvas canvas) {
        Field privateField;
        try {
            privateField = CraftMapCanvas.class.getDeclaredField("buffer");
            privateField.setAccessible(true);
            byte[] buffer = (byte[]) privateField.get(canvas);
            String fileName = card.getName().replaceAll("[\\\\/:*?\"<>|]", "") + ".bin";
            File dir = new File(Cardwars._i.getDataFolder(), File.separator + "cardimages");
            File f = new File(Cardwars._i.getDataFolder(), File.separator + "cardimages" + File.separator + fileName);
            if (!f.exists()) {
                dir.mkdirs();
                f.createNewFile();
            }
            Files.write(f.toPath(), buffer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean loadCardImageFromFile(MapCanvas canvas) {
        Field privateField;
        try {
            privateField = CraftMapCanvas.class.getDeclaredField("buffer");
            privateField.setAccessible(true);
            String fileName = card.getName().replaceAll("[\\\\/:*?\"<>|]", "") + ".bin";
            File f = new File(Cardwars._i.getDataFolder(), File.separator + "cardimages" + File.separator + fileName);
            if (!f.exists()) {
                return false;
            }
            byte[] buffer = Files.readAllBytes(f.toPath());
            privateField.set(f, card);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight, Color color) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }

}
