package main;

import buttons.Revive;
import buttons.YourPoints;
import dbd.swf.buttons.SWFJoin;
import dbd.swf.buttons.SWFLeave;
import dbd.swf.buttons.SWFYes;
import buttons.types.ServerButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.concurrent.ConcurrentHashMap;

public class ButtonManager {
    public ConcurrentHashMap<String, ServerButton> buttons;

    public ButtonManager() {
        this.buttons = new ConcurrentHashMap<>();

        buttons.put("swfjoin", new SWFJoin());
        buttons.put("swfleave", new SWFLeave());
        buttons.put("swfyes", new SWFYes());
        buttons.put("showPoints", new YourPoints());
        buttons.put("revive", new Revive());
    }

    public boolean perform(String command, ButtonInteractionEvent event){

        ServerButton cmd;
        if(command.contains("swf")) {
            if ((cmd = this.buttons.get(command.toLowerCase().substring(0, command.length() - 36))) != null) {
                cmd.performCommand(event);
                return true;
            }
        }else if(command.contains("revive")){
            if ((cmd = this.buttons.get("revive")) != null) {
                cmd.performCommand(event);
                return true;
            }
        }else {
            if ((cmd = this.buttons.get(command)) != null) {
                cmd.performCommand(event);
                return true;
            }
        }
        return false;
    }
}
