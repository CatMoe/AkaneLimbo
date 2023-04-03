package catmoe.fallencrystal.limborule.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catmoe.fallencrystal.limborule.util.MessageUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

public class CommandManager extends Command implements TabExecutor {
    private final List<SubCommand> loadedCommands;
    private final List<String> tabComplete;

    public CommandManager(Plugin plugin, String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.loadedCommands = new ArrayList<>();
        this.tabComplete = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            MessageUtil.prefixsender(sender, "idk");
            return;
        }
        SubCommand cmd = getSubCommandFromArgs(args[0]);
        if (cmd == null) {
            return; // 未找到命令
        }
        if (args[0].equals(cmd.getSubCommandId())) {
            if (!cmd.allowedConsole() && !(sender instanceof ProxiedPlayer)) {
                return; // not allowed console
            }
            if (!sender.hasPermission(cmd.getPermission())) {
                return; // dont have permission
            }
            if (cmd.StrictSizeLimit()) {
                if (args.length == cmd.StrictSize()) {
                    cmd.execute(sender, args);
                    return;
                } else {
                    return; // command length not equal command need.
                }
            } else {
                cmd.execute(sender, args);
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        SubCommand subCommand = getSubCommandFromArgs(args[0]);
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(args.length - 1, Arrays.asList("<Null>"));
        if (subCommand != null && args[0].equals(subCommand.getSubCommandId())) {
            if (subCommand.getTabCompleter() != null && subCommand.getTabCompleter().get(args.length - 1) != null) {
                return subCommand.getTabCompleter().get(args.length - 1);
            } else {
                return map.get(args.length - 1);
            }
        }
        if (args.length == 1) {
            return tabComplete;
        }
        return map.get(args.length - 1);
    }

    private SubCommand getSubCommandFromArgs(String args0) {
        for (SubCommand subCommand : loadedCommands) {
            if (subCommand.getSubCommandId().equals(args0)) {
                return subCommand;
            }
        }
        return null;
    }

    public void register(SubCommand subCommand) {
        loadedCommands.add(subCommand);
        tabComplete.add(subCommand.getSubCommandId());
    }
}
