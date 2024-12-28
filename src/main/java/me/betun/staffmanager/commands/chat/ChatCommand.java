package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.interfaces.SubCommand;
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
            sender.sendMessage("Uso: /staffmanager chat <subcomando>");
            return;
        }

        SubCommand subCommand = subCommands.get(args[1].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage("Subcomando no válido para chat.");
        }
    }
}