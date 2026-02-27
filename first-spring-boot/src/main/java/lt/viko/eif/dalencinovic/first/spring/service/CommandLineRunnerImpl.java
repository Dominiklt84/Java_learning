package lt.viko.eif.dalencinovic.first.spring.service;

import lt.viko.eif.dalencinovic.first.spring.menu.UserMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private UserMenu userMenu;

    @Override
    public void run(String... args) throws Exception{
        userMenu.showMenu();
    }
}
