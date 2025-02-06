package me.betun.staffmanager.commands.chat;

import com.mojang.brigadier.Message;
import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatPause implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && (sender.hasPermission("staffmanager.pause") || sender.hasPermission("staffmanager.chat.all") || sender.hasPermission("staffmanager.all"))){
            if (args.length < 3) {
                MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"Usage: /staffmanager chat pause <on|off>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat pause <on|off>"));
                return;
            }

            String state = args[2].toLowerCase();
            if (state.equals("on")) {
                // Lógica para activar el pause chat
                Files.getChatFile().set("paused",true);
                Files.saveChatFile();
                for(Player p: Bukkit.getOnlinePlayers()){
                    MessageUtils.sendMessage(p,StaffManager.prefix+"Chat has been paused");
                }
                //Bukkit.broadcast(MessageUtils.coloredMessage(StaffManager.prefix+"Chat has been paused"));
            } else if (state.equals("off")) {
                // Lógica para desactivar el pause chat
                Files.getChatFile().set("paused",false);
                Files.saveChatFile();
                for(Player p: Bukkit.getOnlinePlayers()){
                    MessageUtils.sendMessage(p,StaffManager.prefix+"Chat is active again");
                }
                //Bukkit.broadcast(MessageUtils.coloredMessage(StaffManager.prefix+"Chat is active again"));
            } else {
                MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"Usage: /staffmanager chat pause <on|off>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat pause <on|off>"));
            }
        }else{
            MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&cYou don't have permission to use this command.");
            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cYou don't have permission to use this command."));
        }
    }
}
