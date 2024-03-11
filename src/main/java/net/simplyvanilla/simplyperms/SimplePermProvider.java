package net.simplyvanilla.simplyperms;

import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import net.simplyvanilla.simplyperms.users.User;

import java.util.UUID;

public class SimplePermProvider implements PermissionProvider {
    protected final SimplyPerms plugin;

    public SimplePermProvider(SimplyPerms plugin) {
        this.plugin = plugin;
    }

    @Override
    public PermissionFunction createFunction(final PermissionSubject subject) {
        return new SimplePermFunction(this.plugin, subject);
    }

    private record SimplePermFunction(SimplyPerms plugin, PermissionSubject subject) implements PermissionFunction {

        @Override
        public Tristate getPermissionValue(final String permission) {
            if (this.subject instanceof Player) {
                UUID uuid = ((Player) this.subject).getUniqueId();
                User user = this.plugin.getUsersManager().getUser(uuid);
                return user.getPermissionState(permission);
            }
            return Tristate.TRUE;
        }
    }
}
