package com.driver.services.impl;

import com.driver.model.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {
        Optional<User> optionuser = userRepository4.findById(userId);
        if(optionuser.isPresent()) {
            User newuser = optionuser.get();
            userRepository4.delete(newuser);
        }
    }

    @Override
    public User updatePassword(Integer userId, String password) {
        Optional<User> optionuser = userRepository4.findById(userId);
        if(optionuser.isPresent()) {
            User newuser = optionuser.get();
            newuser.setPassword(password);
            return userRepository4.save(newuser);
        }
        return null;
    }

    @Override
    public void register(String name, String phoneNumber, String password) {
          User newuser = new User();
          newuser.setName(name);
          newuser.setPhoneNumber(phoneNumber);
          newuser.setPassword(password);
          userRepository4.save(newuser);
    }
}
