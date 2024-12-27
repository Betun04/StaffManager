package me.betun.staffmanager.listeners;

import me.betun.staffmanager.utils.Files;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.Set;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){

        Set<String> VanishedPlayers = Files.getVanish().getKeys(false);
        String VanishState = Files.getVanish().getString(e.getDamager().getUniqueId().toString()+".vanish");

        if(e.getDamager() instanceof Player && VanishedPlayers.contains(e.getDamager().getUniqueId().toString()) && VanishState.equalsIgnoreCase("true")){
            e.setCancelled(true);
        }

    }


    //region Freeze things

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        // Verifica si el jugador está congelado
        if (isPlayerFrozen(p)) {
            // Obtiene las posiciones anteriores y actuales del jugador
            Location from = e.getFrom();
            Location to = e.getTo();

            // Cancelar el movimiento físico, pero permitir la rotación de la cámara
            if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
                e.setCancelled(true); // Cancela el movimiento físico
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (isPlayerFrozen(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if(isPlayerFrozen(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickUp(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player && isPlayerFrozen((Player) e.getEntity())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            // Cancelar la interacción con el inventario
            if (isPlayerFrozen(player)) {
                event.setCancelled(true);
            }
        }
    }

    public boolean isPlayerFrozen(Player p) {

        List<String> frozenPlayers = Files.getFreeze().getStringList("freezed");

        return frozenPlayers.contains(p.getUniqueId().toString());
    }

    //endregion


}
