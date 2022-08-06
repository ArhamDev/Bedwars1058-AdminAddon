package me.zuyte.admin.commands;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.zuyte.admin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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
                    String team = args[1].substring(0,1).toUpperCase() + args[1].substring(1).toLowerCase();
                    if (playerArena.getTeam(team) == null) {
                        p.sendMessage(ChatColor.RED + "Team not found");
                        return true;
                    }
                    ITeam playerArenaTeam = playerArena.getTeam(team);
                    if (playerArena.getPlayerTeam(args[0]) != null) {
                        p.sendMessage(ChatColor.RED + "Player has already selected a team!");
                        return true;
                    }

                    if (playerArenaTeam.getMembers().size() == playerArena.getMaxInTeam()) {
                        p.sendMessage(ChatColor.RED + "Team is full!");
                        return true;
                    }
                    p.sendMessage(ChatColor.GREEN + "Successfully set " + args[0] + "'s Team to " + playerArenaTeam.getColor().chat() + playerArenaTeam.getName());
                    Main.getInstance().temp.put(player.getName(), playerArena.getArenaName());
                    Main.getInstance().temp.put(player.getName() + "-team", playerArenaTeam.getName());
                    return true;
                } else {
                    if (playerArena.getPlayerTeam(args[0]) != null) {
                        p.sendMessage(ChatColor.RED + "Player has already selected a team!");
                        return true;
                    }
                    setTeamGui(player, playerArena);
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "Usage: /bw setteam <player> <team>");
        }
        if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender c = (ConsoleCommandSender) commandSender;
                if (args.length > 0) {
                    Player player = Bukkit.getPlayerExact(args[0]);
                    if (player == null) {
                        c.sendMessage(ChatColor.RED + "Player not found");
                        return true;
                    }
                    if (arenaUtil.getArenaByPlayer(player) == null) {
                        c.sendMessage(ChatColor.RED + "Player has not joined an arena");
                        return true;
                    }
                    IArena playerArena = arenaUtil.getArenaByPlayer(player);
                    if (args.length >= 2) {
                        String team = args[1].substring(0,1).toUpperCase() + args[1].substring(1).toLowerCase();
                        if (playerArena.getTeam(team) == null) {
                            c.sendMessage(ChatColor.RED + "Team not found");
                            return true;
                        }
                        ITeam playerArenaTeam = playerArena.getTeam(team);
                        if (playerArena.getPlayerTeam(args[0]) != null) {
                            c.sendMessage(ChatColor.RED + "Player has already selected a team!");
                            return true;
                        }

                        if (playerArenaTeam.getMembers().size() == playerArena.getMaxInTeam()) {
                            c.sendMessage(ChatColor.RED + "Team is full!");
                            return true;
                        }
                        c.sendMessage(ChatColor.GREEN + "Successfully set " + args[0] + "'s Team to " + playerArenaTeam.getColor().chat() + playerArenaTeam.getName());
                        Main.getInstance().temp.put(player.getName(), playerArena.getArenaName());
                        Main.getInstance().temp.put(player.getName() + "-team", playerArenaTeam.getName());
                        return true;
                    }
                }
                c.sendMessage(ChatColor.RED + "Usage: /bw setteam <player> <team>");
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

    private void setTeamGui(Player player, IArena arena) {
        Inventory setTeamInventory = Bukkit.createInventory(player, 9, "Admin - Team Selector");
        for (int i = 0; i < arena.getTeams().size(); i++) {
            ITeam team = arena.getTeams().get(i);
            ItemStack itemStacki = new ItemStack(Material.WOOL, 1, team.getColor().dye().getWoolData());
            ItemMeta itemMetai = itemStacki.getItemMeta();
            itemMetai.setDisplayName(team.getColor().chat() + "" + ChatColor.BOLD + team.getName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "•" + ChatColor.GREEN + " Click to select team!");
            itemMetai.setLore(lore);
            itemStacki.setItemMeta(itemMetai);
            setTeamInventory.setItem(i, itemStacki);
        }
        player.openInventory(setTeamInventory);
    }
}