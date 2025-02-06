package me.betun.staffmanager.commands;

import me.betun.staffmanager.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        List<String> l = new ArrayList<>();
        String input;

        if(args.length==0){
            return l;
        }
        else if(args.length==1){
            if(sender.hasPermission("staffmanager.chat") || sender.hasPermission("staffmanager.*")) {
                l.add("chat");
            }
            if(sender.hasPermission("staffmanager.staffchat") || sender.hasPermission("staffmanager.*")) {
                l.add("staffchat");
            }
            if(sender.hasPermission("staffmanager.freeze") || sender.hasPermission("staffmanager.*")) {
                l.add("freeze");
            }
            if(sender.hasPermission("staffmanager.vanish") || sender.hasPermission("staffmanager.*")) {
                l.add("vanish");
            }
            if(sender.hasPermission("staffmanager.saveinv") || sender.hasPermission("staffmanager.*")) {
                l.add("saveinv");
            }
            if(sender.hasPermission("staffmanager.invsee") || sender.hasPermission("staffmanager.*")) {
                l.add("invsee");
            }
            l.add("reload");
        }
        else if(args.length==2) {
            switch (args[0]){
                case "chat":
                    if(sender.hasPermission("staffmanager.slow") || sender.hasPermission("staffmanager.*")) {
                        l.add("slow");
                    }
                    if(sender.hasPermission("staffmanager.mute") || sender.hasPermission("staffmanager.*")) {
                        l.add("mute");
                    }
                    if(sender.hasPermission("staffmanager.pause") || sender.hasPermission("staffmanager.*")) {
                        l.add("pause");
                    }
                    if(sender.hasPermission("staffmanager.banword") || sender.hasPermission("staffmanager.*")) {
                        l.add("banword");
                    }
                    break;
                case "staffchat":
                    l.add("on");
                    l.add("off");
                    break;
                case "freeze", "invsee":
                    input = args[1].toLowerCase(); // Texto que ya escribió el usuario

                    // Iterar sobre los jugadores online
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String playerName = player.getName();
                        if (playerName.toLowerCase().startsWith(input)) {
                            l.add(playerName); // Agregar coincidencias
                        }
                    }
                    break;
                case "vanish":
                    l.add("pickup");
                    l.add("collidable");
                    break;
                case "saveinv":
                    l.add("save");
                    l.add("load");
                    break;
                default:
                    return l;
            }
        }else if(args.length==3){
            switch (args[1]){
                case "slow", "pause":
                    l.add("on");
                    l.add("off");
                    break;
                case "mute":

                    input = args[2].toLowerCase(); // Texto que ya escribió el usuario

                    // Iterar sobre los jugadores online
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String playerName = player.getName();
                        if (playerName.toLowerCase().startsWith(input)) {
                            l.add(playerName); // Agregar coincidencias
                        }
                    }
                    break;
                case "save","load":
                    l.add("all");
                    input = args[2].toLowerCase(); // Texto que ya escribió el usuario

                    // Iterar sobre los jugadores online
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String playerName = player.getName();
                        if (playerName.toLowerCase().startsWith(input)) {
                            l.add(playerName); // Agregar coincidencias
                        }
                    }
                    break;
                case "banword":
                    l.add("add");
                    l.add("remove");
                    break;
            }
        }else if(args.length==4){
            if (args[2].equals("remove")) {
                List<String> banedWords = Files.getChatFile().getStringList("banedWords");
                l.addAll(banedWords);
            }

        }

        return l;
    }
}
