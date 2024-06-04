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

public class SaveInventoryTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> l = new ArrayList<>();

        if (args.length == 0) {
            return l;
        }
        else if (!(sender instanceof Player)) {
            return l;
        }
        else if(args.length == 1){
            l.add("save");
            l.add("load");
            return l;
        }
        else if(args.length == 2){
            if(args[1].isEmpty()){
                l.add("all");
            }
            for(Player player: Bukkit.getOnlinePlayers()){
                if(l.isEmpty() || player.getName().contains(args[1])){
                    l.add(player.getName());
                }
            }
            return l;
        }
        else{
            return l;
        }

    }
}
