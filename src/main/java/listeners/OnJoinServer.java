package listeners;

import mysql.Connect;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OnJoinServer extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        createDatabase(event.getGuild().getId());

        createTablePoints(event.getGuild().getId());
        createTableBotInfos(event.getGuild().getId());
    }

    private void createDatabase(String id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("CREATE DATABASE " + id);

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTablePoints(String id){
        try {
            Connection con = Connect.getConnection2(id);

            PreparedStatement posted = con.prepareStatement("CREATE TABLE points (id int, discord_id varchar(255), points int)");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableBotInfos(String id){
        try {
            Connection con = Connect.getConnection2(id);

            PreparedStatement posted = con.prepareStatement("CREATE TABLE bot_infos (id int, discord_id varchar(255), points int)");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
