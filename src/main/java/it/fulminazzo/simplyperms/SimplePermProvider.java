package it.fulminazzo.simplyperms;

import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import it.fulminazzo.simplyperms.users.User;

import java.util.UUID;

public class SimplePermProvider implements PermissionProvider {
    @Override
    public PermissionFunction createFunction(final PermissionSubject subject) {
        return new SimplePermFunction(subject);
    }

    private record SimplePermFunction(PermissionSubject subject) implements PermissionFunction {

        @Override
        public Tristate getPermissionValue(final String permission) {
            if (this.subject instanceof Player) {
                UUID uuid = ((Player) this.subject).getUniqueId();
                User user = User.getUser(uuid);
                return user.getPermissionState(permission);
            }
            return Tristate.TRUE;
        }
    }
}
