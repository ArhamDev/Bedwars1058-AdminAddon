package me.zuyte.admin;

import com.andrei1058.bedwars.api.BedWars;
import me.zuyte.admin.commands.*;
import me.zuyte.admin.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static String version;
    public static BedWars bw;
    public Map<String, String> temp = new HashMap<String, String>();
    public Map<String, ItemStack> mlg = new HashMap<String, ItemStack>();
    public Map<String, String> kaboom = new HashMap<String, String>();
    public Map<String, BlockFace> beds = new HashMap<String, BlockFace>();
    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") != null) {
            instance = this;
            getLogger().info(ChatColor.translateAlternateColorCodes('&',"&fFound Bedwars1058 & hooked into."));
            setup();
        } else {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void setup() {
        version = getDescription().getVersion();
        bw = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        new admin(bw.getBedWarsCommand(), "admin");
        new forcejoin(bw.getBedWarsCommand(), "forcejoin");
        new setbed(bw.getBedWarsCommand(), "setbed");
        new skipevent(bw.getBedWarsCommand(), "skipevent");
        new nextevent(bw.getBedWarsCommand(), "nextevent");
        new troll(bw.getBedWarsCommand(), "troll");
        new revive(bw.getBedWarsCommand(), "revive");
        new setteam(bw.getBedWarsCommand(), "setteam");
        if (Bukkit.getPluginManager().getPlugin("BedWars1058-TeamSelector") == null) {
            getLogger().info(ChatColor.YELLOW + "Bedwars1058-TeamSelector Plugin was not found.");
        } else {
            getServer().getPluginManager().registerEvents(new Teamselect(), this);
            getLogger().info(ChatColor.translateAlternateColorCodes('&', "&fFound Bedwars1058-TeamSelector & hooked into."));
        }
        getServer().getPluginManager().registerEvents(new Assignevent(), this);
        getServer().getPluginManager().registerEvents(new Arenastart(), this);
        getServer().getPluginManager().registerEvents(new Arenaleave(), this);
        getServer().getPluginManager().registerEvents(new WBDeath(), this);
        getServer().getPluginManager().registerEvents(new WBPlace(), this);
        getServer().getPluginManager().registerEvents(new Damage(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new GUIListener() , this);
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&aRunning Bedwars1058-AdminAddon &fv" + getDescription().getVersion() + " &7- &eBy Zuyte"));
    }

    public static Main getInstance(){
        return instance;
    }
}