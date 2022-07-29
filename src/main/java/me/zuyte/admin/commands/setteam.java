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

public class setteam extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();

    public setteam(ParentCommand parent, String name) {
        super(parent, name);
        showInList(true);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.setteam");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.setteam")) return false;
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
                IArena playerArena = arenaUtil.getArenaByPlayer(player);
                if (args.length >= 2) {
                    String team = args[1];
                    if (playerArena.getTeam(team) == null) {
                        p.sendMessage(ChatColor.RED + "Team not found");
                        return true;
                    }
                    ITeam playerArenaTeam = playerArena.getTeam(team);
                    if (playerArena.getPlayerTeam(args[0]) != null) {
                        p.sendMessage(ChatColor.RED + "Player is already in a team");
                        return true;
                    }

                    if (playerArenaTeam.getMembers().size() == playerArena.getMaxInTeam()) {
                        p.sendMessage(ChatColor.RED + "Team is full!");
                        return true;
                    }
                    playerArenaTeam.addPlayers(player);
                    p.sendMessage(ChatColor.GREEN + "Successfully set " + args[0] + "'s Team to " + playerArenaTeam.getColor().chat() + args[1]);
                    Main.getInstance().temp.put(player.getName(), playerArena.getArenaName());
                    Main.getInstance().temp.put(player.getName() + "-team", playerArenaTeam.getName());
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "Usage: /bw setteam <player> <team>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

}