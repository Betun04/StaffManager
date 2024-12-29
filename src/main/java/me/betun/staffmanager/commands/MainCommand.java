package me.betun.staffmanager.commands;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.commands.chat.ChatCommand;
import me.betun.staffmanager.commands.chat.StaffChatCommand;
import me.betun.staffmanager.commands.freeze.FreezeCommand;
import me.betun.staffmanager.commands.inventory.InvSeeCommand;
import me.betun.staffmanager.commands.inventory.SaveInventoryCommand;
import me.betun.staffmanager.commands.vanish.VanishCommand;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class MainCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public MainCommand() {
        // Registrar subcomandos
        subCommands.put("chat", new ChatCommand());
        subCommands.put("staffchat", new StaffChatCommand());
        subCommands.put("freeze", new FreezeCommand());
        subCommands.put("vanish", new VanishCommand());
        subCommands.put("saveinv", new SaveInventoryCommand());
        subCommands.put("invsee", new InvSeeCommand());
        subCommands.put("reload", new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Component[] lines = {
                    MessageUtils.coloredMessage(StaffManager.prefix),
                    MessageUtils.coloredMessage("&6Usages for /staffmanager:"),
                    MessageUtils.coloredMessage("&7- /sm chat"),
                    MessageUtils.coloredMessage("&7- /sm staffchat"),
                    MessageUtils.coloredMessage("&7- /sm freeze"),
                    MessageUtils.coloredMessage("&7- /sm vanish"),
                    MessageUtils.coloredMessage("&7- /sm saveinv"),
                    MessageUtils.coloredMessage("&7- /sm invsee"),
                    MessageUtils.coloredMessage("&7- /sm reload")
            };

            for (Component line : lines) {
                sender.sendMessage(line);
            }
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Invalid subcommand. Use /staffmanager for help."));
        }

        return true;
    }
}