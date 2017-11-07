package MySQLConnection;

import model.userModel;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySQLDB {
    private Connection connection;
    private String username;
    private String password;
    private String host;
    private String db_name;

    public MySQLDB(){
        this.username = "root";
        this.password = "qweert123456";
        this.host = "jdbc:mysql://localhost:3306/";
        this.db_name = "tweet-schema";
        connectDB();
    }
    public void connectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.host + this.db_name, this.username, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }

    }



    public List<String> getGamestitles(){
        //Set<String> titles = new HashSet<>();
        List<String> titles = new ArrayList<>();
        String query = "SELECT nombre FROM juegos";
        try{
            Statement st = this.connection.createStatement();
            ResultSet resultset = st.executeQuery(query);
            while(resultset.next()){
                titles.add(resultset.getNString("nombre"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return titles;

    }


    public void setTotalTweets(){
        String query = "SELECT id FROM juegos";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ResultSet re = ps.executeQuery();
            String query_count = "SELECT COUNT(*) as count FROM tweet where id_juego = ?";
            String query_act = "UPDATE resume SET total_tweets = ? WHERE id_juego = ?";
            while(re.next())
            {
                int id = re.getInt("id");
                //se cuenta la cantidad de tweets asociados
                PreparedStatement ps_c = this.connection.prepareStatement(query_count);
                ps_c.setInt(1,id);
                ResultSet re_count = ps_c.executeQuery();
                int cont = 0;
                while(re_count.next()) {
                    cont += re_count.getInt("count");
                }

                //se actualiza el campo total_tweets de la tabla resumen
                PreparedStatement ps_a = this.connection.prepareStatement(query_act);
                ps_a.setInt(1,cont);
                ps_a.setInt(2,id);
                ps_a.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addDateUpdate(){
        String query = "INSERT INTO  actualizacion (fecha) VALUES (?)";

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, currentTime);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int addTweet(long id_tweet, String gameName){

        String query_id = "SELECT COUNT(*) as idCount FROM tweet where idtweet = ?";
        int value = 0;

        try {
            PreparedStatement ps = this.connection.prepareStatement(query_id);
            ps.setLong(1,id_tweet);
            ResultSet re = ps.executeQuery();
            while(re.next()){
                value += re.getInt("idCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(value == 0){
            String query_game = "SELECT id FROM juegos where nombre = ?";
            String query_add = "INSERT INTO tweet (idtweet,id_juego) VALUES (?,?)";
            try {
                PreparedStatement ps = this.connection.prepareStatement(query_game);
                ps.setString(1,gameName);
                ResultSet re = ps.executeQuery();
                int idGame = 0;
                while(re.next()){
                    idGame += re.getInt("id");
                }
                PreparedStatement ps_add = this.connection.prepareStatement(query_add);
                ps_add.setLong(1,id_tweet);
                ps_add.setInt(2,idGame);
                ps_add.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return value;
    }

    //Obtiene la cantidad de tweets positivos negativos en un arreglo de 2
    public int[] getCountTweets(String gameName)
    {
        //[0] = tweets positivos, [1] = tweets negativos
        int[] return_values = new int[2];
        String query_pos = "SELECT tweets_pos,tweets_neg FROM resume INNER JOIN juegos on juegos.id = resume.id_juego where nombre ="+"'"+gameName+"'";
        String query_neg = "SELECT tweets_neg FROM resume INNER JOIN juegos on juegos.id = resume.id_juego where nombre ="+"'"+gameName+"'";
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultset = st.executeQuery(query_pos);
            while(resultset.next()){
                return_values[0] = resultset.getInt("tweets_pos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement st = this.connection.createStatement();
            ResultSet resultset = st.executeQuery(query_neg);

            while(resultset.next()){
                return_values[1] = resultset.getInt("tweets_neg");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return return_values;
    }

    //Aumenta la metrica de tweets positivos en 1
    public void aumentPositiveTweets(int[] tweet_values,String gameName){

        int tweets_positivos = tweet_values[0] + 1;

        String query = "UPDATE resume INNER JOIN juegos on juegos.id = resume.id_juego SET tweets_pos = ? WHERE nombre ="+"'"+gameName+"'";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1,tweets_positivos);
            ps.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Aumenta la metrica de tweets negativos en 1
    public void aumentNegativeTweets(int[] tweet_values,String gameName) {

        int tweets_negativos = tweet_values[1] + 1;

        String query = "UPDATE resume INNER JOIN juegos on juegos.id = resume.id_juego SET tweets_neg = ? WHERE nombre =" + "'" + gameName + "'";
        try {
            PreparedStatement p = this.connection.prepareStatement(query);
            p.setInt(1,tweets_negativos);
            p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void truncateTop(){

        String query_t = "TRUNCATE usertop";
        try {
            PreparedStatement p = this.connection.prepareStatement(query_t);
            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query_a = "TRUNCATE userretop";
        try {
            PreparedStatement pa = this.connection.prepareStatement(query_a);
            pa.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTopUser(userModel u){

        String query_in = "INSERT INTO  usertop (user_id,user_name,user_mentions,user_favourites_count,followers_count,user_friend_count,user_listed_count,user_statuses_count,user_url,user_profile_image_url,retweets,rank) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query_in);
            ps.setLong(1,u.getU_id());
            ps.setString(2,u.getU_name());
            ps.setInt(3,u.getU_mentions());
            ps.setInt(4,u.getU_user_favourites_count());
            ps.setInt(5,u.getU_followers_count());
            ps.setInt(6,u.getU_friend_count());
            ps.setInt(7,u.getU_listed_count());
            ps.setInt(8,u.getU_statuses_count());
            ps.setString(9,u.getU_url());
            ps.setString(10,u.getU_profile_image_url());
            ps.setInt(11,u.getRetweet());
            ps.setInt(12,u.getRank());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }

    public void addRetweet(long id, String user_name, String user_url, String u_profile_image){
        String query = "SELECT id FROM usertop where user_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet re_c = ps.executeQuery();
            long value = 0;
            while(re_c.next()){
                value += re_c.getLong("id");
            }
            String query_b = "INSERT INTO userretop (id_top,user_name,user_url,user_profile_image_url) VALUES (?,?,?,?)";
            PreparedStatement ps_b = this.connection.prepareStatement(query_b);
            ps_b.setLong(1,value);
            ps_b.setString(2,user_name);
            ps_b.setString(3,user_url);
            ps_b.setString(4,u_profile_image);
            ps_b.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setRankinOnTable(List<userModel> um){
        String query = "UPDATE usertop SET rank = ? WHERE user_id = ?";
        for(userModel u: um){
            try {
                PreparedStatement ps = this.connection.prepareStatement(query);
                ps.setInt(1,u.getRank());
                ps.setLong(2,u.getU_id());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
