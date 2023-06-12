package ru.kata.spring.boot_security.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitUsersAndRoles {
    UserService userService;

    public InitUsersAndRoles(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {

        if (userService.getAllRoles().isEmpty()) {
            System.out.println("Initializing users and roles...");

            Role admin = new Role(1L, "ROLE_ADMIN");
            Role user = new Role(2L, "ROLE_USER");
            userService.saveRole(admin);
            userService.saveRole(user);

            Set<Role> roles = new HashSet<>();
            roles.add(user);

            User newUser = new User(1L, "USER", "USEROVICH", 20, "user@mail.com", "user", "user", roles);

            roles = new HashSet<>();
            roles.add(user);
            roles.add(admin);

            User newAdmin = new User(2L, "ADMIN", "ADMINOVICH", 20, "admin@mail.com", "admin", "admin", roles);

            userService.saveUser(newUser);
            userService.saveUser(newAdmin);

            System.out.println("Hello! I have just created a few users:\n" +
                    "Username: USER      Password: user\n" +
                    "Username: ADMIN     Password: admin");
        }
    }
}
