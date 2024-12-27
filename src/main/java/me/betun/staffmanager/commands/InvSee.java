package me.betun.staffmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InvSee implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("invsee")) {
            if (sender instanceof Player) {
                Player admin = (Player) sender;

                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]); // Busca al jugador objetivo

                    if (target != null && target.isOnline()) {
                        openPlayerFullInventory(admin, target); // Llama al nuevo metodo
                    }
                }
            } else {
                sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            }
            return true;
        }
        return false;
    }

    public void openPlayerFullInventory(Player admin, Player target) {
        if (target != null && target.isOnline()) {
            // Crea un inventario con 45 slots (36 para el inventario + 4 para la armadura + 5 adicionales)
            Inventory fullInventory = Bukkit.createInventory(null, 45, "Inventario de " + target.getName());

            // Copia el inventario principal del jugador
            ItemStack[] mainContents = target.getInventory().getContents();
            for (int i = 0; i < mainContents.length; i++) {
                fullInventory.setItem(i, mainContents[i]);
            }

            // Copia las ranuras de armadura
            ItemStack[] armorContents = target.getInventory().getArmorContents();
            for (int i = 0; i < armorContents.length; i++) {
                // Las ranuras de armadura estarán al final del inventario
                fullInventory.setItem(36 + i, armorContents[i]);
            }

            // Copia la ranura de la mano secundaria (off-hand)
            ItemStack offHand = target.getInventory().getItemInOffHand();
            fullInventory.setItem(40, offHand);

            // Abre el inventario al administrador
            admin.openInventory(fullInventory);
        }
    }

}
