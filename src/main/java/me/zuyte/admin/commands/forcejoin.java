package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import me.zuyte.admin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class forcejoin {
    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();
public void player(Player p, String[] args) {
            if (!p.hasPermission("bw.admin.forcejoin")) {
                p.sendMessage(ChatColor.RED + "You dont have permission to use this command.");
                return;
            }
            if (args.length > 1) {
                Player player = Bukkit.getPlayerExact(args[1]);
                if (player == null) {
                    p.sendMessage(ChatColor.RED + "Player not found");
                    return;
                }
                if (args.length >= 3) {
                String arenaWorld = args[2];
                    if (arenaUtil.getArenaByName(arenaWorld) == null) {
                        p.sendMessage(ChatColor.RED + "Arena not found");
                        return;
                    } else {
                        IArena arena = arenaUtil.getArenaByName(arenaWorld);
                        arena.addPlayer(player, true);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                        return;
                    }
                }
            }
                p.sendMessage(ChatColor.RED + "Usage: /bw forcejoin <player> <arena>");
        }
    public void console(ConsoleCommandSender c, String[] args) {
            if (args.length > 1) {
                Player player = Bukkit.getPlayerExact(args[1]);
                if (player == null) {
                    c.sendMessage(ChatColor.RED + "Player not found");
                    return;
                }
                if (args.length >= 3) {
                    String arenaWorld = args[2];
                    if (arenaUtil.getArenaByName(arenaWorld) == null) {
                        c.sendMessage(ChatColor.RED + "Arena not found");
                        return;
                    } else {
                        IArena arena = arenaUtil.getArenaByName(arenaWorld);
                        arena.addPlayer(player, true);
                        c.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                        return;
                    }
                }
            }
            c.sendMessage(ChatColor.RED + "Usage: /bw forcejoin <player> <arena>");
    }
}