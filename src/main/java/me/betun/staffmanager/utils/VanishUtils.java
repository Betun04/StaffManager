package me.betun.staffmanager.utils;

import me.betun.staffmanager.StaffManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishUtils {

    public static void vanishState(Player p){
        String path = p.getUniqueId().toString();
        String status = Files.getVanish().getString(path+".vanish");

        try {
            if(status.equalsIgnoreCase("true")){
                Files.getVanish().set(path+".vanish","false");
            }else if(status.equalsIgnoreCase("false")){
                Files.getVanish().set(path+".vanish","true");
            }
        } catch(NullPointerException e) {
            if (p.isVisibleByDefault()) {
                Files.getVanish().set(path + ".vanish", "true");
            } else {
                Files.getVanish().set(path + ".vanish", "false");
            }
        }

        Files.saveVanish();

    }

    public static void pickUp(Player p,boolean announce){
        String path = p.getUniqueId().toString();
        String status = Files.getVanish().getString(path+".pickup");

        try {
            if(status.equalsIgnoreCase("true")){
                Files.getVanish().set(path+".pickup","false");
            }else if(status.equalsIgnoreCase("false")){
                Files.getVanish().set(path+".pickup","true");
            }
        } catch(NullPointerException e) {
            if (p.isVisibleByDefault()) {
                Files.getVanish().set(path + ".pickup", "false");
            } else {
                Files.getVanish().set(path + ".pickup", "true");
            }
        }

        Files.saveVanish();

        status = Files.getVanish().getString(path+".pickup");

        switch (status){
            case "true":
                p.setCanPickupItems(true);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aItem Pickup enabled"));}
                break;
            case "false":
                p.setCanPickupItems(false);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cItem Pickup disabled"));}
                break;
        }

    }

    public static void collidable(Player p,boolean announce){
        String path = p.getUniqueId().toString();
        String status = Files.getVanish().getString(path+".collidable");

        try {
            if(status.equalsIgnoreCase("true")){
                Files.getVanish().set(path+".collidable","false");
            }else if(status.equalsIgnoreCase("false")){
                Files.getVanish().set(path+".collidable","true");
            }
        } catch(NullPointerException e) {
            if (p.isVisibleByDefault()) {
                Files.getVanish().set(path + ".collidable", "false");
            } else {
                Files.getVanish().set(path + ".collidable", "true");
            }
        }

        Files.saveVanish();

        status = Files.getVanish().getString(path+".collidable");

        switch (status){
            case "true":
                p.setCollidable(true);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aCollidable enabled"));}
                break;
            case "false":
                p.setCollidable(false);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cCollidable disabled"));}
                break;
        }

    }

    public static void hide(Player p){
        for(Player p2: Bukkit.getOnlinePlayers()){
            p2.hidePlayer(StaffManager.getInstance(),p);
        }
    }

}
