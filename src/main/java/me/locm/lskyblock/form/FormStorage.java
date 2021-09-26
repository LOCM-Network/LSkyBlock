package me.locm.lskyblock.form;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import me.locm.lskyblock.api.SkyBlockAPI;
import me.locm.lskyblock.provider.SQLiteProvider;
import me.locm.lskyblock.skyblock.Island;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.Input;
import ru.contentforge.formconstructor.form.element.Toggle;

import java.util.List;

public class FormStorage {

    public static SimpleForm getStartForm(){
        SimpleForm form = new SimpleForm(TextFormat.colorize("SKYBLOCK"));
        form.addButton(TextFormat.colorize("dich chuyen"), (p, button) -> sendTeleportForm(p));
        form.addButton(TextFormat.colorize("Quan ly dao"), (p, button) -> sendManagerForm(p));
        return form;
    }

    public static void sendTeleportForm(Player player){
        SimpleForm form = new SimpleForm(TextFormat.colorize("teleport"));
        form.addButton(TextFormat.colorize("Dich chuyen den dao cua ban"), (p, button) -> {
            Position spawn = SkyBlockAPI.getIsland(p).getSpawn();
            if(spawn != null){
                p.teleport(spawn);
                p.sendActionBar(TextFormat.colorize("Dang dich chuyen den dao"));
                return;
            }
            p.sendActionBar("Da xay ra loi");
        });
        form.addButton(TextFormat.colorize("Dich chuyen toi dao khac"), (p, button) -> {
            CustomForm cform = new CustomForm(TextFormat.colorize("TELEPORT form"));
            cform.addElement("name", new Input("Nhap ten:"));
            cform.setHandler((cp, response) -> {
                String name = response.getInput("name").getValue();
                if(!name.equals("")){
                    Island island = new SQLiteProvider().getIsland(name);
                    if(island != null){
                        p.teleport(island.getSpawn());
                        p.sendActionBar(TextFormat.colorize(
                                "Dich chuyen toi dao cua " + island.getOwner() + "\n" +
                                            "Trang thai PVP: " + (island.getPvp() ? "Bat" : "Tat")
                        ));
                    }
                }else getStartForm().send(cp);
            });
        });
        form.send(player);
    }

    public static void sendManagerForm(Player player){
        Island island = new SQLiteProvider().getIsland(player.getName());
        SimpleForm form = new SimpleForm(TextFormat.colorize("quan ly dao"));
        form.addButton(TextFormat.colorize("Thong tin dao"), (p, button) -> sendInfoForm(p));
        form.addButton(TextFormat.colorize("Xoa/Them ban be"), (p, button) -> sendMemberForm(p));
        form.addButton((island.getPvp() ? "&a" : "&c") + TextFormat.colorize("PVP"), (p, button) -> {
            island.setPvp(!island.getPvp());
            sendManagerForm(p);
        });
        form.send(player);
    }

    public static void sendInfoForm(Player player){
        Island island = new SQLiteProvider().getIsland(player.getName());
        CustomForm form = new CustomForm(TextFormat.colorize("Information"));
        form.addElement(TextFormat.colorize("ID: " + island.getId()));
        form.addElement(TextFormat.colorize("Dao cua nguoi choi: " + player.getName()));
        List<String> members = island.getMembers();
        form.addElement(TextFormat.colorize("Thanh vien: " + members.toString()));
        form.addElement(TextFormat.colorize("Trang thai pvp: " + (island.getPvp() ? "bat" : "tat")));
    }

    public static void sendMemberForm(Player player){
        CustomForm form = new CustomForm(TextFormat.colorize("Members"));
        form.addElement("add", new Toggle(TextFormat.colorize("Xoa/Them"), true));
        form.addElement("target", new Input(TextFormat.colorize("Nhap ten")));
        form.setNoneHandler(FormStorage::sendManagerForm);
        form.setHandler((p, respone) -> {
            boolean add = respone.getToggle("add").getValue();
            String target = respone.getInput("target").getValue();
            Island island = new SQLiteProvider().getIsland(player.getName());
            if(!target.equals("")){
                Player targetp = Server.getInstance().getPlayerExact(target);
                if(add){
                    if(targetp != null){
                        island.addMember(targetp);
                        sendNotice(player, "da them " + targetp.getName() + " vao dao");
                    }
                }else{
                    if(targetp != null){
                        island.removeMember(targetp);
                        sendNotice(player, "da xoa " + targetp.getName() + " ra khoi dao");
                    }
                }
            }else getStartForm().send(player);
        });
        form.setNoneHandler((p) -> getStartForm().send(player));
        form.send(player);
    }

    public static void sendNotice(Player player, String note){
        CustomForm form = new CustomForm(TextFormat.colorize("NOTICE"));
        form.addElement(TextFormat.colorize(note));
        form.setHandler((p, rsp) -> getStartForm().send(player));
        form.send(player);
    }
}
