package me.betun.staffmanager.commands.vanish;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.MessageUtils;
import me.betun.staffmanager.utils.VanishUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player p) {

            ArrayList<String> options = new ArrayList<>();
            options.add("pickup");
            options.add("spawning");
            options.add("collidable");
            if(args.length == 1){
                if(p.isVisibleByDefault()){
                    VanishUtils.pickUp(p,false);
                    VanishUtils.collidable(p,false);
                    VanishUtils.vanishState(p);
                    p.setVisibleByDefault(false);
                    p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&dYou are no longer visible, shh"));
                }
                else{
                    if(!p.getCanPickupItems()){
                        VanishUtils.pickUp(p,false);
                    }

                    if(!p.isCollidable()){
                        VanishUtils.collidable(p,false);
                    }

                    VanishUtils.vanishState(p);
                    p.setVisibleByDefault(true);
                    p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&dYou are visible again!"));
                }
            }else if(args[1].equalsIgnoreCase("pickup")){
                if(!p.isVisibleByDefault()){
                    VanishUtils.pickUp(p,true);
                }else{
                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cYou must be vanished to use this command"));
                }
            }else if(args[1].equalsIgnoreCase("collidable")) {
                if(!p.isVisibleByDefault()){
                    VanishUtils.collidable(p,true);
                }else{
                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cYou must be vanished to use this command"));
                }
            }
        }else{
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cSender must be player"));
        }
    }
}