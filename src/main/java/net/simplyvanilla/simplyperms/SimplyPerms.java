package net.simplyvanilla.simplyperms;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.simplyvanilla.simplyperms.groups.Group;
import net.simplyvanilla.simplyperms.groups.GroupYAMLParser;
import net.simplyvanilla.simplyperms.listeners.CommandListener;
import net.simplyvanilla.simplyperms.users.User;
import net.simplyvanilla.simplyperms.users.UserYAMLParser;
import net.simplyvanilla.simplyperms.users.UsersManager;
import net.simplyvanilla.simplyperms.utils.GroupUtils;
import it.fulminazzo.yamlparser.configuration.ConfigurationSection;
import it.fulminazzo.yamlparser.configuration.FileConfiguration;
import it.fulminazzo.yamlparser.utils.FileUtils;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "simplyperms",
        name = "SimplyPerms",
        version = "1.0",
        url = "https://github.com/SimplyVanilla/SimplyPerms",
        description = "A simple Velocity plugin to manage permissions on your server via config.yml.",
        authors = {"Simply Vanilla"}
)
@Getter
public class SimplyPerms {
    @Getter
    private static SimplyPerms plugin;
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final File dataDirectory;

    private FileConfiguration configuration;
    private UsersManager usersManager;

    static {
        FileConfiguration.addParsers(new GroupYAMLParser());
        FileConfiguration.addParsers(new UserYAMLParser());
    }

    @Inject
    public SimplyPerms(final ProxyServer proxyServer, final Logger logger, final @DataDirectory Path dataDirectory) {
        plugin = this;
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory.toFile();
    }

    @Subscribe
    public void onEnable(final ProxyInitializeEvent event) {
        this.configuration = loadConfiguration("config.yml");

        this.usersManager = new UsersManager();

        loadGroups();
        loadUsers();

        this.proxyServer.getEventManager().register(this, new CommandListener(this));
    }

    @Subscribe
    public void onDisable(final ProxyShutdownEvent event) {
        unloadGroups();
        unloadUsers();
    }

    @Subscribe
    public void on(PermissionsSetupEvent event) {
        event.setProvider(new SimplePermProvider(this));
    }

    public List<String> getAllowedCommands() {
        List<String> commands = this.configuration.getStringList("allowed-commands");
        if (commands == null) commands = new ArrayList<>();
        return commands;
    }

    private FileConfiguration loadConfiguration(final String configName) {
        File file = new File(this.dataDirectory, configName);
        if (!file.exists())
            try {
                InputStream stream = SimplyPerms.class.getResourceAsStream(configName);
                if (stream == null) stream = SimplyPerms.class.getResourceAsStream("/" + configName);
                if (stream == null) throw new NullPointerException("Could not find configuration file: " + configName);
                FileUtils.writeToFile(file, stream);
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return new FileConfiguration(file);
    }

    private void loadGroups() {
        GroupUtils.loadGroups(this.configuration);
    }

    private void loadUsers() {
        final ConfigurationSection usersSection = this.configuration.getConfigurationSection("users");
        if (usersSection != null)
            for (final String key : usersSection.getKeys()) {
                this.usersManager.addUser(usersSection.get(key, User.class));
            }
    }

    private void unloadGroups() {
        Group.clearGroups();
    }

    private void unloadUsers() {
        this.usersManager.clearUsers();
    }
}
