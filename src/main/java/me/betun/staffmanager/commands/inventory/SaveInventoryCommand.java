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
        if(sender instanceof Player && (sender.hasPermission("staffmanager.saveinv") || sender.hasPermission("staffmanager.all"))){

            if(args.length == 3){
                switch (args[1]){
                    case "save":

                        if(args[2] != null && !args[2].equalsIgnoreCase("all")){
                            Player p = Bukkit.getPlayer(args[2]); assert p != null;
                            boolean result = InventorySaveUtil.saveInv(p);
                            if(result){MessageUtils.sendMessage((Player) sender,StaffManager.prefix +"&aInventory of "+p.getName()+" saved");}
                            else {MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&dInventory of "+p.getName()+" was already saved");;}
                        }else if(args[2] != null && args[2].equalsIgnoreCase("all")){
                            ArrayList<String> alreadySaved = new ArrayList<>();

                            boolean result = false;

                            for(Player p: Bukkit.getOnlinePlayers()){
                                result = InventorySaveUtil.saveInv(p);
                                if(!result){alreadySaved.add(p.getName());}
                            }

                            if(result){MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&aInventories saved");}
                            for(String name: alreadySaved){
                                MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&dInventory of "+name+" was already saved");
                                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&dInventory of "+name+" was already saved"));
                            }
                        }

                        break;
                    case "load":

                        if(args[2] != null && !args[2].equalsIgnoreCase("all")){
                            Player p = Bukkit.getPlayer(args[2]); assert p != null;
                            boolean result = InventorySaveUtil.loadInv(p);
                            if(result){MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&aInventory loaded");}
                            else{MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&dInventory wasn't saved");}
                        }else if(args[2] != null && args[2].equalsIgnoreCase("all")){
                            boolean result = false;
                            ArrayList<String> notSaved = new ArrayList<>();
                            for(Player p: Bukkit.getOnlinePlayers()){
                                result = InventorySaveUtil.loadInv(p);
                                if(!result){notSaved.add(p.getName());}
                            }
                            if(notSaved.size() < Bukkit.getOnlinePlayers().size()){
                                MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&aInventories loaded");
                                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aInventories loaded"));
                            }
                        }else{
                            MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&cMissing argument");
                            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cMissing argument"));
                        }
                        break;
                }
            }else{
                MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"Usage: /sm saveinv <save/load> <all/player>");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"Usage: /sm saveinv <save/load> <all/player>"));
            }
        }else{
            MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&cYou don't have permission to use this command.");
            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cYou don't have permission to use this command."));
        }
    }
}
