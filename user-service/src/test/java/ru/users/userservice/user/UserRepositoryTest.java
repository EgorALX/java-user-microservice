package ru.users.userservice.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.users.userservice.model.User;
import ru.users.userservice.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUsersByParamsTest() {

        userRepository.saveAll(List.of(user1, user2, user3));

        Pageable pageable = PageRequest.of(0, 10);

        List<User> result = userRepository.getUsersByParams("John", null, LocalDate.of(1990, 1, 1), pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user1.getSurname(), result.get(0).getSurname());
        assertEquals(user1.getRegistrationDate(), result.get(0).getRegistrationDate());

        List<User> resultWithSurname = userRepository.getUsersByParams(null, "Smith", null, pageable);

        assertNotNull(resultWithSurname);
        assertEquals(1, resultWithSurname.size());
        assertEquals(user2.getName(), resultWithSurname.get(0).getName());
        assertEquals(user2.getSurname(), resultWithSurname.get(0).getSurname());
        assertEquals(user2.getRegistrationDate(), resultWithSurname.get(0).getRegistrationDate());

        List<User> resultWithNullParams = userRepository.getUsersByParams(null, null, null, pageable);

        assertNotNull(resultWithNullParams);
        assertEquals(3, resultWithNullParams.size());
    }
}
