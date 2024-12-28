package me.betun.staffmanager.commands.inventory;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.utils.InventorySaveUtil;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class SaveInventory implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(args.length > 0){
            switch (args[0]){
                case "save":

                    if(args[1] != null && !args[1].equalsIgnoreCase("all")){
                        Player p = Bukkit.getPlayer(args[1]); assert p != null;
                        boolean result = InventorySaveUtil.saveInv(p);
                        if(result){sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix +"&aInventory of "+p.getName()+" saved"));}
                        else {sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory of "+p.getName()+" was already saved"));}
                    }else if(args[1].equalsIgnoreCase("all")){
                        ArrayList<String> alreadySaved = new ArrayList<>();

                        boolean result = false;

                        for(Player p: Bukkit.getOnlinePlayers()){
                            result = InventorySaveUtil.saveInv(p);
                            if(!result){alreadySaved.add(p.getName());}
                        }

                        if(result){sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aInventories saved"));}

                        for(String name: alreadySaved){
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory of "+name+" was already saved"));
                        }
                    }

                    break;
                case "load":

                    if(args[1] != null && !args[1].equalsIgnoreCase("all")){
                        Player p = Bukkit.getPlayer(args[1]); assert p != null;
                        boolean result = InventorySaveUtil.loadInv(p);
                        if(result){sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aInventory loaded"));}
                        else{sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory wasn't saved"));}
                    }else if(args[1].equalsIgnoreCase("all")){
                        boolean result = false;
                        ArrayList<String> notSaved = new ArrayList<>();
                        for(Player p: Bukkit.getOnlinePlayers()){
                            result = InventorySaveUtil.loadInv(p);
                            if(!result){notSaved.add(p.getName());}
                        }
                        if(notSaved.size() < Bukkit.getOnlinePlayers().size()){
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aInventories loaded"));
                            for(String name: notSaved){
                                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory of "+name+" wasn't saved"));
                            }
                        }
                    }else{
                        sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cMissing argument"));
                    }
                    break;
            }
        }else{
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cMissing argument"));
        }
        return true;
    }
}
