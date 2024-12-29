package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StaffChatCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player p && sender.isOp()){

            List<String> staffChatToggled = Files.getChatFile().getStringList("staffsChat");

            if (args.length < 2) {
                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager staffchat <on|off>"));
                return;
            }

            String state = args[1].toLowerCase();
            if (state.equals("on")) {
                staffChatToggled.add(p.getUniqueId().toString());
                Files.getChatFile().set("staffsChat",staffChatToggled);
                Files.saveChatFile();

                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"StaffChat&a mode on"));
            } else if (state.equals("off")) {
                staffChatToggled.remove(p.getUniqueId().toString());
                Files.getChatFile().set("staffsChat",staffChatToggled);
                Files.saveChatFile();
                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"StaffChat&c mode off"));
            } else {
                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager staffchat <on|off>"));
            }
        }
    }
}