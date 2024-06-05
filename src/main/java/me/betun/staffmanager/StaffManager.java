package me.betun.staffmanager;

import me.betun.staffmanager.commands.SaveInventory;
import me.betun.staffmanager.commands.SaveInventoryTabCompleter;
import me.betun.staffmanager.commands.Vanish;
import me.betun.staffmanager.commands.VanishTabCompleter;
import me.betun.staffmanager.utils.Files;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffManager extends JavaPlugin {

    private static StaffManager instance;
    public static String prefix = "&l&7[&3StaffManager&7]&r ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Files.setup();
        Files.getInvs().options().copyDefaults(true);
        Files.getVanish().options().copyDefaults(true);
        Files.saveInvs();
        Files.saveVanish();

        registerCommands();
    }

    public void registerListeners(){
        //getServer().getPluginManager().registerEvents(new ShootListener(), this);
    }

    public void registerCommands(){
        getCommand("saveInv").setExecutor(new SaveInventory());
        getCommand("saveInv").setTabCompleter(new SaveInventoryTabCompleter());

        getCommand("vanish").setExecutor(new Vanish());
        getCommand("vanish").setTabCompleter(new VanishTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static StaffManager getInstance() {
        return instance;
    }
}
