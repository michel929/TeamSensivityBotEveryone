package mysql;

import geheim.Passwort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    static String url = "jdbc:mysql://192.168.178.200:3306";
    static String user = "discord";
    static String pass = Passwort.getDataPasswort();

    public static Connection getConnection(){
        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Connection getConnection2(String id){
        url = url + "/" + id;
        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
