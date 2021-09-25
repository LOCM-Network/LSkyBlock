package me.locm.lskyblock.form;

import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import me.locm.lskyblock.api.SkyBlockAPI;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.SimpleForm;

public class FormStorage {

    public static SimpleForm getStartForm(){
        SimpleForm form = new SimpleForm(TextFormat.colorize("SKYBLOCK"));
        form.addButton(TextFormat.colorize("Join"), (p, button) -> {
            Position spawn = SkyBlockAPI.getIsland(p).getSpawn();
            if(spawn != null){
                p.teleport(spawn);
                p.sendActionBar(TextFormat.colorize("Dang dich chuyen den dao"));
                return;
            }
            p.sendActionBar("Da xay ra loi");
        });
        form.addButton(TextFormat.colorize("manager"), (p, button) -> {});
        return form;
    }

}
