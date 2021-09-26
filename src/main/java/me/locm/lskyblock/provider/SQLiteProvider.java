package me.locm.lskyblock.provider;

import cn.nukkit.Player;
import lombok.Getter;
import me.locm.lskyblock.LSkyblock;
import me.locm.lskyblock.skyblock.Island;
import me.locm.lskyblock.utils.Utils;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteProvider {

    public SQLiteProvider(){

    }

    public Island getIsland(String player){
        Island island =  new Island(player);
        String spawn = ""; //getSpawn method
        List<String> members = new ArrayList<String>(); //getMembers method
        boolean pvp = true; //getPvp method
        int id = 1; //getId Method
        island.setId(id);
        island.setMembers(members);
        island.setPvp(pvp);
        island.setSpawn(Utils.stringToPos(spawn));
        return island;
    }

    public void updateIsland(Island island){
        int id = island.getId();
        String owner = island.getOwner();
        List<String> members = island.getMembers();
        boolean pvp = island.getPvp();
        //TODO: update
    }

    public boolean hasIsland(String player){
        return false;
    }

    public List<Island> getIslands(){
        List<Island> islands = new ArrayList<Island>();
        //TODO: query
        return islands;
    }

    public void createIsland(){
        //TODO: query
        //id: number
        //player: player name
        //members: players
        //pvp-mod: 0(disable <default> ), 1(enable)
    }

    public static Connection connectToSQLite() throws SQLException {
        return connectToSQLite("databases.db");
    }

    public static Connection connectToSQLite(String filename) throws SQLException {
        File file = new File(LSkyblock.getInstance().getDataFolder() + File.separator + filename);
        Connection connection = DbLib.getSQLiteConnection(file);
        return connection;
    }

    public static boolean executeUpdate(String query) throws SQLException {
        Connection connection = connectToSQLite();
        if (connection == null) return false;
        connection.createStatement().executeUpdate(query);
        if (connection != null) connection.close();
        return true;
    }

    public static void create() throws SQLException {
        try{
            String query = "create table if not exists lskyblock (id int, name varchar(20), members text, pvp int)";
            executeUpdate(query);
        }catch(SQLException ecp){
            System.out.println(ecp);
        }
    }

}
