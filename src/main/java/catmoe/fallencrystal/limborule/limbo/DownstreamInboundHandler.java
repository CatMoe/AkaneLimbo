package catmoe.fallencrystal.limborule.limbo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelPipeline;
import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.PacketWrapper;
import net.md_5.bungee.protocol.packet.Kick;

public class DownstreamInboundHandler extends ChannelHandlerAdapter implements ChannelInboundHandler {
    public static final String name = "catmoe.fallencrystal.limborule:downstream-inbound-handler";

    private final UserConnection ucon;
    private final ServerConnection server;
    private final ChannelWrapper ch;

    public static void attachHandlerTo(UserConnection ucon) {
        ChannelPipeline pipeline = ucon.getServer().getCh().getHandle().pipeline();
        attachHandlerTo(pipeline, ucon);
    }

    public static void attachHandlerTo(ChannelPipeline pipeline, UserConnection ucon) {
        detachHandlerFrom(pipeline);
        pipeline.addBefore("inbound-boss", name, new DownstreamInboundHandler(ucon));
    }

    public static void detachHandlerFrom(ChannelPipeline pipeline) {
        if (pipeline.get(name) != null) {
            pipeline.remove(name);
        }
    }

    public DownstreamInboundHandler(UserConnection ucon) {
        this.ucon = ucon;
        this.server = ucon.getServer();
        this.ch = ucon.getServer().getCh();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (ucon.isConnected()) {
            server.setObsolete(true);
            ch.markClosed();
            ;
            server.getInfo().removePlayer(ucon);
            ServerDisconnectEvent serverDisconnectEvent = new ServerDisconnectEvent(ucon, this.server.getInfo());
            ProxyServer.getInstance().getPluginManager().callEvent(serverDisconnectEvent);
            return;
        }
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        if (obj instanceof PacketWrapper) {
            boolean fireNextRead = true;
            PacketWrapper wrapper = (PacketWrapper) obj;
            DefinedPacket packet = wrapper.packet;
            if (packet instanceof Kick) {
                if (fireNextRead) {
                    ctx.fireChannelRead(obj);
                } else {
                    wrapper.trySingleRelease();
                }
            } else {
                ctx.fireChannelRead(obj);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) {
        ctx.fireUserEventTriggered(obj);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        ctx.fireChannelWritabilityChanged();
    }
}
