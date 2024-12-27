package me.betun.staffmanager.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MessageUtils {

    public static Component coloredMessage(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }
}
