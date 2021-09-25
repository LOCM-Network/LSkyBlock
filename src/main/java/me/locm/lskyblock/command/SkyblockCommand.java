package me.locm.lskyblock.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.locm.lskyblock.form.FormStorage;

public class SkyblockCommand extends Command {

    public SkyblockCommand(){
        super("skyblock", "skyblock command");
    }

    public boolean execute(CommandSender sender, String label, String[] args){
        if(sender instanceof Player){
            FormStorage.getStartForm().send((Player) sender);
        }
        return true;
    }

}
