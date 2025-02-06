package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSlow implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && (sender.hasPermission("staffmanager.slow") || sender.hasPermission("staffmanager.chat.all") || sender.hasPermission("staffmanager.all"))) {
            if (args.length < 3) {
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix + "Usage: /staffmanager chat slow <on|off>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "Usage: /staffmanager chat slow <on|off>"));
                return;
            }

            String state = args[2].toLowerCase();
            if (state.equals("on")) {
                // Lógica para activar el slow chat
                Files.getChatFile().set("slowed", true);
                Files.saveChatFile();
                for(Player p: Bukkit.getOnlinePlayers()){
                    MessageUtils.sendMessage(p, StaffManager.prefix + "Slow chat on");
                }
                //Bukkit.broadcast(MessageUtils.coloredMessage(StaffManager.prefix + "Slow chat on"));
            } else if (state.equals("off")) {
                // Lógica para desactivar el slow chat
                Files.getChatFile().set("slowed", false);
                Files.saveChatFile();
                for(Player p: Bukkit.getOnlinePlayers()){
                    MessageUtils.sendMessage(p, StaffManager.prefix + "Slow chat off");
                }
                //Bukkit.broadcast(MessageUtils.coloredMessage(StaffManager.prefix + "Slow chat off"));
            } else {
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix + "Usage: /staffmanager chat slow <on|off>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "Usage: /staffmanager chat slow <on|off>"));
            }
        }else{
            MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"&cYou don't have permission to use this command.");
            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cYou don't have permission to use this command."));
        }
    }
}