package catmoe.fallencrystal.akanelimbo.command;

import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;

public interface SubCommand {
    String getSubCommandId();

    void execute(CommandSender sender, String[] args);

    String getPermission();

    Map<Integer, List<String>> getTabCompleter();

    boolean allowedConsole();

    boolean strictSizeLimit();

    int strictSize();
}
