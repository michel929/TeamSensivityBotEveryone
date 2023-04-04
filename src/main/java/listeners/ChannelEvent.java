package listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelEvent extends ListenerAdapter {
    public static List<Channel> channel = new ArrayList<>();
    private static ConcurrentHashMap<Member, LocalDateTime> members = new ConcurrentHashMap<>();
    EnumSet<Permission> permission = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_MUTE_OTHERS, Permission.VOICE_DEAF_OTHERS, Permission.VOICE_MOVE_OTHERS);
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Category c = event.getGuild().getCategoryById(BotInfos.getBotInfos("chill_cat"));

        if(event.getChannelLeft() != null) {
            //Create-Chill
            if (channel.contains(event.getChannelLeft())) {
                if (event.getChannelLeft().getMembers().size() == 0) {
                    event.getChannelLeft().delete().queue();
                    channel.remove(event.getChannelLeft());
                }
            }
        }

        if(event.getChannelJoined() != null) {
            //Create-Chill
            if (BotInfos.getBotInfos("chill_create").equals("1")) {
                if (event.getChannelJoined().getId().equals(BotInfos.getBotInfos("chill_channel"))) {
                    boolean finish = false;
                    int x = 1;

                    while(finish == false) {
                        int w = 0;
                        for (Channel v : channel) {
                            if(v.getName().contains("" + x)){
                                x++;
                            }else {
                                w++;
                            }
                        }

                        if(w == channel.size()){
                            finish = true;
                        }
                    }

                    c.createVoiceChannel("Chill | " + x).addPermissionOverride(event.getMember(), permission, null).queue(voiceChannel -> {
                        event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue();
                        channel.add(voiceChannel);
                    });
                }
            }
        }

        if(event.getChannelLeft() == null){
            //PointsSystem
            if (BotInfos.getBotInfos("punktesystem").equals("1")) {
                if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                    members.put(event.getMember(), LocalDateTime.now());
                }
            }
        }

        if(event.getChannelJoined() == null){
            //PointSystem
            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users") && members.containsKey(event.getMember())) {
                LocalDateTime date = members.get(event.getMember());
                Minutes m = Minutes.minutesBetween(date, LocalDateTime.now());

                if(m.getMinutes() > 1) {
                    PunkteSystem.uploadMinutes(date, LocalDateTime.now(), event.getMember().getId(), m.getMinutes());

                    if(event.getMember().isBoosting()){
                        PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes() + (m.getMinutes() / 2));
                        PunkteSystem.upload(event.getMember().getId(), m.getMinutes() + (m.getMinutes() / 2), 1, "Durch Aktivität im SprachChannel. (Points x 1.5 Booster)");
                    }else {
                        PunkteSystem.uploadPoints(event.getMember().getId(), m.getMinutes());
                        PunkteSystem.upload(event.getMember().getId(), m.getMinutes(), 1, "Durch Aktivität im SprachChannel.");
                    }

                    if(!PlayerInfos.getInfo(event.getMember().getId(),"discord_id", "discord_token", "users").equals("0")){
                        String url = "https://dashboard.sensivity.team/connect/discord/update-points.php?discord_id=" + event.getMember().getId();
                        String url2 = "https://dashboard.sensivity.team/connect/discord/refresh.php?id=" + event.getMember().getId();
                        try {
                            if(GetInfos.getPoints(new URL(url)).contains("Unauthorized")){
                                GetInfos.streamBOT(new URL(url2));
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                members.remove(event.getMember());
            }
        }
    }
}
