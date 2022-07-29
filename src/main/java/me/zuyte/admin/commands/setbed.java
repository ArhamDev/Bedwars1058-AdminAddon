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

public class setbed extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();

    public setbed(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.setbed");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.setbed")) return false;
            if (args.length > 0) {
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
                    if (args[1] == "true") {
                        ITeam mplayerteam = arena.getPlayerTeam(player.getName());
                        mplayerteam.setBedDestroyed(false);
                    } else if (args[1] == "false") {
                        ITeam mplayerteam = arena.getPlayerTeam(player.getName());
                        mplayerteam.setBedDestroyed(true);
                    }
                }
            }
            p.sendMessage(ChatColor.RED + "Usage: /bw setbed <player> <true ┃ false>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

}
