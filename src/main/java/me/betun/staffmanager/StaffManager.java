package me.betun.staffmanager;

import me.betun.staffmanager.commands.MainCommand;
import me.betun.staffmanager.commands.MainCommandTabCompleter;
import me.betun.staffmanager.listeners.PlayerListener;
import me.betun.staffmanager.listeners.ProtocolLibHook;
import me.betun.staffmanager.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffManager extends JavaPlugin {

    private static StaffManager instance;
    public static String prefix = "&l&7[&3StaffManager&7]&r ";
    public static int version;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        version = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].replace(".",""));
        //region Files setup

        Files.setup();
        Files.getInvs().options().copyDefaults(true);
        Files.getVanish().options().copyDefaults(true);
        Files.getFreeze().options().copyDefaults(true);
        Files.getChatFile().options().copyDefaults(true);
        Files.saveInvs();
        Files.saveVanish();
        Files.saveFreeze();
        Files.saveChatFile();

        saveDefaultConfig();
        //endregion

        registerCommands();
        registerListeners();

        //Check ProtocolLib
        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null){
            ProtocolLibHook.register();
        }
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void registerCommands(){

        getCommand("staffmanager").setExecutor(new MainCommand());
        getCommand("staffManager").setTabCompleter(new MainCommandTabCompleter());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Files.saveInvs();
        Files.saveVanish();
        Files.saveFreeze();
        Files.saveChatFile();
    }

    public static StaffManager getInstance() {
        return instance;
    }
}
