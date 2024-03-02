package it.fulminazzo.simplyperms.listeners;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.command.PlayerAvailableCommandsEvent;
import com.velocitypowered.api.event.player.TabCompleteEvent;
import com.velocitypowered.api.proxy.Player;
import it.fulminazzo.simplyperms.SimplyPerms;

public class CommandListener {
    private final SimplyPerms plugin;

    public CommandListener(SimplyPerms plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void on(CommandExecuteEvent event) {
        if (cannotExecute(event.getCommandSource(), event.getCommand()))
            event.setResult(CommandExecuteEvent.CommandResult.denied());
    }

    @Subscribe
    @SuppressWarnings("UnstableApiUsage")
    public void on(PlayerAvailableCommandsEvent event) {
        event.getRootNode().getChildren().removeIf((commandNode) ->
                cannotExecute(event.getPlayer(), commandNode.getName()));
    }

    @Subscribe
    public void on(TabCompleteEvent event) {
        if (cannotExecute(event.getPlayer(), event.getPartialMessage()))
            event.getSuggestions().clear();
    }

    private boolean cannotExecute(CommandSource source, String command) {
        String cmd = getCommand(command);
        if (!(source instanceof Player)) return false;
        if (this.plugin.getProxyServer().getCommandManager().getCommandMeta(cmd) == null) return false;
        return this.plugin.getAllowedCommands().stream().noneMatch(c -> c.equalsIgnoreCase(getCommand(cmd)));
    }

    private String getCommand(String raw) {
        if (raw == null) return "";
        while (raw.startsWith("/")) raw = raw.substring(1);
        return raw.split(" ")[0];
    }
}
