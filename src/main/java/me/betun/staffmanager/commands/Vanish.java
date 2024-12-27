package me.betun.staffmanager.commands;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.utils.MessageUtils;
import me.betun.staffmanager.utils.VanishUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Vanish implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player p){
            ArrayList<String> options = new ArrayList<>();
            options.add("pickup");
            options.add("spawning");
            options.add("collidable");
            options.add("silent");
            if(args.length == 0){
                if(p.isVisibleByDefault()){
                    VanishUtils.pickUp(p,false);
                    VanishUtils.spawning(p,false);
                    VanishUtils.collidable(p,false);
                    VanishUtils.silent(p,false);
                    VanishUtils.vanishState(p);
                    p.setVisibleByDefault(false);
                    p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&dYou are no longer visible, shh"));
                }
                else{
                    if(!p.getCanPickupItems()){
                        VanishUtils.pickUp(p,false);
                    }

                    if(!p.getAffectsSpawning()){
                        VanishUtils.spawning(p,false);
                    }

                    if(!p.isCollidable()){
                        VanishUtils.collidable(p,false);
                    }

                    if(!p.isSilent()){
                        VanishUtils.silent(p,false);
                    }

                    VanishUtils.vanishState(p);
                    p.setVisibleByDefault(true);
                    p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&dYou are visible again!"));
                }
            }else if(!args[0].equalsIgnoreCase("") && !options.contains(args[0])){
                p = Bukkit.getPlayer(args[0]); assert p != null;
                if(p.isVisibleByDefault()){
                    VanishUtils.pickUp(p,false);
                    VanishUtils.spawning(p,false);
                    VanishUtils.collidable(p,false);
                    VanishUtils.silent(p,false);
                    VanishUtils.hide(p);
                    p.setVisibleByDefault(false);
                }
                else{
                    VanishUtils.pickUp(p,false);
                    VanishUtils.spawning(p,false);
                    VanishUtils.collidable(p,false);
                    VanishUtils.silent(p,false);
                    VanishUtils.hide(p);
                    p.setVisibleByDefault(true);
                }
            }else if(args[0].equalsIgnoreCase("pickup")){
                if(!p.isVisibleByDefault()){
                    VanishUtils.pickUp(p,true);
                }else{
                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cYou must be vanished to use this command"));
                }
            }else if(args[0].equalsIgnoreCase("spawning")){
                if(!p.isVisibleByDefault()){
                    VanishUtils.spawning(p,true);
                }else{
                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cYou must be vanished to use this command"));
                }
            }else if(args[0].equalsIgnoreCase("collidable")) {
                if(!p.isVisibleByDefault()){
                    VanishUtils.collidable(p,true);
                }else{
                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cYou must be vanished to use this command"));
                }
            }else if(args[0].equalsIgnoreCase("silent")){
                if(!p.isVisibleByDefault()){
                    VanishUtils.silent(p,true);
                }else{
                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cYou must be vanished to use this command"));
                }
            }
        }else{
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix + "&cSender must be player"));
        }

        return true;
    }
}
