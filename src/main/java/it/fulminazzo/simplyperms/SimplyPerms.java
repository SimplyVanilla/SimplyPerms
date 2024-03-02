package it.fulminazzo.simplyperms;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "simplyperms",
        name = "SimplyPerms",
        version = "1.0",
        description = "A simple plugin to manage permissions in your server via config.yml. Supports only Velocity.",
        authors = {"Fulminazzo"}
)
public class SimplyPerms {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }
}
