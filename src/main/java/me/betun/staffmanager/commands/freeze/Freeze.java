package me.betun.staffmanager.commands.freeze;

import me.betun.staffmanager.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Freeze implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player && args.length == 1){

            List<String> frozenPlayers = Files.getFreeze().getStringList("freezed");
            Player p = Bukkit.getPlayer(args[0]);

            if(frozenPlayers.contains(p.getUniqueId().toString())){

                //Freeze config
                frozenPlayers.remove(p.getUniqueId().toString());
                Files.getFreeze().set("freezed",frozenPlayers);
                Files.saveFreeze();

                //Remove effects
                p.removePotionEffect(PotionEffectType.SLOWNESS);

                //Un-make invulnerable
                p.setInvulnerable(false);
                //Add Gravity
                p.setGravity(true);

            }else{

                //Freeze config
                frozenPlayers.add(p.getUniqueId().toString());
                Files.getFreeze().set("freezed",frozenPlayers);
                Files.saveFreeze();

                //Add slow effect
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,PotionEffect.INFINITE_DURATION,5,false,false));

                //Make invulnerable
                p.setInvulnerable(true);
                //Gravity
                p.setGravity(false);

            }

        }

        return true;
    }
}