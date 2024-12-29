package me.betun.staffmanager.commands;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player p) {

            StaffManager.getInstance().reloadConfig();
            Files.reload();
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&2Config reloaded"));

        }else{
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cSender must be player"));
        }
    }
}
