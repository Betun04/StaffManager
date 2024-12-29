package me.betun.staffmanager.commands.inventory;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InvSeeCommand implements SubCommand {

    private static final Map<UUID, UUID> adminToTargetMap = new HashMap<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player admin && admin.isOp()) {

            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]); // Busca al jugador objetivo

                if (target != null && target.isOnline()) {
                    openPlayerFullInventory(admin, target); // Llama al nuevo metodo
                    adminToTargetMap.put(admin.getUniqueId(),target.getUniqueId());
                }
            }
        } else {
            sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cThis command can only be executed by players."));
        }
    }

    public static void openPlayerFullInventory(Player admin, Player target) {
        if (target != null && target.isOnline()) {

            // Crea un inventario con 45 slots (36 para el inventario + 4 para la armadura + 5 adicionales)
            Inventory fullInventory = Bukkit.createInventory(null, 54, "Inventory of " + target.getName());

            //Poner critales separadores
            ItemStack panel = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta panelMeta = panel.getItemMeta();
            panelMeta.displayName(Component.text(""));
            panelMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_ADDITIONAL_TOOLTIP,ItemFlag.HIDE_ATTRIBUTES);
            panelMeta.setCustomModelData(1996);
            panel.setItemMeta(panelMeta);

            for(int i=36;i<=53;i++){
                List<Integer> avoids = List.of(45,46,47,48,50);

                if(!avoids.contains(i)){
                    fullInventory.setItem(i, panel);
                }
            }

            // Copia el inventario principal del jugador
            ItemStack[] mainContents = target.getInventory().getContents();
            for (int i = 0; i < 36; i++) {
                fullInventory.setItem(i, mainContents[i]);
            }

            // Copia las ranuras de armadura
            ItemStack[] armorContents = target.getInventory().getArmorContents();
            for (int i = 0; i < armorContents.length; i++) {
                // Las ranuras de armadura estarán al final del inventario
                fullInventory.setItem(45 + i, armorContents[i]);
            }

            // Copia la ranura de la mano secundaria (off-hand)
            ItemStack offHand = target.getInventory().getItemInOffHand();
            fullInventory.setItem(50, offHand);

            // Abre el inventario al administrador
            admin.openInventory(fullInventory);

        }
    }

    public static void removeAdminTarget(Player admin){
        adminToTargetMap.remove(admin.getUniqueId());
    }

    public static boolean playerBeingViewed(Player target){
        return adminToTargetMap.containsValue(target.getUniqueId());
    }

    public static UUID adminViewingInv(Player target){
        for (Map.Entry<UUID, UUID> entry : adminToTargetMap.entrySet()) {
            if(entry.getValue().equals(target.getUniqueId())){
                return entry.getKey();
            }
        }
        return null;
    }
}