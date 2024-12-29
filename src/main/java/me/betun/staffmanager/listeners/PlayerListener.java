package me.betun.staffmanager.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.commands.inventory.InvSeeCommand;
import me.betun.staffmanager.utils.Files;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PlayerListener implements Listener {

    //region Vanish things
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){

        Set<String> VanishedPlayers = Files.getVanish().getKeys(false);
        String VanishState = Files.getVanish().getString(e.getDamager().getUniqueId().toString()+".vanish");

        if(e.getDamager() instanceof Player && VanishedPlayers.contains(e.getDamager().getUniqueId().toString()) && (VanishState != null && VanishState.equalsIgnoreCase("true"))){
            e.setCancelled(true);
        }

    }
    //endregion

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
        if (event.getWhoClicked() instanceof Player player) {

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

    //region InvSee things

    @EventHandler
    public void onInventoryModify(InventoryClickEvent e) {

        List<Integer> avoidSlotsWOffHand = List.of(45,46,47,48,50);
        List<Integer> avoidSlotsNoOffHand = List.of(45,46,47,48);


        // Verificar que el inventario sea parte del sistema InvSee
        if (e.getView().getTitle().contains("Inventory of")) {
            Player admin = (Player) e.getWhoClicked();
            Player target = Bukkit.getPlayer(e.getView().getTitle().replace("Inventory of ",""));

            if (target != null && target.isOnline()) {

                // Verificar el tipo de clic
                if (e.getClickedInventory() == null ||
                   (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE && e.getCurrentItem().getItemMeta().getCustomModelData() == 1996)
                   || e.getClick() == ClickType.NUMBER_KEY || (avoidSlotsNoOffHand.contains(e.getRawSlot()) && e.getClick() == ClickType.RIGHT)){

                    e.setCancelled(true);
                }
                else if ((e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT)) {
                    if(avoidSlotsWOffHand.contains(e.getRawSlot())){
                        e.setCancelled(true);
                    }else{
                        e.setCancelled(true); // Cancelar el Shift + Click por defecto

                        // Obtener el inventario objetivo (del jugador que se está inspeccionando)
                        Inventory clickedInventory = e.getClickedInventory();
                        if (clickedInventory == null) return;

                        int slot = e.getRawSlot(); // El slot que fue clickeado

                        // Si el clic ocurre en el inventario del jugador objetivo
                        if (slot < e.getView().getTopInventory().getSize()) {
                            // Mover ítem al inventario del administrador
                            ItemStack item = e.getCurrentItem();
                            if (item != null && item.getType() != Material.AIR) {
                                admin.getInventory().addItem(item); // Mover al inventario del administrador
                                target.getInventory().setItem(slot, new ItemStack(Material.AIR)); // Remover del inventario del jugador objetivo
                                admin.getOpenInventory().getTopInventory().setItem(slot,new ItemStack(Material.AIR)); //Modificar interfaz
                            }
                        } else {
                            // Si el clic ocurre en el inventario del administrador, mover ítem de vuelta al jugador objetivo
                            ItemStack item = e.getCurrentItem();
                            if (item != null && item.getType() != Material.AIR) {
                                target.getInventory().addItem(item); // Mover al inventario del jugador objetivo
                                admin.getOpenInventory().getTopInventory().addItem(item); //Añadir item a la interfaz
                                e.getClickedInventory().setItem(e.getSlot(), null); // Remover del inventario del administrador
                            }
                        }
                    }
                }
                else if(e.getClick() == ClickType.RIGHT && !avoidSlotsNoOffHand.contains(e.getRawSlot())){

                    // Sincronizar cambios al inventario del jugador objetivo

                    int slot = e.getRawSlot(); // El slot que fue clickeado

                    if (slot < 36) {
                        if(target.getInventory().getItem(slot) == null){
                            target.getInventory().setItem(slot, new ItemStack(e.getCursor().getType(),1));
                        }else{
                            int amount = e.getInventory().getItem(slot).getAmount();
                            target.getInventory().setItem(slot, new ItemStack(e.getCursor().getType(),amount+1));
                        }
                    }else if (slot == 50) {
                        if(admin.getOpenInventory().getTopInventory().getItem(50) == null){
                            target.getInventory().setItemInOffHand(new ItemStack(e.getCursor().getType(),1));
                        }else{
                            int amount = admin.getOpenInventory().getTopInventory().getItem(50).getAmount();
                            target.getInventory().setItemInOffHand(new ItemStack(e.getCursor().getType(),amount+1));
                        }
                    }
                }
                else if(e.getClick() == ClickType.LEFT){
                    // Sincronizar cambios al inventario del jugador objetivo
                    int slot = e.getRawSlot(); // El slot que fue clickeado
                    if (slot < 36) {
                        target.getInventory().setItem(slot, e.getCursor());
                    } else if (slot >= 45 && slot < 49) {
                        ItemStack[] armor = target.getInventory().getArmorContents();
                        armor[slot - 45] = e.getCursor();
                        target.getInventory().setArmorContents(armor);
                    } else if (slot == 50) {
                        target.getInventory().setItemInOffHand(e.getCursor());
                    }
                }
                else if(e.getClick() == ClickType.DOUBLE_CLICK && !avoidSlotsNoOffHand.contains(e.getRawSlot())){
                    Bukkit.getScheduler().runTaskLater(StaffManager.getInstance(), () -> {
                        for(int i=0;i<36;i++){
                            ItemStack item = admin.getOpenInventory().getItem(i);
                            target.getInventory().setItem(i,item);
                        }
                        target.getInventory().setItemInOffHand(admin.getOpenInventory().getItem(50));
                    }, 5L); // 5 ticks de retraso
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){
        if(e.getView().getTitle().contains("Inventory of")){
            Player admin = (Player) e.getWhoClicked();
            Player target = Bukkit.getPlayer(e.getView().getTitle().replace("Inventory of ",""));

            if(target != null && target.isOnline()){

                Bukkit.getScheduler().runTaskLater(StaffManager.getInstance(), () -> {
                    for(int i=0;i<36;i++){
                        ItemStack item = admin.getOpenInventory().getItem(i);
                        target.getInventory().setItem(i,item);
                    }
                    target.getInventory().setItemInOffHand(admin.getOpenInventory().getItem(50));
                }, 5L); // 5 ticks de retraso
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getPlayer().isOp() && e.getView().getTitle().contains("Inventory of")){
            Player admin = (Player) e.getPlayer();
            InvSeeCommand.removeAdminTarget(admin);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(InvSeeCommand.playerBeingViewed(e.getPlayer())){
            UUID adminUUID = InvSeeCommand.adminViewingInv(e.getPlayer());
            if(adminUUID != null && Bukkit.getPlayer(adminUUID).isOnline()){
                Player admin = Bukkit.getPlayer(adminUUID);
                admin.closeInventory();
                InvSeeCommand.removeAdminTarget(Bukkit.getPlayer(adminUUID));
            }
        }
    }

    //endregion

}
