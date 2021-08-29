package ecma.ai.lesson6_task2.component;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.Role;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.entity.enums.CardType;
import ecma.ai.lesson6_task2.entity.enums.RoleName;
import ecma.ai.lesson6_task2.repository.BankRepository;
import ecma.ai.lesson6_task2.repository.CardRepository;
import ecma.ai.lesson6_task2.repository.RoleRepository;
import ecma.ai.lesson6_task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CardRepository cardRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

            Role director = roleRepository.save(
                    new Role(1, RoleName.DIRECTOR));

            Role manager = roleRepository.save(
                    new Role(2, RoleName.MANAGER));

            Role user = roleRepository.save(
                    new Role(3, RoleName.USER));


            Bank universalBank = bankRepository.save(new Bank(1, "UniversalBank"));

            List<Card> cardList = new ArrayList<>();
            cardList.add(new Card(1, "1234567891234567", "111", universalBank, "TJU", ("1234"),
                    Date.valueOf("2021-03-05"), CardType.HUMO, false, false, 0));
            cardList.add(new Card(2, "2345678912345678", "111", universalBank, "AAA", ("1111"),
                    Date.valueOf("2021-05-04"), CardType.UZCARD, false, false, 0));
            cardList.add(new Card(3, "3456789123456789", "111", universalBank, "JJJ", ("2222"),
                    Date.valueOf("2021-05-04"), CardType.HUMO, false, false, 0));

            cardRepository.saveAll(cardList);
            userRepository.save(new User(1, "DDD", director));
            userRepository.save(new User(2, "MMM", manager));
            userRepository.save(new User(3, "UUU", user));
        }
    }
}
