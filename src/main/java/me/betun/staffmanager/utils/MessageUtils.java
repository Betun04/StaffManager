package me.betun.staffmanager.utils;

import org.bukkit.ChatColor;

public class MessageUtils {

    public static String coloredMessage(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
