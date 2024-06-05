package me.betun.staffmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VanishTabCompleter implements TabCompleter {
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
            l.add("pickup");
            l.add("spawning");
            l.add("collidable");
            l.add("silent");
            return l;
        }
        return l;
    }
}
