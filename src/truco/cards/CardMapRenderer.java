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

    // map size
    private static final int CARD_SIZE_X = 128;
    private static final int CARD_SIZE_Y = 128;

    private static final int MID_X = CARD_SIZE_X / 2;
    private static final int MID_Y = CARD_SIZE_Y / 2;

    private static final int TOP_BORDER_SIZE = 10;
    private static final int BOT_BORDER_SIZE = 10;
    private static final int SIDE_BORDER_SIZE = 10;

    private static final int CENTER_SIZE_X = CARD_SIZE_X - (SIDE_BORDER_SIZE * 2);
    private static final int CENTER_SIZE_Y = CARD_SIZE_Y - TOP_BORDER_SIZE - BOT_BORDER_SIZE;
    private static final int BG_REPETITION = 4;

    private static final int BG_SIZE_X = CENTER_SIZE_X / BG_REPETITION;
    private static final int BG_SIZE_Y = CENTER_SIZE_Y / BG_REPETITION;

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

        if (loadCardImageFromFile(mc)) {
            Cardwars.log.info("Lendo carta de " + player.getName());
            bakedView = mv;
            return;
        }

        Cardwars.log.info("Renderizando carta " + card.getName());
        drawFrame(mc);
        drawBackground(mc);
        drawRarityIcons(mc);
        drawPowers(mc);
        drawItems(mc);
        bakedView = mv;
        saveCardImageToFile(mc);
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

    //116 - 110
    public static MapView GetView(Card c) {
        return getRenderer(c).getView();
    }

    public MapView getView() {
        return bakedView;
    }

    private void drawBackground(MapCanvas mc) {
        if (card.getImage().getBackground() == null) {
            return;
        }
        for (int x = 0; x < BG_REPETITION; x++) {
            for (int y = 0; y < BG_REPETITION; y++) {
                int xpos = x * BG_SIZE_X + SIDE_BORDER_SIZE;
                int ypos = y * BG_SIZE_Y + TOP_BORDER_SIZE;
                drawImage(xpos, ypos, mc, "item" + File.separator + card.getImage().getBackground().name().toLowerCase() + ".png", BG_SIZE_X, BG_SIZE_Y,
                        card.getImage().getBackgroundColor()
                );
            }
        }
    }

    private void drawRarityBorders(MapCanvas mc, Material m) {
        drawItem(1, 1, mc, m, 8);
        drawItem(CARD_SIZE_X - 1 - 8, CARD_SIZE_Y - 1 - 8, mc, m, 8);
        drawItem(CARD_SIZE_X - 1 - 8, 1, mc, m, 8);
        drawItem(1, CARD_SIZE_Y - 1 - 8, mc, m, 8);
    }

    private void drawRarityIcons(MapCanvas mc) {
        if (card.getCardRarity() == CardRarity.Comum) {
            drawRarityBorders(mc, Material.SMOOTH_STONE);
        } else if (card.getCardRarity() == CardRarity.Incomum) {
            drawRarityBorders(mc, Material.EMERALD_BLOCK);
        } else if (card.getCardRarity() == CardRarity.Rara) {
            drawRarityBorders(mc, Material.LAPIS_BLOCK);
        }
    }

    private void drawItems(MapCanvas mc) {
        if (card.getCardRarity() == CardRarity.Rara) {
            drawItem(MID_X - 32 - 20, MID_Y - 32 + 20, mc, card.getImage().getItem());
            drawItem(MID_X - 32 + 20, MID_Y - 32 + 20, mc, card.getImage().getItem());
        }
        drawItem(MID_X - 32, MID_Y - 32, mc, card.getImage().getItem(), 64);
    }

    private void drawPowers(MapCanvas mc) {
        if (card.getPowers()[0] > 0) {
            drawItem(MID_X - 8, TOP_BORDER_SIZE, mc, Material.SMOOTH_STONE, 16); // top
        }
        if (card.getPowers()[1] > 0) {
            drawItem(SIDE_BORDER_SIZE, MID_Y - 8, mc, Material.SMOOTH_STONE, 16); // left
        }
        if (card.getPowers()[2] > 0) {
            drawItem(CARD_SIZE_X - SIDE_BORDER_SIZE - 16, MID_Y - 8, mc, Material.SMOOTH_STONE, 16); // right
        }
        if (card.getPowers()[3] > 0) {
            drawItem(MID_X - 8, CARD_SIZE_X - 16 - BOT_BORDER_SIZE, mc, Material.SMOOTH_STONE, 16); // bot
        }
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
            File f = new File(Cardwars.getPluginFolder(), File.separator + "textures" + File.separator + imagePath);
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
            String fileName = card.getName().replaceAll("[\\\\/:*?\"<>|]", "") + ".card";
            File dir = new File(Cardwars.getPluginFolder(), File.separator + "cardimages");
            File f = new File(Cardwars.getPluginFolder(), File.separator + "cardimages" + File.separator + fileName);
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
            String fileName = card.getName().replaceAll("[\\\\/:*?\"<>|]", "") + ".card";
            File f = new File(Cardwars.getPluginFolder(), File.separator + "cardimages" + File.separator + fileName);
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
