package main;

import commands.types.PrivateCommand;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    public ConcurrentHashMap<String, ServerCommand> commands;
    public ConcurrentHashMap<String, PrivateCommand> commandsp;

    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();
        this.commandsp = new ConcurrentHashMap<>();

        commandsp.put("update", new UpdateCommand());
    }

    public boolean perform(String command, Member m, TextChannel channel, Message message){

        ServerCommand cmd;
        if((cmd = this.commands.get(command.toLowerCase())) != null){
            try {
                cmd.performCommand(m, channel, message);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean perform(String command, User m, PrivateChannel channel, Message message){

        PrivateCommand cmd;
        if((cmd = this.commandsp.get(command.toLowerCase())) != null){
            cmd.performCommand(m, channel, message);
            return true;
        }
        return false;
    }
}