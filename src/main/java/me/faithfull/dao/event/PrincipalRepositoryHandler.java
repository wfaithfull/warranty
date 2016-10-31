package me.faithfull.dao.event;

import me.faithfull.domain.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Will Faithfull
 */
@RepositoryEventHandler(value = Principal.class)
@Component
public class PrincipalRepositoryHandler {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void onBeforeCreate(Principal principal) {
        principal.setPassword(passwordEncoder.encode(principal.getPassword()));
    }

}
