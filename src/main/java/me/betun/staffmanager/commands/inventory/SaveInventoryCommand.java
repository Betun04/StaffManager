package me.betun.staffmanager.commands.inventory;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.InventorySaveUtil;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SaveInventoryCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){

            if(args.length == 3){
                switch (args[1]){
                    case "save":

                        if(args[2] != null && !args[2].equalsIgnoreCase("all")){
                            Player p = Bukkit.getPlayer(args[2]); assert p != null;
                            boolean result = InventorySaveUtil.saveInv(p);
                            if(result){sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix +"&aInventory of "+p.getName()+" saved"));}
                            else {sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory of "+p.getName()+" was already saved"));}
                        }else if(args[2] != null && args[2].equalsIgnoreCase("all")){
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

                        if(args[2] != null && !args[2].equalsIgnoreCase("all")){
                            Player p = Bukkit.getPlayer(args[2]); assert p != null;
                            boolean result = InventorySaveUtil.loadInv(p);
                            if(result){sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aInventory loaded"));}
                            else{sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory wasn't saved"));}
                        }else if(args[2] != null && args[2].equalsIgnoreCase("all")){
                            boolean result = false;
                            ArrayList<String> notSaved = new ArrayList<>();
                            for(Player p: Bukkit.getOnlinePlayers()){
                                result = InventorySaveUtil.loadInv(p);
                                if(!result){notSaved.add(p.getName());}
                            }
                            if(notSaved.size() < Bukkit.getOnlinePlayers().size()){
                                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aInventories loaded"));
                            }
                        }else{
                            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cMissing argument"));
                        }
                        break;
                }
            }else{
                sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /sm saveinv <save/load> <all/player>"));
            }
        }
    }
}
