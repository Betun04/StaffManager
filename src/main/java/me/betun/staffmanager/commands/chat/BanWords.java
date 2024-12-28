package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BanWords implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player){

            List<String> banedWordsList = Files.getChatFile().getStringList("banedWords");

            switch (args.length){

                case 1:
                    String word = args[0];
                    if(banedWordsList.contains(word)){
                        banedWordsList.remove(word);
                        Files.getChatFile().set("banedWords",banedWordsList);
                    }else{
                        banedWordsList.add(word);
                        Files.getChatFile().set("banedWords",banedWordsList);
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
