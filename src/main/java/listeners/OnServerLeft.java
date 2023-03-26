package listeners;

import mysql.Connect;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OnServerLeft extends ListenerAdapter {
    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        removeDatabase(event.getGuild().getId());
    }

    private void removeDatabase(String id){
        try {
            Connection con = Connect.getConnection();

            PreparedStatement posted = con.prepareStatement("DROP DATABASE " + id);

            posted.executeUpdate();
            con.close();

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
