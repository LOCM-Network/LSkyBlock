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

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FormStorage {

    public static SimpleForm getStartForm(){
        SimpleForm form = new SimpleForm(TextFormat.colorize("§e§lLOCM §aSKYBLOCK"));
        form.addButton(TextFormat.colorize("§l§8DỊCH CHUYỂN VỀ ĐẢO"), (p, button) -> sendTeleportForm(p));
        form.addButton(TextFormat.colorize("§l§8QUẢN LÍ ĐẢO"), (p, button) -> {
            try {
                sendManagerForm(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return form;
    }

    public static void sendTeleportForm(Player player){
        SimpleForm form = new SimpleForm(TextFormat.colorize("§e§lLOCM §aSKYBLOCK"));
        form.addButton(TextFormat.colorize("§l§8DỊCH CHUYỂN VỀ ĐẢO CỦA BẠN"), (p, button) -> {
            Position spawn = null;
            try {
                spawn = Objects.requireNonNull(SkyBlockAPI.getIsland(p)).getSpawn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(spawn != null){
                p.teleport(spawn);
                p.sendActionBar(TextFormat.colorize("§eĐang dịch chuyển đến đảo..."));
                return;
            }
            p.sendActionBar("§cĐã xảy ra lỗi...");
        });
        form.addButton(TextFormat.colorize("§l§8DỊCH CHUYỂN ĐẾN ĐẢO KHÁC"), (p, button) -> {
            CustomForm cform = new CustomForm(TextFormat.colorize("§e§lLOCM §aSKYBLOCK"));
            cform.addElement("name", new Input("§l§8NHẬP TÊN:"));
            cform.setHandler((cp, response) -> {
                String name = response.getInput("name").getValue();
                if(!name.equals("")){
                    Island island = null;
                    try {
                        island = new SQLiteProvider().getIsland(name);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(island != null){
                        p.teleport(island.getSpawn());
                        p.sendActionBar(TextFormat.colorize(
                                "§eĐang dịch chuyển đến đảo của " + island.getOwner() + "\n" +
                                            "§cTrạng tháii PVP: " + (island.getPvp() ? "§aBật" : "§cTắt")
                        ));
                    }else p.sendMessage(TextFormat.colorize("§l§cNgười chơi §e" + name + " §ckhông tồn tại!!"));
                }else getStartForm().send(cp);
            });
        });
        form.send(player);
    }

    public static void sendManagerForm(Player player) throws SQLException {
        Island island = new SQLiteProvider().getIsland(player.getName());
        SimpleForm form = new SimpleForm(TextFormat.colorize("§e§lLOCM §aSKYBLOCK"));
        form.addButton(TextFormat.colorize("§l§8THÔNG TIN ĐẢO"), (p, button) -> {
            try {
                sendInfoForm(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        form.addButton(TextFormat.colorize("§l§8THÊM/XÓA BẠN"), (p, button) -> sendMemberForm(p));
        form.addButton((island.getPvp() ? "&a" : "&c") + TextFormat.colorize("PVP"), (p, button) -> {
            island.setPvp(!island.getPvp());
            try {
                sendManagerForm(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        form.send(player);
    }

    public static void sendInfoForm(Player player) throws SQLException {
        Island island = new SQLiteProvider().getIsland(player.getName());
        CustomForm form = new CustomForm(TextFormat.colorize("§e§lLOCM §aSKYBLOCK"));
        form.addElement(TextFormat.colorize("§l§8ID: §f" + island.getId()));
        form.addElement(TextFormat.colorize("§l§8ĐẢO CỦA NGƯỜI CHƠI: §f" + player.getName()));
        List<String> members = island.getMembers();
        form.addElement(TextFormat.colorize("§l§8THÀNH VIÊN: " + members.toString()));
        form.addElement(TextFormat.colorize("§l§8TRẠNG THÁI PVP: " + (island.getPvp() "§aBật" : "§cTắt")));
    }

    public static void sendMemberForm(Player player){
        CustomForm form = new CustomForm(TextFormat.colorize("§e§lLOCM §aSKYBLOCK"));
        form.addElement("add", new Toggle(TextFormat.colorize("§l§8THÊM/XÓA BẠN"), true));
        form.addElement("target", new Input(TextFormat.colorize("§l§8NHẬP TÊN")));
        form.setHandler((p, respone) -> {
            boolean add = respone.getToggle("add").getValue();
            String target = respone.getInput("target").getValue();
            Island island = null;
            try {
                island = new SQLiteProvider().getIsland(player.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(!target.equals("")){
                Player targetp = Server.getInstance().getPlayerExact(target);
                if(add){
                    if(targetp != null){
                        assert island != null;
                        island.addMember(targetp);
                        sendNotice(player, "§eĐã thêm §a§l" + targetp.getName() + "§r§e vào đảo thành công!");
                    }
                }else{
                    if(targetp != null){
                        assert island != null;
                        island.removeMember(targetp);
                        sendNotice(player, "§cĐã xóa §a§l" + targetp.getName() + "§r§c ra khỏi đảo thành công!");
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
