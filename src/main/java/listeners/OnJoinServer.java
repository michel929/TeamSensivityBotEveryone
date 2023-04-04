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
        createTableTemp(event.getGuild().getId());

        addToList(event.getGuild().getId());
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

            posted = con.prepareStatement("CREATE TABLE points_settings (id int(10) UNSIGNED NOT NULL, nachrichten_on int(11) NOT NULL DEFAULT 0, reaktionen_on int(11) NOT NULL DEFAULT 0, voicechannel_on int(11) NOT NULL DEFAULT 0, multiplier_on int(11) NOT NULL DEFAULT 0, nachrichten int(11) NOT NULL DEFAULT 1, reaktionen int(11) NOT NULL DEFAULT 1, voicechannel int(11) NOT NULL DEFAULT 1, multiplier double NOT NULL DEFAULT 1.5, feature int(11) NOT NULL DEFAULT 0)");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableTemp(String id){
        try {
            Connection con = Connect.getConnection2(id);

            PreparedStatement posted = con.prepareStatement("CREATE TABLE temp_settings (Id int, VoiceChannel varchar(255), Syntax varchar(255), feature int)");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    private void addToList(String id){
        try {
            Connection con = Connect.getConnection3();

            PreparedStatement posted = con.prepareStatement("INSERT INTO bot_guilds (Id, guild_id) VALUES ('"+ id +"')");

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
