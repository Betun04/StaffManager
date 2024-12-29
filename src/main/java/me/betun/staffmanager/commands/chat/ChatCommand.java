package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class ChatCommand implements SubCommand {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public ChatCommand() {
        subCommands.put("slow", new ChatSlow());
        subCommands.put("mute", new ChatMute());
        subCommands.put("pause", new ChatPause());
        subCommands.put("banword", new ChatBanWords());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /staffmanager chat <subcommand>"));
            return;
        }

        SubCommand subCommand = subCommands.get(args[1].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Invalid option for chat."));
        }
    }
}