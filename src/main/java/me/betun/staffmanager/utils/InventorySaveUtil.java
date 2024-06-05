package me.betun.staffmanager.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class InventorySaveUtil {

    public static String invStr;
    public static String armorStr;

    public static boolean saveInv(Player p){
        //region Inventory
        String encodedObject;

        ItemStack[] inventory = p.getInventory().getContents();
        ItemStack[] armor = p.getInventory().getArmorContents();

        try{
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(inventory);
            os.flush();

            byte[] serializedObject = io.toByteArray();

            encodedObject = Base64.getEncoder().encodeToString(serializedObject);

            invStr = encodedObject;

        }catch (
                IOException ex){
            System.out.println(ex.getCause());
        }
        //endregion

        //region Armor

        try{
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(armor);
            os.flush();

            byte[] serializedObject = io.toByteArray();

            encodedObject = Base64.getEncoder().encodeToString(serializedObject);

            armorStr = encodedObject;

        }catch (IOException ex){
            System.out.println(ex.getCause());
        }
        //endregion

        if(Files.getInvs().get(p.getUniqueId().toString()) == null){
            Files.getInvs().set(p.getUniqueId().toString()+".items",invStr);
            Files.getInvs().set(p.getUniqueId().toString()+".armor",armorStr);
            invStr = "";
            armorStr = "";
            Files.saveInvs();
            p.getInventory().clear();
            return true;
        }else{
            return false;
        }


    }

    public static boolean loadInv(Player p){

        if(Files.getInvs().get(p.getUniqueId().toString()) != null) {
            //region Items
            byte[] serializedObject = Base64.getDecoder().decode(Files.getInvs().getString(p.getUniqueId().toString() + ".items"));

            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            try {
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                ItemStack[] inv = (ItemStack[]) is.readObject();
                p.getInventory().setContents(inv);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //endregion

            //region Armor
            serializedObject = Base64.getDecoder().decode(Files.getInvs().getString(p.getUniqueId().toString() + ".armor"));

            in = new ByteArrayInputStream(serializedObject);
            try {
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                ItemStack[] inv = (ItemStack[]) is.readObject();
                p.getInventory().setArmorContents(inv);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //endregion

            Files.getInvs().set(p.getUniqueId().toString(),null);
            Files.saveInvs();
            return true;
        }else{
            return false;
        }

    }
}
