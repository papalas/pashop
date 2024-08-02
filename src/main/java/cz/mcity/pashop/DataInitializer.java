package cz.mcity.pashop;

import cz.mcity.pashop.model.Users;
import cz.mcity.pashop.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            if (userRepository.count() == 0) {
                Users user = new Users();
                user.setUsername("user");
                user.setPasswordHash(passwordEncoder.encode("password"));
                user.setEmail("test@mcity.cz");
                userRepository.save(user);
                System.out.println("Sample user created");
            }
        };
    }
}