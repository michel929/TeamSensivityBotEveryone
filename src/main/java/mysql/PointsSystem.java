package mysql;

import java.sql.*;

public class PointsSystem {
    public static void uploadPoints(String id, int points){
        points = points + getPoints(id);

        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("UPDATE users SET points = '" + points + "' WHERE discord_id = '" + id + "'");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPoints(String id){
        int i = 0;
        try {
            Connection con = Connect.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                if(id.equals(rs.getString("discord_id"))){
                    i = rs.getInt("points");
                }

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }
}
