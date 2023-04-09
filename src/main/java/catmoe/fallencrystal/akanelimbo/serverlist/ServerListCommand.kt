package catmoe.fallencrystal.akanelimbo.serverlist;

import java.util.List;
import java.util.Map;

import catmoe.fallencrystal.akanelimbo.command.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerListCommand implements SubCommand {

    @Override
    public String getSubCommandId() {
        return "serverlist";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ServerListMenu menu = new ServerListMenu();
        menu.open((ProxiedPlayer) sender);
    }

    @Override
    public String getPermission() {
        return "bungeecord.command.server";
    }

    @Override
    public Map<Integer, List<String>> getTabCompleter() {
        return null;
    }

    @Override
    public boolean allowedConsole() {
        return false;
    }

    @Override
    public boolean StrictSizeLimit() {
        return true;
    }

    @Override
    public int StrictSize() {
        return 1;
    }

}
