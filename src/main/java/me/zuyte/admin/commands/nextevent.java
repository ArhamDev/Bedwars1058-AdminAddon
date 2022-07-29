package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.NextEvent;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class nextevent extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();

    public nextevent(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.nextevent");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.nextevent")) return false;
            if (args.length > 0) {
                if (arenaUtil.getArenaByName(args[0]) == null) {
                    p.sendMessage(ChatColor.RED + "Arena not found");
                    return true;
                }
                IArena arena = arenaUtil.getArenaByName(args[0]);
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("diamond-2")) {
                        arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
                    } else if (args[1].equalsIgnoreCase("diamond-3")) {
                        arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
                    } else if (args[1].equalsIgnoreCase("emerald-2")) {
                        arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
                    } else if (args[1].equalsIgnoreCase("emerald-3")) {
                        arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
                    } else if (args[1].equalsIgnoreCase("bed-destroy")) {
                        arena.setNextEvent(NextEvent.BEDS_DESTROY);
                    } else if (args[1].equalsIgnoreCase("dragon")) {
                        arena.setNextEvent(NextEvent.ENDER_DRAGON);
                    } else if (args[1].equalsIgnoreCase("end")) {
                        arena.setNextEvent(NextEvent.GAME_END);
                    }
                    p.sendMessage(ChatColor.GREEN + "Success!");
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "Usage: /bw nextevent <arena>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

}
