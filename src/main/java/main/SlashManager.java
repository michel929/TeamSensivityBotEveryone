package main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import slash.types.ServerSlash;

import java.util.concurrent.ConcurrentHashMap;

public class SlashManager {
    public ConcurrentHashMap<String, ServerSlash> slashs;

    public SlashManager() {
        this.slashs = new ConcurrentHashMap<>();

        slashs.put("login", new Login());
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