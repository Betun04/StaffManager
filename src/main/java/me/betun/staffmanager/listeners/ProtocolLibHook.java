package me.betun.staffmanager.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProtocolLibHook {

    public static void register(){
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        final Cache<UUID, Long> lastMessageTime = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES) // Elimina entradas inactivas después de 5 minutos
                .build();
        long chatCooldown = 5000; // 5 segundos

        manager.addPacketListener(new PacketAdapter(StaffManager.getInstance(), PacketType.Play.Client.CHAT) {

            @Override
            public void onPacketReceiving(PacketEvent e) {

                List<String> muted = Files.getChatFile().getStringList("muted");
                List<String> banedWords = Files.getChatFile().getStringList("banedWords");
                List<String> staffsChat = Files.getChatFile().getStringList("staffsChat");

                boolean paused = Files.getChatFile().getBoolean("paused");
                boolean slowed = Files.getChatFile().getBoolean("slowed");

                String message = e.getPacket().getStrings().read(0);

                if(paused && !e.getPlayer().isOp()){
                    e.setCancelled(true);
                }
                else if(muted.contains(e.getPlayer().getUniqueId().toString()) && !e.getPlayer().isOp()) {
                    e.getPlayer().sendMessage(MessageUtils.coloredMessage("&cFuiste silenciado, no podes hablar."));
                    e.setCancelled(true);
                }
                else if(slowed && !e.getPlayer().isOp()){

                    UUID playerId = e.getPlayer().getUniqueId();
                    long currentTime = System.currentTimeMillis();

                    Long lastTime = lastMessageTime.getIfPresent(playerId);

                    if (lastTime != null && currentTime - lastTime < chatCooldown) {
                        // Cooldown activo
                        e.setCancelled(true);
                        long timeLeft = (chatCooldown - (currentTime - lastTime)) / 1000;
                        e.getPlayer().sendMessage("Por favor, espera " + timeLeft + " segundos antes de enviar otro mensaje.");
                        return;
                    }

                    lastMessageTime.put(playerId, currentTime);
                }
                else if(e.getPlayer().isOp() && staffsChat.contains(e.getPlayer().getUniqueId().toString())){

                    Component staffChatPrefix = MessageUtils.coloredMessage("&d[StaffChat]&r ");

                    for(Player p: Bukkit.getOnlinePlayers()){
                        e.setCancelled(true);
                        if(p.isOp()){

                            // Crear varios componentes de texto
                            Component pName = MessageUtils.coloredMessage("&6"+e.getPlayer().getName()+"&r: ");
                            Component msg = MessageUtils.coloredMessage("&e"+message);

                            // Concatenar los componentes
                            Component concatenated = staffChatPrefix.append(pName).append(msg);

                            p.sendMessage(concatenated);
                        }
                    }

                }

                for(String word: banedWords) {
                    String normalizedMessage = MessageUtils.normalizeLeet(message);
                    if (normalizedMessage.toLowerCase().contains(word) && !e.getPlayer().isOp()) {
                        String censored = "*".repeat(word.length());

                        // Modificar el mensaje
                        String modifiedMessage = replaceWord(normalizedMessage,word,censored);
                        e.getPacket().getStrings().write(0, modifiedMessage);
                    }
                }
            }

        });
    }

    public static String replaceWord(String message, String targetWord, String replacement) {
        // Crear un patrón que busque la palabra sin importar mayúsculas/minúsculas
        String pattern = "\\b" + targetWord + "\\b"; // \b asegura que coincida solo con palabras completas
        return message.replaceAll("(?i)" + pattern, replacement);
    }

}
