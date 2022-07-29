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

public class skipevent extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();

    public skipevent(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.skipevent");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.skipevent")) return false;
            if (args.length > 0) {
                if (arenaUtil.getArenaByName(args[0]) == null) {
                    p.sendMessage(ChatColor.RED + "Arena not found");
                    return true;
                }
                IArena arena = arenaUtil.getArenaByName(args[0]);
                    if (arena.getNextEvent().equals(NextEvent.DIAMOND_GENERATOR_TIER_II)) {
                        arena.setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_III);
                    } else if (arena.getNextEvent().equals(NextEvent.DIAMOND_GENERATOR_TIER_III)) {
                        arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
                    } else if (arena.getNextEvent().equals(NextEvent.EMERALD_GENERATOR_TIER_II)) {
                        arena.setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_III);
                    } else if (arena.getNextEvent().equals(NextEvent.EMERALD_GENERATOR_TIER_III)) {
                        arena.setNextEvent(NextEvent.BEDS_DESTROY);
                    } else if (arena.getNextEvent().equals(NextEvent.BEDS_DESTROY)) {
                        arena.setNextEvent(NextEvent.ENDER_DRAGON);
                    } else if (arena.getNextEvent().equals(NextEvent.ENDER_DRAGON)) {
                        arena.setNextEvent(NextEvent.GAME_END);
                    } else if (arena.getNextEvent().equals(NextEvent.GAME_END)) {
                        p.sendMessage((ChatColor.RED + "You are already at the last event"));
                        return true;
                    }
                    p.sendMessage(ChatColor.GREEN + "Current event has been skipped!");
                    return true;
                }
            p.sendMessage(ChatColor.RED + "Usage: /bw skipevent <arena>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

}
