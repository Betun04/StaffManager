package me.betun.staffmanager.commands.chat;

import me.betun.staffmanager.utils.Files;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StaffChat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player p && sender.isOp()){

            List<String> staffChatToggled = Files.getChatFile().getStringList("staffsChat");

            if(cmd.getName().equalsIgnoreCase("staffChat") && args.length == 1 && args[0].equalsIgnoreCase("toggle")){

                if(staffChatToggled.contains(p.getUniqueId().toString())){
                    staffChatToggled.remove(p.getUniqueId().toString());
                    Files.getChatFile().set("staffsChat",staffChatToggled);
                }else{
                    staffChatToggled.add(p.getUniqueId().toString());
                    Files.getChatFile().set("staffsChat",staffChatToggled);
                }

                Files.saveChatFile();

            }

        }

        return true;

    }
}
