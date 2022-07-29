package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class revive extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();

    public revive(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.revive");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.revive")) return false;
            if (args.length > 1) {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    p.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
                if (arenaUtil.getArenaByPlayer(player) == null) {
                    p.sendMessage(ChatColor.RED + "Player has not joined an arena");
                    return true;
                }
                    IArena arena = arenaUtil.getArenaByPlayer(player);
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("bed")) {
                        arena.startReSpawnSession(player, 0);
                        player.resetTitle();
                        player.sendTitle(ChatColor.AQUA + "Revived!", ChatColor.RED + "By " + p.getName());
                        ITeam mplayerteam = arena.getPlayerTeam(player.getName());
                        mplayerteam.setBedDestroyed(false);
                        return true;
                    } else if (args[1].equalsIgnoreCase("final")) {
                        arena.startReSpawnSession(player, 0);
                        player.resetTitle();
                        player.sendTitle(ChatColor.AQUA + "Revived!", ChatColor.RED + "By " + p.getName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully revived " + args[0]));
                        return true;
                    }
                }
            }
            p.sendMessage(ChatColor.RED + "Usage: /bw revive <player> <final ┃ bed>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

}