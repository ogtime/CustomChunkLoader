package org.kayteam.chunkloader.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.chunkloader.util.PermissionChecker;
import org.kayteam.storageapi.storage.Yaml;

import java.util.ArrayList;
import java.util.List;

public class Command_ChunkLoader implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    new Command_Help().chunkHelp(player);
                } else if (args[0].equalsIgnoreCase("list")) {
                    if (PermissionChecker.check(player, "chunkloader.list")) {
                        new Command_List().chunkList(player);
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (PermissionChecker.check(player, "chunkloader.reload")) {
                        new Command_Reload().commandReload(player);
                    }
                } else if (args[0].equalsIgnoreCase("on")) {
                    if (PermissionChecker.check(player, "chunkloader.on")) {
                        new Command_On().enableChunkLoad(player);
                    }
                } else if (args[0].equalsIgnoreCase("off")) {
                    if (PermissionChecker.check(player, "chunkloader.off")) {
                        new Command_Off().disableChunkLoad(player);
                    }

                } else if (args[0].equalsIgnoreCase("give")) {
                    if (PermissionChecker.check(player, "chunkloader.give")) {
                        try {
                            // Set receiver if one is specified
                            Player receiver = player;
                            if (args.length > 1) {
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    if (p.getDisplayName().equals(args[1])) {
                                        receiver = p;
                                    }
                                }
                            }

                            // Check if the radius is valid
                            if (args[2].equals("1x1") || args[2].equals("2x2") || args[2].equals("3x3")) {
                                new Command_CreateChunkLoader().createChunkLoader(receiver, args[2]);
                            }
                            else {
                                ChunkLoader.messages.sendMessage(player, "give.command");
                            }
                        } catch (Exception ignored) {
                        }

                    }
                } else if (args[0].equalsIgnoreCase("tp")) {
                    if (PermissionChecker.check(player, "chunkloader.tp")) {
                        if (args.length > 1) {
                            try {
                                new Command_TP().chunkTeleport(player, Integer.parseInt(args[1]));
                            } catch (Exception ignored) {
                            }
                        } else {
                            Yaml.sendSimpleMessage(player, "&c/cl tp <list-number>");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("menu")) {
                    if (PermissionChecker.check(player, "chunkloader.menu")) {
                        new Command_Menu().openMenu(player);
                    }
                } else {
                    new Command_Help().chunkHelp(player);
                }
            } else {
                new Command_Help().chunkHelp(player);
            }
        }
        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command c, @NotNull String label, String[] args) {
        ArrayList<String> tabs = new ArrayList<>();
        if (args.length == 1) {
            tabs.add("help");
            tabs.add("list");
            tabs.add("menu");
            tabs.add("on");
            tabs.add("off");
            tabs.add("tp");
            tabs.add("reload");
            tabs.add("give");

            return tabs;
        } else if (args.length == 2) {
            if (args[0].equals("tp")) {
                for (int i = 0; i < ChunkLoader.getChunkManager().getChunkList().size(); i++) {
                    tabs.add(String.valueOf(i));
                }
                return tabs;
            }
        } else if (args.length == 3) {
            if (args[0].equals("give")) {
                tabs.add("1x1");
                tabs.add("2x2");
                tabs.add("3x3");

                return tabs;
            }
        }
        return null;
    }
}
