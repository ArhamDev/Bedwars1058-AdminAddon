package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
                        BlockFace targetFace = Main.getInstance().beds.get(arena.getArenaName()+ mplayerteam.getName()+"Main");
                        placeBed(mplayerteam.getBed(), targetFace.getOppositeFace());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully revived " + args[0]));
                        return true;
                    } else if (args[1].equalsIgnoreCase("final")) {
                        arena.startReSpawnSession(player, 0);
                        player.resetTitle();
                        player.sendTitle(ChatColor.AQUA + "Revived!", ChatColor.RED + "By " + p.getName());
                        ITeam mplayerteam = arena.getPlayerTeam(player.getName());
                        mplayerteam.setBedDestroyed(true);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully revived " + args[0]));
                        return true;
                    }
                }
            }
            p.sendMessage(ChatColor.RED + "Usage: /bw revive <player> <final ┃ bed>");
        }
        if (commandSender instanceof ConsoleCommandSender) {
        ConsoleCommandSender c = (ConsoleCommandSender) commandSender;
            if (args.length > 1) {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    c.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
                if (arenaUtil.getArenaByPlayer(player) == null) {
                    c.sendMessage(ChatColor.RED + "Player has not joined an arena");
                    return true;
                }
                IArena arena = arenaUtil.getArenaByPlayer(player);
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("bed")) {
                        arena.startReSpawnSession(player, 0);
                        player.resetTitle();
                        player.sendTitle(ChatColor.AQUA + "Revived!", ChatColor.RED + "By " + c.getName());
                        ITeam mplayerteam = arena.getPlayerTeam(player.getName());
                        mplayerteam.setBedDestroyed(false);
                        BlockFace targetFace = Main.getInstance().beds.get(arena.getArenaName()+ mplayerteam.getName()+"Main");
                        placeBed(mplayerteam.getBed(), targetFace.getOppositeFace());
                        c.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully revived " + args[0]));
                        return true;
                    } else if (args[1].equalsIgnoreCase("final")) {
                        arena.startReSpawnSession(player, 0);
                        player.resetTitle();
                        player.sendTitle(ChatColor.AQUA + "Revived!", ChatColor.RED + "By " + c.getName());
                        ITeam mplayerteam = arena.getPlayerTeam(player.getName());
                        mplayerteam.setBedDestroyed(true);
                        c.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully revived " + args[0]));
                        return true;
                    }
                }
            }
            c.sendMessage(ChatColor.RED + "Usage: /bw revive <player> <final ┃ bed>");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
    }

    private void placeBed(Location loc, BlockFace face) {

        BlockState bedFoot = loc.getBlock().getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(face.getOppositeFace()).getState();

        bedFoot.setType(Material.BED_BLOCK);
        bedHead.setType(Material.BED_BLOCK);

        bedFoot.setRawData((byte) face.ordinal());
        bedHead.setRawData((byte) (face.ordinal()+8));

        bedFoot.update(true, false);
        bedHead.update(true, true);

    }

}