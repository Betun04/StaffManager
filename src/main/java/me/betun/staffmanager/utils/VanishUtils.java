package me.betun.staffmanager.utils;

import me.betun.staffmanager.StaffManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishUtils {

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

    public static void spawning(Player p,boolean announce){
        String path = p.getUniqueId().toString();
        String status = Files.getVanish().getString(path+".affectSpawning");

        try {
            if(status.equalsIgnoreCase("true")){
                Files.getVanish().set(path+".affectSpawning","false");
            }else if(status.equalsIgnoreCase("false")){
                Files.getVanish().set(path+".affectSpawning","true");
            }
        } catch(NullPointerException e) {
            if (p.isVisibleByDefault()) {
                Files.getVanish().set(path + ".affectSpawning", "false");
            } else {
                Files.getVanish().set(path + ".affectSpawning", "true");
            }
        }

        Files.saveVanish();

        status = Files.getVanish().getString(path+".affectSpawning");

        switch (status){
            case "true":
                p.setAffectsSpawning(true);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aAffect spawning enabled"));}
                break;
            case "false":
                p.setAffectsSpawning(false);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cAffect spawning disabled"));}

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

    public static void silent(Player p,boolean announce){
        String path = p.getUniqueId().toString();
        String status = Files.getVanish().getString(path+".silent");

        try {
            if(status.equalsIgnoreCase("true")){
                Files.getVanish().set(path+".silent","false");
            }else if(status.equalsIgnoreCase("false")){
                Files.getVanish().set(path+".silent","true");
            }
        } catch(NullPointerException e) {
            if (p.isVisibleByDefault()) {
                Files.getVanish().set(path + ".silent", "false");
            } else {
                Files.getVanish().set(path + ".silent", "true");
            }
        }

        Files.saveVanish();

        status = Files.getVanish().getString(path+".silent");

        switch (status){
            case "true":
                p.setSilent(true);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&aSilent enabled"));}
                break;
            case "false":
                p.setSilent(false);
                if(announce){p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cSilent disabled"));}
                break;
        }

    }

    public static void hide(Player p){
        for(Player p2: Bukkit.getOnlinePlayers()){
            p2.hidePlayer(StaffManager.getInstance(),p);
        }
    }
}
