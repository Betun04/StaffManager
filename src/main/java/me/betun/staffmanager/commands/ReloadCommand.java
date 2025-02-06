package me.betun.staffmanager.commands;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.isOp() || sender instanceof ConsoleCommandSender) {

            StaffManager.getInstance().reloadConfig();
            Files.reload();
            MessageUtils.sendMessage((Player) sender,StaffManager.prefix + "&2Config reloaded");
        }
    }
}



