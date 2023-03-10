package buttons.types;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ServerButton {
    public void performCommand(ButtonInteractionEvent event);
}
