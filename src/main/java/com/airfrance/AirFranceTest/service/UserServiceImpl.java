package com.airfrance.AirFranceTest.service;

import com.airfrance.AirFranceTest.entity.User;
import com.airfrance.AirFranceTest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     This is the userService implementation of User entity
 * </p>
 *
 * @author Younes Benkirane
 */
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * <p>
     *      this method is find all users
     * </p>
     * @return
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * <p>
     *      this method find user by login
     * </p>
     * @param login
     * @return
     */
    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    /**
     * <p>
     *      this method save validate user
     * </p>
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * <p>
     *     This method calculate age from user birthDay
     * </p>
     * @param user
     * @return
     */
    @Override
    public int findAge(User user) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthday= LocalDate.parse(user.getBirthday(), format);
        LocalDate today = LocalDate.now();
        int age = Period.between(birthday, today).getYears();
        return age;
    }
}
