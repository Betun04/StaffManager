package me.betun.staffmanager.commands;

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

        if(args.length==0){
            return l;
        }
        else if(args.length==1){
            l.add("chat");
            l.add("staffchat");
        }
        else if(args.length==2) {
            switch (args[0]){
                case "chat":
                    l.add("slow");
                    l.add("mute");
                    l.add("pause");
                    l.add("banword");
                    break;
                case "staffchat":
                    l.add("on");
                    l.add("off");
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

                    String input = args[2].toLowerCase(); // Texto que ya escribió el usuario

                    // Iterar sobre los jugadores online
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String playerName = player.getName();
                        if (playerName.toLowerCase().startsWith(input)) {
                            l.add(playerName); // Agregar coincidencias
                        }
                    }
                    break;
            }
        }else if(args.length==4){
            if (args[1].equals("banword")) {
                l.add("add");
                l.add("remove");
            }
        }

        return l;
    }
}
