package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class ChatPause implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Uso: /staffmanager chat pause <on|off>");
            return;
        }

        String state = args[2].toLowerCase();
        if (state.equals("on")) {
            // Lógica para activar el pause chat
            Files.getChatFile().set("paused",true);
            Files.saveChatFile();
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Chat pausado."));
        } else if (state.equals("off")) {
            // Lógica para desactivar el pause chat
            Files.getChatFile().set("paused",false);
            Files.saveChatFile();
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Chat activado."));
        } else {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Uso: /staffmanager chat pause <on|off>"));
        }
    }
}
