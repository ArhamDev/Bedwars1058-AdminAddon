package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class forcejoin extends SubCommand {

    BedWars.ArenaUtil arenaUtil = Main.getInstance().bw.getArenaUtil();
    public forcejoin(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
        setPriority(20);
        setArenaSetupCommand(false);
        setPermission("bw.admin.forcejoin");
    }

    @Override
    public boolean execute(String[] args, CommandSender commandSender) {
        if (commandSender instanceof Player){
            Player p = (Player) commandSender;
            if (!p.hasPermission("bw.admin.forcejoin")) return false;
            if (args.length > 0) {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    p.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
                if (args.length >= 2) {
                String arenaWorld = args[1];
                    if (arenaUtil.getArenaByName(arenaWorld) == null) {
                        p.sendMessage(ChatColor.RED + "Arena not found");
                        return true;
                    } else {
                        IArena arena = arenaUtil.getArenaByName(arenaWorld);
                        arena.addPlayer(player, true);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aForce joined " + player.getName() + " to " + arenaWorld));
                        return true;
                    }
                }
            }
                p.sendMessage(ChatColor.RED + "Usage: /bw forcejoin <player> <arena>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

}