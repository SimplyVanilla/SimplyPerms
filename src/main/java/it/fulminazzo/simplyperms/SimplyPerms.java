package it.fulminazzo.simplyperms;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.File;
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

    @Inject
    public SimplyPerms(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory) {
        plugin = this;
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory.toFile();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }
}
