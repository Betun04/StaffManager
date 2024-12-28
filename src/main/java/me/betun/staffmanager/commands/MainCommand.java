package me.betun.staffmanager.commands;

import me.betun.staffmanager.commands.chat.ChatCommand;
import me.betun.staffmanager.interfaces.SubCommand;
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
        //subCommands.put("freeze", new FreezeCommand());
        //subCommands.put("vanish", new VanishCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Uso: /staffmanager <subcomando>");
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            subCommand.execute(sender, args);
        } else {
            sender.sendMessage("Subcomando no válido. Usa /staffmanager para ayuda.");
        }

        return true;
    }
}