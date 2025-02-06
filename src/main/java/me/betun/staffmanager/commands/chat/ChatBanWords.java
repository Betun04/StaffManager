package me.betun.staffmanager.commands.chat;

import com.mojang.brigadier.Message;
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
        if(sender instanceof Player && (sender.hasPermission("staffmanager.banword") || sender.hasPermission("staffmanager.chat.all") || sender.hasPermission("staffmanager.all"))){

            List<String> banedWordsList = Files.getChatFile().getStringList("banedWords");

            if(args.length==4){

                String word = args[3];

                switch (args[2]){
                    case "add":
                        if(banedWordsList.contains(word)){
                            MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The word is already banned");
                            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The word is already banned"));
                        }else{
                            banedWordsList.add(word);
                            Files.getChatFile().set("banedWords",banedWordsList);
                            Files.saveChatFile();
                            MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The word &c"+word+"&r was banned");
                            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The word &c"+word+"&r was banned"));
                        }
                        break;
                    case "remove":
                        if(banedWordsList.contains(word)){
                            banedWordsList.remove(word);
                            Files.getChatFile().set("banedWords",banedWordsList);
                            Files.saveChatFile();
                            MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The word &a"+word+"&r is no longer banned");
                            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The word &a"+word+"&r is no longer banned"));
                        }else{
                            MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The word &c"+word+"&r was not banned");
                            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The word &c"+word+"&r was not banned"));
                        }
                        break;
                }
            }else{
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The banword usage is: /sm chat banword add|remove <word>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The banword usage is: /sm chat banword add|remove <word>"));
            }
        }else{
            MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"&cYou don't have permission to use this command.");
            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cYou don't have permission to use this command."));
        }
    }
}