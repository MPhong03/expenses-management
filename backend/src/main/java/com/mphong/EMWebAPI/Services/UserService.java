package com.mphong.EMWebAPI.Services;

import com.mphong.EMWebAPI.Models.Datas.User;
import com.mphong.EMWebAPI.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(User user) {
        if (checkUserExist(user)) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        User newUser = userRepository.findByEmail(user.getEmail());
        if (newUser == null) {
            throw new RuntimeException("Email not exists");
        }
        newUser.setOtp(user.getOtp());
        newUser.setOtpExpiryTime(user.getOtpExpiryTime());
        userRepository.save(newUser);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void changePassword(User user, String password) {
        if (!checkUserExist(user)) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean checkUserExist(User user) {
        // System.out.println("Checking user: email=" + user.getEmail() + ", username=" + user.getUsername());
        return findByUsername(user.getUsername()) != null || findByEmail(user.getEmail()) != null;
    }
}
