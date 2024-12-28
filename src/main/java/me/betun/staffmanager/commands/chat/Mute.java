package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class Mute implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player){

            List<String> mutedList = Files.getChatFile().getStringList("muted");

            switch (args.length){

                case 1:
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null && mutedList.contains(target.getUniqueId().toString())){
                        mutedList.remove(target.getUniqueId().toString());
                        Files.getChatFile().set("muted",mutedList);
                    }else if(target != null){
                        mutedList.add(target.getUniqueId().toString());
                        Files.getChatFile().set("muted",mutedList);
                    }

                    Files.saveChatFile();

                    break;
                default:
                    sender.sendMessage("Missing arguments");
            }

        }

        return true;
    }
}
