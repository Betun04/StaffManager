package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatBanWords implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){

            List<String> banedWordsList = Files.getChatFile().getStringList("banedWords");

            if(args.length==4){

                String word = args[2];

                switch (args[3]){
                    case "add":
                        if(banedWordsList.contains(word)){
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Esta palabra ya está baneada."));
                        }else{
                            banedWordsList.add(word);
                            Files.getChatFile().set("banedWords",banedWordsList);
                            Files.saveChatFile();
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"La palabra &c"+word+"&r fue baneada."));
                        }
                        break;
                    case "remove":
                        if(banedWordsList.contains(word)){
                            banedWordsList.remove(word);
                            Files.getChatFile().set("banedWords",banedWordsList);
                            Files.saveChatFile();
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"La palabra &a"+word+"&r ya no está baneada."));
                        }else{
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"La palabra &c"+word+"&r no está baneada."));
                        }
                        break;
                }
            }
        }
    }
}