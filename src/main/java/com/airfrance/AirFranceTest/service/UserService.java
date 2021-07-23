package com.airfrance.AirFranceTest.service;

import com.airfrance.AirFranceTest.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     Interface User Service for CRUD Operations
 * </p>
 *
 * @author  Younes Benkirane
 */
public interface UserService {

    List<User> findAll();

    Optional<User> getUserByLogin(String login);

    User save(User user);

    int findAge(User user);
}
