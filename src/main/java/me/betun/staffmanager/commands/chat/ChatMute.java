package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatMute implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && (sender.hasPermission("staffmanager.mute") || sender.hasPermission("staffmanager.chat.all") || sender.hasPermission("staffmanager.all"))){
            if (args.length < 3) {
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"Usage: /staffmanager chat mute <player>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat mute <player>"));
                return;
            }

            String mutedName = args[2].toLowerCase();
            Player p = Bukkit.getPlayer(mutedName);
            List<String> mutedList = Files.getChatFile().getStringList("muted");

            if (p != null && p.isOnline() && !p.isOp() && !mutedList.contains(p.getUniqueId().toString()) ) {
                // Lógica para activar el mute
                mutedList.add(p.getUniqueId().toString());
                Files.getChatFile().set("muted",mutedList);
                Files.saveChatFile();
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The user &3"+p.getName()+"&r was muted");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The user &3"+p.getName()+"&r was muted"));
            } else if (p != null && p.isOnline() && !p.isOp() && mutedList.contains(p.getUniqueId().toString())){
                // Lógica para desactivar el mute
                mutedList.remove(p.getUniqueId().toString());
                Files.getChatFile().set("muted",mutedList);
                Files.saveChatFile();
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"The user &3"+p.getName()+"&r can speak again");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The user &3"+p.getName()+"&r can speak again"));
            } else {
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix+"Usage: /staffmanager chat mute <player> (Can't mute op players)");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat mute <player> (Can't mute op players)"));
            }
        }else{
            if(sender instanceof Player) {
                MessageUtils.sendMessage((Player) sender, StaffManager.prefix + "&cYou don't have permission to use this command.");
            }
            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cYou don't have permission to use this command."));
        }
    }
}
