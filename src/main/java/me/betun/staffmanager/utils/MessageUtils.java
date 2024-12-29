package me.betun.staffmanager.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.HashMap;
import java.util.Map;

public class MessageUtils {

    public static Component coloredMessage(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    private static final Map<Character, Character> leetMap = new HashMap<>();

    static {
        leetMap.put('4', 'a');
        leetMap.put('3', 'e');
        leetMap.put('1', 'l');
        leetMap.put('0', 'o');
        leetMap.put('7', 't');
        leetMap.put('5', 's');
        leetMap.put('@', 'a');
        leetMap.put('$', 's');
    }

    public static String normalizeLeet(String message) {
        StringBuilder normalized = new StringBuilder();
        for (char c : message.toLowerCase().toCharArray()) {
            normalized.append(leetMap.getOrDefault(c, c)); // Reemplaza si está en el mapa, o deja el carácter original
        }
        return normalized.toString();
    }

    public static String replaceWord(String message, String targetWord, String replacement) {
        // Crear un patrón que busque la palabra sin importar mayúsculas/minúsculas
        String pattern = "\\b" + targetWord + "\\b"; // \b asegura que coincida solo con palabras completas
        return message.replaceAll("(?i)" + pattern, replacement);
    }
}
