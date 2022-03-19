package com.example.demo.registration;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.registration.model.User;
import com.example.demo.registration.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    public void testCreate() {
        User user = new User();
        user.setEmailAddress("bob@ibiza.com");
        user.setFirstName("bob");
        user.setLastName("sinclar");
        user.setPassword("ibiza");
        user.setTimeZone("Spain");
        user.setUuid(UUID.randomUUID());
        
        User savesUser = repo.save(user);
        
        User existUser = entityManager.find(User.class, savesUser.getId());
        
        assertThat(existUser.getEmailAddress()).isEqualTo(user.getEmailAddress());
    }
    
    @Test
    public void testFindByEmailAddress() {
        String emailAddress = "david@ibiza.com";
        
        Optional<User> user1 = repo.findByEmailAddress(emailAddress);        
        assertThat(user1.isPresent()).isFalse();
        
        User newUser = new User();
        newUser.setEmailAddress(emailAddress);
        newUser.setFirstName("devid");
        newUser.setLastName("guetta");
        newUser.setPassword("ibiza");
        newUser.setTimeZone("Spain");
        newUser.setUuid(UUID.randomUUID());
        
        User savedNewUser = repo.save(newUser);

        user1 = repo.findByEmailAddress(emailAddress);        
        assertThat(user1.isPresent()).isTrue();
        
        assertThat(user1.get().getId()).isEqualTo(savedNewUser.getId());
    }
}
