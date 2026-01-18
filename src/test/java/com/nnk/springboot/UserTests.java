package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest(){
        User user = new User("user1", "123456", "lastName firstName", "Admin");

        //Save
        user = userRepository.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getUsername().equals("user1"));

        //Update
        user.setUsername("user1 update");
        user = userRepository.save(user);
        Assert.assertTrue(user.getUsername().equals("user1 update"));

        //Find
        List<User> listUser = userRepository.findAll();
        Assert.assertTrue(listUser.size() > 0);

        //Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userDeleted = userRepository.findById(id);
        Assert.assertFalse(userDeleted.isPresent());
    }

}
