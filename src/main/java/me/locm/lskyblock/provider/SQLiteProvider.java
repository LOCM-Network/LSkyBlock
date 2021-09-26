package me.locm.lskyblock.provider;

import me.locm.lskyblock.LSkyblock;
import me.locm.lskyblock.skyblock.Island;
import me.locm.lskyblock.utils.Caculator;
import me.locm.lskyblock.utils.Utils;
import org.sqlite.util.StringUtils;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteProvider {

    public SQLiteProvider(){
        try{
            create();
        }catch (SQLException ignored){}
    }

    public Island getIsland(String player) throws SQLException {
        Map<String, String> data = selectAllFormPlayer(player);
        Island island =  new Island(player);
        List<String> members = getMembers(island);
        assert data != null;
        int id = Integer.parseInt(data.get("id"));
        island.setId(id);
        island.setMembers(members);
        island.setSpawn(Caculator.getDefaultSpawn(id));
        return island;
    }

    public boolean hasIsland(String player){
        boolean has = false;
        try{
            Map<String, String> data = selectAllFormPlayer(player);
            assert data != null;
            has = (data.get("id") == null);
        }catch (SQLException ignored){}
        return has;
    }

    public List<Island> getIslands(){
        List<Island> islands = new ArrayList<>();
        try{
            islands = selectAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return islands;
    }

    public List<String> getMembers(Island island) throws SQLException {
        List<String> members = new ArrayList<>();
        String memberstr = get(island.getOwner(), "members");
        assert memberstr != null;
        return Utils.stringToList(memberstr);
    }

    public void addMember(Island island, String member){
        List<String> mems = island.getMembers();
        mems.add(member);
        updateIsland(island);
    }

    public void removeMember(Island island, String member){
        List<String> mems = island.getMembers();
        mems.remove(member);
        updateIsland(island);
    }

    public void createIsland(String player, int id) throws SQLException {
        String query = "insert into lskyblock(id, player, members, pvp)" +
                        " values ('"+id+"', '"+player+"', '', 0)";
        executeUpdate(query);
    }

    public static Connection connectToSQLite(){
        return connectToSQLite("databases.db");
    }

    public void updateIsland(Island island){
        int id = island.getId();
        String owner = island.getOwner();
        List<String> members = island.getMembers();
        int pvp = island.getPvp() ? 1 : 0;
        String query =
                "insert or replace lskyblock(id, player, members, pvpmode)" +
                        " values ('"+id+"', '"+owner+"', '" + StringUtils.join(members, ",") + "', '"+ pvp +"')";
        try{
            executeUpdate(query);
        }catch (SQLException ignored){}
    }

    public static List<Island> selectAll() throws SQLException{
        String query = "select * from lskyblock";
        List<Island> islands = new ArrayList<>();
        Connection connection = connectToSQLite();
        if (connection == null) return islands;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            Island island = new Island(resultSet.getString("player"));
            island.setId(Integer.parseInt(resultSet.getString("id")));
            island.setMembers(Utils.stringToList(resultSet.getString("mebers")));
            island.setPvp(resultSet.getString("pvp").equals("1"));
        }
        return islands;
    }

    public static Connection connectToSQLite(String filename){
        File file = new File(LSkyblock.getInstance().getDataFolder() + File.separator + filename);
        return DbLib.getSQLiteConnection(file);
    }

    public static Map<String, String> selectAllFormPlayer(String player) throws SQLException {
        String query = "select * from lskyblock where player='" + player + "'";
        Map<String, String> list = new HashMap<>();
        Connection connection = connectToSQLite();
        if (connection == null) return list;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            list.put("id", resultSet.getString("id"));
            list.put("player", player);
            list.put("members", resultSet.getString("members"));
            list.put("pvpmode", resultSet.getString("pvp"));
        }
        connection.close();
        return list;
    }

    public static String get(String player, String component) throws SQLException {
        String query = "select * from lskyblock where player='" + player + "'";
        Connection connection = connectToSQLite();
        if (connection == null) return "";
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        connection.close();
        return resultSet.getString(component);
    }

    public static void executeUpdate(String query) throws SQLException {
        Connection connection = connectToSQLite();
        if (connection == null) return;
        connection.createStatement().executeUpdate(query);
        connection.close();
    }

    public static void create() throws SQLException {
        try{
            String query = "create table if not exists lskyblock (id int, name varchar(20), members text, pvp varchar(1))";
            executeUpdate(query);
        }catch(SQLException ignored){}
    }

}
