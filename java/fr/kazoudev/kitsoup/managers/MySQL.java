package fr.kazoudev.kitsoup.managers;

import cn.nukkit.Player;
import fr.kazoudev.kitsoup.Main;
import fr.kazoudev.kitsoup.SoupPlayer;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MySQL {
    private static Connection con;

    public static Object getConnection(){return con;}

    public static void openConnection() throws Exception{
        if(con != null && !con.isClosed()){
            return;
        }
            String url = "jdbc:mysql://127.0.0.1:3306/Joueurs?useSSL=false";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "exodus", "ExoLesMeilleurs");
    }

    public static void closeConnection() throws Exception {
        if(!con.isClosed()){
            try{
                con.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void createPlayerProfile(Player player) throws Exception{
        openConnection();
        Statement st = con.createStatement();
        String sql = "SELECT UUID FROM Joueurs WHERE UUID = '"+player.getUniqueId().toString()+"';";
        ResultSet rs;
        rs = st.executeQuery(sql);
        if(!rs.next()){
            sql = "INSERT INTO Joueurs(UUID, IP, Rank, Lang, Coins, Killed, Death, autodrop, prefix) VALUES ('"+player.getUniqueId().toString()+"', '"+player.getAddress()+"', 'PLAYER', 'FR', 0, 0, 0, 0, '')";
            st.execute(sql);
        }
        con.close();
    }

    public static String getPlayerIP(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT IP FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getString("IP");
            con.close();
        } catch (Exception s){

        }
        return null;
    }

    public static String getPlayerRank(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT Rank FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getString("Rank");
            con.close();
        } catch (Exception s){

        }
        return null;
    }

    public static String getPlayerLang(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT Lang FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getString("Lang");
            con.close();
        } catch (Exception s){

        }
        return null;
    }

    public static int getPlayerCoins(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT Coins FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getInt("Coins");
            con.close();
        } catch (Exception s){

        }
        return -1;
    }


    public static Integer getPlayerKill(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT Killed FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getInt("Killed");
            con.close();
        } catch (Exception s){

        }
        return -1;
    }

    public static Integer getPlayerDeath(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT Death FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getInt("Death");
            con.close();
        } catch (Exception s){

        }
        return -1;
    }

    public static boolean getAutoDrop(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT autodrop FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getBoolean("autodrop");
            con.close();
        } catch (Exception s){

        }
        return false;
    }

    public static String getPrefix(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT prefix FROM Joueurs WHERE UUID = '"+uuid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getString("prefix");
            con.close();
        } catch (Exception s){

        }
        return null;
    }
    public static List<String> getTopKill(){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM Joueurs ORDER BY Killed DESC";
            ResultSet rs;
            rs = st.executeQuery(sql);
            List<String> top =  new ArrayList<String>() ;
            while (rs.next()){
                top.add(rs.getString("UUID"));
            }
            con.close();
            return top;
        } catch (Exception e){

        }
        return null;
    }

    public static List<String> getOtherAccount(Player p){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM Joueurs WHERE Ip = '"+p.getAddress()+"'";
            ResultSet rs;
            rs = st.executeQuery(sql);
            List<String> acc = new ArrayList<String>();
            while(rs.next()){
                acc.add(rs.getString("UUID"));
            }
        } catch (Exception e){

        }
        return null;
    }

    public static int isPlayer(String uuid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT count(*) AS count FROM Joueurs WHERE UUID = '" + uuid + "';";
            ResultSet rs = st.executeQuery(sql);
            int count=0;
            rs.next();
            count = rs.getInt("count");
            con.close();
            return count;
        }
        catch (Exception e){

        }
        return -1;
    }

    public static void updatePlayerRank(Player player, String rank){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET Rank = '" + rank + "' WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void updatePlayerLang(Player player, String lang){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET lang = '" + lang + "' WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void updatePlayerCoins(Player player, int value){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET Coins = " + value + " WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void updatePlayerKill(Player player, int value){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET Killed = " + value + " WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void updatePlayerDeath(Player player, int value){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET Death = " + value + " WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void updatePlayerAutodrop(Player player, boolean value){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET autodrop = " + value + " WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void updatePlayerPrefix(Player player, String lang){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Joueurs SET prefix = '" + lang + "' WHERE UUID = '" + player.getUniqueId().toString() + "';";
            st.executeUpdate(sql);
            con.close();
        }
        catch (Exception e){

        }
    }

    public static void addKits(String uid, int kitid, int life){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "INSERT INTO user_kits(UUID, kitid, life) VALUES ('"+uid+"', '"+kitid+ "', '"+life+"')";
            st.execute(sql);
            con.close();
        } catch (Exception e){

        }
    }

    public static void remKits(String uid, int kitid){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "DELETE FROM user_kits WHERE UUID = '"+uid+"' && kitid = '"+kitid+"';";
            st.execute(sql);
            con.close();
        } catch (Exception e){

        }
    }

    public static boolean hasKit(String uid, int kitid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT count(*) AS count FROM user_kits WHERE UUID = '" + uid + "' && kitid = '"+ kitid +"';";
            ResultSet rs = st.executeQuery(sql);
            int count =0;
            rs.next();
            count = rs.getInt("count");
            con.close();
            return count == 1;
        } catch (Exception e){

        }
        return false;
    }

    public static boolean getIsLifeKit(String uid, int kitid){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT life FROM user_kits WHERE UUID = '"+uid+"' && kitid = '"+kitid+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) return rs.getBoolean("life");
            con.close();
        } catch (Exception e){

        }
        return false;
    }

    public static int getPlayerKitCount(String uuid) {
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT count(*) AS count FROM user_kit WHERE UUID = '" + uuid + "';";
            ResultSet rs = st.executeQuery(sql);
            int count = 0;
            rs.next();
            count = rs.getInt("count");
            con.close();
            return count;
        } catch (Exception e) {

        }
        return -1;
    }
    public static void banPlayer(String player, String raison, String punisher, int seconds){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT joueur FROM bans WHERE joueur = '" + player.toLowerCase() + "';";
            ResultSet rs;
            rs = st.executeQuery(sql);

            Main.getInstance().getLogger().critical("§c[§eBAN§c] " + player.toLowerCase() + " §e is in Banning");

            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.SECOND, seconds);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Paris")));
            String date = dateFormat.format(cal.getTime());
            Main.getInstance().getLogger().critical("§c[§eBAN§c] " + player.toLowerCase() + " §e is in Banning 2");
            if (!rs.next()) {
                sql = "INSERT INTO bans(joueur, raison, banner, end) VALUES ('" + player.toLowerCase() + "', '" + raison + "', '" + punisher.toLowerCase() + "', '" + date + "')";
                st.execute(sql);
                Main.getInstance().getLogger().critical("§c[§eBAN§c] " + player.toLowerCase() + " §e is Banned");
            } else {
                sql = "UPDATE mute SET raison = '"+raison+"', punisher = '"+punisher.toLowerCase()+"', date = '"+date+"'  WHERE joueur = '"+player.toLowerCase()+"';";
                st.execute(sql);
            }
            con.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void remBan(String player) {
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "DELETE FROM bans WHERE joueur = '"+player.toLowerCase()+"';";
            st.execute(sql);
            con.close();
        } catch (Exception e) {

        }
    }

    public static String getBanEnd(String player){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT end FROM bans WHERE joueur = '" +player.toLowerCase()+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while(rs.next()) return rs.getString("end");
            con.close();
        }
        catch (Exception e){

        }
        return null;
    }

    public static boolean isBanned(String player){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT count(*) AS count FROM bans WHERE joueur = '" + player.toLowerCase() + "';";
            ResultSet rs = st.executeQuery(sql);
            int count=0;
            rs.next();
            count = rs.getInt("count");
            con.close();
            if(count != 0){
                return true;
            }
            return false;
        }
        catch (Exception e){

        }
        return false;
    }

    public static String getBanReason(String player){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT raison FROM bans WHERE joueur = '" +player.toLowerCase()+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while(rs.next()) return rs.getString("raison");
            con.close();
        }
        catch (Exception e){

        }
        return null;
    }

    public static void mutePlayer(String player, String raison, String punisher, Integer seconds){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT joueur FROM mute WHERE joueur = '" + player.toLowerCase() + "';";
            ResultSet rs;
            rs = st.executeQuery(sql);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, seconds);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Paris")));
            String date = dateFormat.format(cal.getTime());

            if (!rs.next()) {
                sql = "INSERT INTO mute(joueur, raison, muter, date) VALUES ('" + player.toLowerCase() + "', '" + raison + "', '" + punisher.toLowerCase() + "', '" + date + "')";
                st.execute(sql);
            } else {
                sql = "UPDATE mute SET raison = '" + raison + "', punisher = '" + punisher.toLowerCase() + "', date = '" + date + "'  WHERE joueur = '" + player.toLowerCase() + "';";
                st.execute(sql);
            }
            con.close();
        } catch (Exception e){

        }
    }

    public static void remMute(String player) {
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "DELETE FROM mute WHERE joueur = '"+player.toLowerCase()+"';";
            st.execute(sql);
            con.close();
        } catch (Exception e) {

        }
    }


    public static boolean isMute(String player){
        try{
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT count(*) AS count FROM mute WHERE joueur = '" + player.toLowerCase() + "';";
            ResultSet rs = st.executeQuery(sql);
            int count=0;
            rs.next();
            count = rs.getInt("count");
            con.close();
            if(count != 0){
                return true;
            }
            return false;
        } catch (Exception e){

        }
        return false;
    }
    public static String getMuteReason(String player){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT raison FROM mute WHERE joueur = '" +player.toLowerCase()+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while(rs.next()) return rs.getString("raison");
            con.close();
        }
        catch (Exception e){

        }
        return null;
    }

    public static String getMuteDate(String player){
        try {
            openConnection();
            Statement st = con.createStatement();
            String sql = "SELECT date FROM mute WHERE joueur = '" +player.toLowerCase()+"';";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while(rs.next()) return rs.getString("date");
            con.close();
        }
        catch (Exception e){

        }
        return null;
    }


    public static void savePlayer(Player player) {
        SoupPlayer p = Main.getSPlayer(player);
        updatePlayerCoins(player, p.getCoins());
        updatePlayerLang(player, p.getLang());
        updatePlayerKill(player, p.getKill());
        updatePlayerDeath(player, p.getDeath());
        updatePlayerAutodrop(player, p.getAutoDrop());
        updatePlayerPrefix(player, p.getPrefix());
    }
}
