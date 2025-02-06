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
import java.util.function.BiConsumer;

public class FreezeCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && args.length == 2 && (sender.hasPermission("staffmanager.freeze") || sender.hasPermission("staffmanager.all"))){

            List<String> frozenPlayers = Files.getFreeze().getStringList("freezed");
            Player p = Bukkit.getPlayer(args[1]);

            if(p!= null && p.isOnline() && !p.isOp()){
                if(frozenPlayers.contains(p.getUniqueId().toString())){

                    //Freeze config
                    frozenPlayers.remove(p.getUniqueId().toString());
                    Files.getFreeze().set("freezed",frozenPlayers);
                    Files.saveFreeze();

                    //Remove effects
                    p.removePotionEffect(PotionEffectType.WEAKNESS);

                    //Un-make invulnerable
                    p.setInvulnerable(false);
                    //Add Gravity
                    p.setGravity(true);

                    MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"The player &b"+p.getName() +"&r is no longer&b frozen");
                    //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The player &b"+p.getName() +"&r is no longer&b frozen"));
                    MessageUtils.sendMessage(p,StaffManager.prefix+"You have been&b unfrozen");
                    //p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"You have been&b unfrozen"));

                }else{

                    //Freeze config
                    frozenPlayers.add(p.getUniqueId().toString());
                    Files.getFreeze().set("freezed",frozenPlayers);
                    Files.saveFreeze();

                    //Add effects
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,PotionEffect.INFINITE_DURATION,5,false,false));

                    //Make invulnerable
                    p.setInvulnerable(true);
                    //Gravity
                    p.setGravity(false);

                    MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"The player &b"+p.getName() +"&r is&b frozen");
                    //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"The player &b"+p.getName() +"&r is&b frozen"));

                    MessageUtils.sendMessage(p,StaffManager.prefix+"You have been&b frozen");
                    //p.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"You have been&b frozen"));
                }
            }else{
                MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&cPlayer isn't online or is Op");
                //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cPlayer isn't online or is Op"));
            }
        }else{
            MessageUtils.sendMessage((Player) sender,StaffManager.prefix+"&cYou don't have permission to use this command.");
            //sender.sendMessage(MessageUtils.coloredMessage(StaffManager.prefix+"&cYou don't have permission to use this command."));
        }
    }
}
