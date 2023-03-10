package main;
import music.slash.Play;
import music.slash.Skip;
import music.slash.Stop;
import music.slash.Volume;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.*;
import dbd.swf.slash.SWF;
import slash.dashboard.*;
import slash.types.ServerSlash;

import java.util.concurrent.ConcurrentHashMap;

public class SlashManager {
    public ConcurrentHashMap<String, ServerSlash> slashs;

    public SlashManager() {
        this.slashs = new ConcurrentHashMap<>();

        slashs.put("login", new Login());
        slashs.put("connect", new Connect());
        slashs.put("revoke", new Revoke());
        slashs.put("lock", new LockUsername());
        slashs.put("setup", new Setup());

        slashs.put("account", new Profil());
        slashs.put("minecraft", new ConnectMinecraft());

        slashs.put("swf", new SWF());
        slashs.put("points", new Punkte());
        slashs.put("daily", new Daily());

        slashs.put("play", new Play());
        slashs.put("volume", new Volume());
        slashs.put("stop", new Stop());
        slashs.put("skip", new Skip());
    }

    public boolean perform(String command, SlashCommandInteractionEvent event){

        ServerSlash cmd;
        if((cmd = this.slashs.get(command.toLowerCase())) != null){
            cmd.performCommand(event);
            return true;
        }
        return false;
    }
}
