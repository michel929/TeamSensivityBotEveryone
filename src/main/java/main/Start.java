package main;

import geheim.BotToken;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import templates.EmbedMessages;

import javax.security.auth.login.LoginException;

public class Start {

    public static void main(String[] args) {
        System.out.println("Bot wird gestartet!");

        try {
            INSTANCE = new Start();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public static Start INSTANCE;
    public static String VERSION_ID = "2.4.2";

    private JDA api;
    private CommandManager cmdMan;
    private SlashManager slashMan;
    private ButtonManager buttonMan;
    private EmbedMessages embedMessages;

    public Start() throws LoginException, IllegalArgumentException {

        INSTANCE = this;

        api = JDABuilder.create(BotToken.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build();
        api.getPresence().setActivity(Activity.competing("Version " + VERSION_ID));
        api.getPresence().setStatus(OnlineStatus.ONLINE);

        listeners();
        commands();

        System.out.println("Bot ist online!");

        this.cmdMan = new CommandManager();
        this.slashMan = new SlashManager();
        this.buttonMan = new ButtonManager();
        this.embedMessages = new EmbedMessages();

        api.setAutoReconnect(true);
    }

    private void listeners() {
        api.addEventListener(new SlashCommand());
    }

    private void commands() {
        api.upsertCommand("connect", "Hiermit verbindest du deinen DiscordAccount mit dem Dashboard.").queue();
    }

    public JDA getApi() {
        return api;
    }

    public EmbedMessages getEmbedMessages() {
        return embedMessages;
    }

    public CommandManager getCmdMan() {
        return cmdMan;
    }

    public SlashManager getSlashMan() {
        return slashMan;
    }

    public ButtonManager getButtonMan() {
        return buttonMan;
    }
}
