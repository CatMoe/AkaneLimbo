package catmoe.fallencrystal.limborule.limbo;

import io.netty.channel.Channel;

import net.md_5.bungee.BungeeServerInfo;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.netty.HandlerBoss;
import net.md_5.bungee.netty.PipelineUtils;
import net.md_5.bungee.protocol.MinecraftDecoder;
import net.md_5.bungee.protocol.Protocol;

public class ChannelInitializer {
    private final ProxyServer bungee;
    private final UserConnection user;
    private final BungeeServerInfo target;

    public ChannelInitializer(ProxyServer bungee, UserConnection user, BungeeServerInfo target) {
        this.bungee = bungee;
        this.user = user;
        this.target = target;
    }

    protected void initChannel(Channel ch) throws Exception {
        PipelineUtils.BASE.initChannel(ch);
        ch.pipeline().addAfter(PipelineUtils.FRAME_DECODER, PipelineUtils.PACKET_DECODER,
                new MinecraftDecoder(Protocol.HANDSHAKE, false, user.getPendingConnection().getVersion()));
        ch.pipeline().addAfter(PipelineUtils.FRAME_PREPENDER, PipelineUtils.PACKET_ENCODER,
                new MinecraftDecoder(Protocol.HANDSHAKE, false, user.getPendingConnection().getVersion()));
        ch.pipeline().get(HandlerBoss.class).setHandler(new LimboServerConnector(bungee, user, target));
    }
}
