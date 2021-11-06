/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import truco.cards.Card;

/**
 *
 * @author gabri
 */
public class PluginConfig {

    private static HashSet<String> _images = null;
    private FileConfiguration config;
    private File file;
    private JavaPlugin pl;
    private JsonObject lang;

    public PluginConfig(JavaPlugin p) {
        pl = p;
        this.file = new File(p.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.config.options().copyDefaults(true);
        saveConfig();
        readLanguageFile();
    }

    public void set(Card c, String s, Object v) {
        this.config.set(c.getId().toString() + "." + s, v);
    }

    public Object get(Card c, String s) {
        return this.config.get(c.getId().toString() + "." + s);
    }

    public Set<String> getKeys() {
        return this.config.getConfigurationSection("").getKeys(false);
    }

    public String getTranslatedName(Material m) {
        JsonElement e = lang.get("item.minecraft." + m.name().toLowerCase());
        if (e == null) {
            e = lang.get("block.minecraft." + m.name().toLowerCase());
        }
        if (e == null) {
            return m.name();
        }
        return e.getAsString();
    }

    public List<?> getList(Card c, String s) {
        return this.config.getList(c.getId().toString() + "." + s);
    }

    public void saveConfig() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.file = new File(pl.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData() {
        return config;
    }

    public void readLanguageFile() {
        try {
            List<String> lines = Files.readAllLines(new File(pl.getDataFolder(), "lang.json").toPath());
            lang = new JsonParser().parse(String.join("", lines)).getAsJsonObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static HashSet<String> getAllImageNames() {
        if (_images == null) {
            _images = new HashSet<String>();
            searchForImages(new File[]{Cardwars._i.getDataFolder()});
        }
        return _images;
    }

    public static void searchForImages(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                searchForImages(file.listFiles());
            } else {
                if (file.getName().contains(".png")) {
                    System.out.println("Detected image " + file.getName());
                    _images.add(file.getName().replace(".png", ""));
                }
            }
        }
    }
}
