package me.betun.staffmanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class Files {

    private static File fileInvs;
    private static File fileVanish;
    private static File fileFreeze;
    private static FileConfiguration customFileInvs;
    private static FileConfiguration customFileVanish;
    private static FileConfiguration customFileFreeze;

    public static void setup(){

        fileInvs = new File(Bukkit.getServer().getPluginManager().getPlugin("StaffManager").getDataFolder(), "inventories.yml");
        fileVanish = new File(Bukkit.getServer().getPluginManager().getPlugin("StaffManager").getDataFolder(),"vanish.yml");
        fileFreeze = new File(Bukkit.getServer().getPluginManager().getPlugin("StaffManager").getDataFolder(),"freeze.yml");

        if(!fileInvs.exists()){
            try{
                fileInvs.createNewFile();
            }catch(IOException e){
                //owww
            }
        }

        if(!fileVanish.exists()){
            try{
                fileVanish.createNewFile();
            }catch(IOException e){
                //owww
            }
        }

        if(!fileFreeze.exists()){
            try{
                fileFreeze.createNewFile();
            }catch(IOException e){
                //owww
            }
        }

        customFileInvs = YamlConfiguration.loadConfiguration(fileInvs);
        customFileVanish = YamlConfiguration.loadConfiguration(fileVanish);
        customFileFreeze = YamlConfiguration.loadConfiguration(fileFreeze);

    }

    public static FileConfiguration getInvs(){
        return customFileInvs;
    }

    public static FileConfiguration getVanish(){
        return customFileVanish;
    }

    public static FileConfiguration getFreeze(){
        return customFileFreeze;
    }

    public static void saveInvs(){
        try{
            customFileInvs.save(fileInvs);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void saveVanish(){
        try{
            customFileVanish.save(fileVanish);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void saveFreeze(){
        try{
            customFileFreeze.save(fileFreeze);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        customFileInvs = YamlConfiguration.loadConfiguration(fileInvs);
        customFileVanish = YamlConfiguration.loadConfiguration(fileVanish);
    }

}
