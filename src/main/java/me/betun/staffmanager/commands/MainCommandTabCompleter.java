package me.betun.staffmanager.commands;

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
        }
        else if(args.length==2) {
            switch (args[0]){
                case "chat":
                    l.add("slow");
                    break;
                default:
                    return l;
            }
        }else if(args.length==3){
            switch (args[1]){
                case "slow":
                    l.add("on");
                    l.add("off");
                    break;
            }
        }

        return l;
    }
}
