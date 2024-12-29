package me.betun.staffmanager.commands.freeze;

import me.betun.staffmanager.StaffManager;
import me.betun.staffmanager.interfaces.SubCommand;
import me.betun.staffmanager.utils.Files;
import me.betun.staffmanager.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FreezeCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && args.length == 2){

            List<String> frozenPlayers = Files.getFreeze().getStringList("freezed");
            Player p = Bukkit.getPlayer(args[1]);

            if(p!= null && p.isOnline()){
                if(frozenPlayers.contains(p.getUniqueId().toString())){

                    //Freeze config
                    frozenPlayers.remove(p.getUniqueId().toString());
                    Files.getFreeze().set("freezed",frozenPlayers);
                    Files.saveFreeze();

                    //Remove effects
                    p.removePotionEffect(PotionEffectType.SLOWNESS);
                    p.removePotionEffect(PotionEffectType.WEAKNESS);

                    //Un-make invulnerable
                    p.setInvulnerable(false);
                    //Add Gravity
                    p.setGravity(true);

                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The player &b"+p.getName() +"&r is no longer frozen."));

                }else{

                    //Freeze config
                    frozenPlayers.add(p.getUniqueId().toString());
                    Files.getFreeze().set("freezed",frozenPlayers);
                    Files.saveFreeze();

                    //Add slow effect
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,PotionEffect.INFINITE_DURATION,2,false,false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,PotionEffect.INFINITE_DURATION,5,false,false));

                    //Make invulnerable
                    p.setInvulnerable(true);
                    //Gravity
                    p.setGravity(false);

                    sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The player &b"+p.getName() +"&r is frozen."));
                }
            }
        }
    }
}
