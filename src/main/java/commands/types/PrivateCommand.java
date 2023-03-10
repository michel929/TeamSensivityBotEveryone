package commands.types;


import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;

public interface PrivateCommand {
    public void performCommand(User m, PrivateChannel channel, Message message);
}
