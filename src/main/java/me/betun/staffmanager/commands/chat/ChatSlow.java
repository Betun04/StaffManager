package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class ChatSlow implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat slow <on|off>"));
            return;
        }

        String state = args[2].toLowerCase();
        if (state.equals("on")) {
            // Lógica para activar el slow chat
            Files.getChatFile().set("slowed",true);
            Files.saveChatFile();
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Slow chat on."));
        } else if (state.equals("off")) {
            // Lógica para desactivar el slow chat
            Files.getChatFile().set("slowed",false);
            Files.saveChatFile();
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Slow chat off."));
        } else {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat slow <on|off>"));
        }
    }
}