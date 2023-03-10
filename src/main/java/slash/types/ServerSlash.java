package slash.types;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ServerSlash {
    public void performCommand(SlashCommandInteractionEvent event);
}
