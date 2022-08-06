package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.Main;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class troll extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();

    public troll(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.troll");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.troll")) return false;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("mlg")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        Location playerLoc = player.getLocation();
                        playerLoc.setY(playerLoc.getY()+50);
                        playerLoc.setPitch(90);
                        playerLoc.setYaw(-90);
                        player.teleport(playerLoc);
                        Main.getInstance().mlg.put(player.getName(), player.getItemInHand());
                        player.setItemInHand(new ItemStack(Material.WATER_BUCKET));
                        player.sendMessage(ChatColor.AQUA + "MLG TIME!!");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("cage")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        IArena playerArena = arenaUtil.getArenaByPlayer(player);
                        int x = player.getLocation().getBlockX();
                        int y = player.getLocation().getBlockY();
                        int z = player.getLocation().getBlockZ();
                        if (player.getWorld().getBlockAt(x,y+2,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y+2, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y+2, z));
                        }
                        if (player.getWorld().getBlockAt(x,y-1,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y-1, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y-1, z));
                        }
                        if (player.getWorld().getBlockAt(x,y,z+1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y, z+1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y, z+1));
                        }
                        if (player.getWorld().getBlockAt(x,y,z-1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y, z-1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x,y,z-1));
                        }
                        if (player.getWorld().getBlockAt(x+1,y,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x+1, y, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x+1, y, z));
                        }
                        if (player.getWorld().getBlockAt(x-1,y,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x-1, y, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x-1, y, z));
                        }
                        // above layer
                        if (player.getWorld().getBlockAt(x, y+1, z+1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y+1, z+1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y+1, z+1));
                        }
                        if (player.getWorld().getBlockAt(x, y+1, z-1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y+1, z-1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y+1, z-1));
                        }
                        if (player.getWorld().getBlockAt(x+1, y+1, z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x+1, y+1, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x+1, y+1, z));
                        }
                        if (player.getWorld().getBlockAt(x-1, y+1, z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x-1, y+1, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x-1, y+1, z));
                        }
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Trapped " + args[1]);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("blind")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        if (args.length >= 3) {
                            try {
                                Integer.parseInt(args[2]);
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(ChatColor.RED + "Please enter a number.");
                                return true;
                            }
                            int duration = Integer.parseInt(args[2]);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration*20, 1));
                            p.sendMessage(ChatColor.RED + args[1] + " is blinded for " + args[2] + " seconds");
                            return true;
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("slowhands")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        if (args.length >= 3) {
                            try {
                                Integer.parseInt(args[2]);
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(ChatColor.RED + "Please enter a number.");
                                return true;
                            }
                            int duration = Integer.parseInt(args[2]);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration*20, 1));
                            p.sendMessage(ChatColor.RED + args[1] + " has slowhands for " + args[2] + " seconds");
                            return true;
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("kaboom")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        Main.getInstance().kaboom.put(player.getDisplayName(), "true");
                        Location playerLocation = player.getLocation();
                        player.setVelocity(new Vector(0, 35, 0));
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        // 1st
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        // 2nd
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("toystick")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        ItemStack toyStick = new ItemStack(Material.STICK, 1);
                        toyStick.addUnsafeEnchantment(Enchantment.LURE, 1);
                        ItemMeta meta = toyStick.getItemMeta();
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bMagic Toy Stick"));
                        List<String> loreList = new ArrayList<String>();
                        loreList.add(ChatColor.translateAlternateColorCodes('&', "&eClick me for an explosion!"));
                        meta.setLore(loreList);
                        toyStick.setItemMeta(meta);
                        player.getInventory().addItem(toyStick);
                        p.sendMessage(ChatColor.GREEN + "Gave " + args[1] + " a toystick!");
                        return true;
                    }
                }
                p.sendMessage(ChatColor.RED + "Invalid Usage.");
                return true;
            }
        }
        if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender p = (ConsoleCommandSender) commandSender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("mlg")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        Location playerLoc = player.getLocation();
                        playerLoc.setY(playerLoc.getY()+50);
                        playerLoc.setPitch(90);
                        playerLoc.setYaw(-90);
                        player.teleport(playerLoc);
                        Main.getInstance().mlg.put(player.getName(), player.getItemInHand());
                        player.setItemInHand(new ItemStack(Material.WATER_BUCKET));
                        player.sendMessage(ChatColor.AQUA + "MLG TIME!!");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("cage")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        IArena playerArena = arenaUtil.getArenaByPlayer(player);
                        int x = player.getLocation().getBlockX();
                        int y = player.getLocation().getBlockY();
                        int z = player.getLocation().getBlockZ();
                        if (player.getWorld().getBlockAt(x,y+2,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y+2, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y+2, z));
                        }
                        if (player.getWorld().getBlockAt(x,y-1,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y-1, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y-1, z));
                        }
                        if (player.getWorld().getBlockAt(x,y,z+1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y, z+1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y, z+1));
                        }
                        if (player.getWorld().getBlockAt(x,y,z-1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y, z-1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x,y,z-1));
                        }
                        if (player.getWorld().getBlockAt(x+1,y,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x+1, y, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x+1, y, z));
                        }
                        if (player.getWorld().getBlockAt(x-1,y,z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x-1, y, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x-1, y, z));
                        }
                        // above layer
                        if (player.getWorld().getBlockAt(x, y+1, z+1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y+1, z+1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y+1, z+1));
                        }
                        if (player.getWorld().getBlockAt(x, y+1, z-1).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x, y+1, z-1).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x, y+1, z-1));
                        }
                        if (player.getWorld().getBlockAt(x+1, y+1, z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x+1, y+1, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x+1, y+1, z));
                        }
                        if (player.getWorld().getBlockAt(x-1, y+1, z).getType() == Material.AIR) {
                            player.getWorld().getBlockAt(x-1, y+1, z).setType(Material.GLASS);
                            playerArena.addPlacedBlock(player.getWorld().getBlockAt(x-1, y+1, z));
                        }
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Trapped " + args[1]);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("blind")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        if (args.length >= 3) {
                            try {
                                Integer.parseInt(args[2]);
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(ChatColor.RED + "Please enter a number.");
                                return true;
                            }
                            int duration = Integer.parseInt(args[2]);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration*20, 1));
                            p.sendMessage(ChatColor.RED + args[1] + " is blinded for " + args[2] + " seconds");
                            return true;
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("slowhands")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        if (args.length >= 3) {
                            try {
                                Integer.parseInt(args[2]);
                            } catch (NumberFormatException nfe) {
                                p.sendMessage(ChatColor.RED + "Please enter a number.");
                                return true;
                            }
                            int duration = Integer.parseInt(args[2]);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration*20, 1));
                            p.sendMessage(ChatColor.RED + args[1] + " has slowhands for " + args[2] + " seconds");
                            return true;
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("kaboom")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        Main.getInstance().kaboom.put(player.getDisplayName(), "true");
                        Location playerLocation = player.getLocation();
                        player.setVelocity(new Vector(0, 35, 0));
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        // 1st
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        // 2nd
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        player.getWorld().strikeLightningEffect(playerLocation);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("toystick")) {
                    if (args.length >= 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatColor.RED + "Player not found");
                            return true;
                        }
                        if (!arenaUtil.isPlaying(player)) {
                            p.sendMessage(ChatColor.RED + "Player is not playing");
                            return true;
                        }
                        ItemStack toyStick = new ItemStack(Material.STICK, 1);
                        toyStick.addUnsafeEnchantment(Enchantment.LURE, 1);
                        ItemMeta meta = toyStick.getItemMeta();
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bMagic Toy Stick"));
                        List<String> loreList = new ArrayList<String>();
                        loreList.add(ChatColor.translateAlternateColorCodes('&', "&eClick me for an explosion!"));
                        meta.setLore(loreList);
                        toyStick.setItemMeta(meta);
                        player.getInventory().addItem(toyStick);
                        p.sendMessage(ChatColor.GREEN + "Gave " + args[1] + " a toystick!");
                        return true;
                    }
                }
                p.sendMessage(ChatColor.RED + "Invalid Usage.");
                return true;
            }
        }
        return true;
    }


    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("mlg", "cage", "blind", "slowhands", "kaboom", "toystick");
    }

}
