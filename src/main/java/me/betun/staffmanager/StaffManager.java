package me.betun.staffmanager;

import me.betun.staffmanager.commands.*;
import me.betun.staffmanager.listeners.PlayerListener;
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
        Files.getFreeze().options().copyDefaults(true);
        Files.saveInvs();
        Files.saveVanish();
        Files.saveFreeze();

        registerCommands();
        registerListeners();
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void registerCommands(){
        getCommand("saveInv").setExecutor(new SaveInventory());
        getCommand("saveInv").setTabCompleter(new SaveInventoryTabCompleter());

        getCommand("vanish").setExecutor(new Vanish());
        getCommand("vanish").setTabCompleter(new VanishTabCompleter());

        getCommand("freeze").setExecutor(new Freeze());
        getCommand("freeze").setTabCompleter(new FreezeTabCompleter());

        getCommand("invsee").setExecutor(new InvSee());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Files.saveInvs();
        Files.saveVanish();
        Files.saveFreeze();
    }

    public static StaffManager getInstance() {
        return instance;
    }
}
