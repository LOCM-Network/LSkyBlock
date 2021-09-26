package me.locm.lskyblock.utils;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import me.locm.lskyblock.LSkyblock;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Position stringToPos(String string){
        String[] parts = string.split(":");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        Level level = Server.getInstance().getLevelByName(parts[3]);
        return new Position(x, y, z, level);
    }

    public static String posToString(Position pos){
        return pos.x + ":" + pos.y + ":" + pos.z + ":" + pos.getLevel().getName();
    }

    public static List<Item> getIslandItem(){
        List<Item> items = new ArrayList<Item>();
        List<String> stritem = LSkyblock.getInstance().getConfig().getStringList("items");
        for(String string : stritem){
            String[] parts = string.split(":");
            int id = Integer.parseInt(parts[0]);
            int meta = Integer.parseInt(parts[1]);
            int count = Integer.parseInt(parts[2]);
            Item item = Item.get(id, meta, count);
            if(parts.length == 4){
                item.setCustomName(TextFormat.colorize(parts[3]));
            }
            items.add(item);
        }
        return items;
    }
}
