package me.locm.lskyblock.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class SkyblockCommand extends Command {

    public SkyblockCommand(){
        super("skyblock", "skyblock command");
    }

    public boolean execute(CommandSender sender, String label, String[] args){
        return true;
    }

}
