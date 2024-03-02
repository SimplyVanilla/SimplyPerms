package it.fulminazzo.simplyperms;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.fulminazzo.simplyperms.groups.Group;
import it.fulminazzo.simplyperms.groups.GroupYAMLParser;
import it.fulminazzo.simplyperms.users.UserYAMLParser;
import it.fulminazzo.simplyperms.utils.GroupUtils;
import it.fulminazzo.yamlparser.configuration.FileConfiguration;
import it.fulminazzo.yamlparser.utils.FileUtils;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Plugin(
        id = "simplyperms",
        name = "SimplyPerms",
        version = "1.0",
        description = "A simple plugin to manage permissions in your server via config.yml. Supports only Velocity.",
        authors = {"Fulminazzo"}
)
@Getter
public class SimplyPerms {
    @Getter
    private static SimplyPerms plugin;
    private final ProxyServer proxyServer;
    private final Logger logger;
    private final File dataDirectory;

    private FileConfiguration configuration;

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

        loadGroups();
    }

    @Subscribe
    public void onDisable(final ProxyShutdownEvent event) {
        unloadGroups();
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

    private void unloadGroups() {
        Group.clearGroups();
    }
}
