package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.utils.Files;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Slow implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player && sender.isOp()){

            if(cmd.getName().equalsIgnoreCase("slow") && args.length == 1){
                switch (args[0]){
                    case "on":
                        Files.getChatFile().set("slowed",true);
                        break;
                    case "off":
                        Files.getChatFile().set("slowed",false);
                        break;
                }

                Files.saveChatFile();

            }

        }

        return true;
    }
}
