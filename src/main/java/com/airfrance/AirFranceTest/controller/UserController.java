package com.airfrance.AirFranceTest.controller;

import com.airfrance.AirFranceTest.dto.UserDto;
import com.airfrance.AirFranceTest.entity.User;
import com.airfrance.AirFranceTest.entity.validator.UserValidation;
import com.airfrance.AirFranceTest.mapper.ObjectMapper;
import com.airfrance.AirFranceTest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *    REST API for register user and display her details
 * </p>
 *
 * @author Younes Benkirane
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private  UserService userService;


    private ObjectMapper objectMapper;

    @Autowired
    private UserValidation userValidation;


    public UserController(UserService userService, UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }


    public UserController(){

    }


    /**
     * <p>
     *     this method retrieve all users
     * </p>
     * @return
     */
    @GetMapping({"", "/"})
    public List<UserDto> findAll()  {
        return ObjectMapper.mapAll(userService.findAll(),UserDto.class) ;
    }

    /**
     * <p>
     *     this method retrieve user by login and print exception if user not exist
     * </p>
     * @param login
     * @return
     */
    @GetMapping("/{login}")
    public UserDto getUserByLogin(@PathVariable("login") String login) {
        return ObjectMapper.map(userService.getUserByLogin(login).orElseThrow(ClassCastException::new),UserDto.class);
    }

    /**
     * <p>
     *     this method create a new user
     * </p>
     * @param userDto
     * @param bindingResult
     * @return
     */

    @PostMapping
    public ResponseEntity<UserDto> newUser(@RequestBody UserDto userDto, BindingResult bindingResult) {
        User user=ObjectMapper.map(userDto,User.class);
        logger.debug("REST request to create a new  User : {}", user);
        userValidation.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.debug(bindingResult.toString());
            return new ResponseEntity("user validations failed: \n" + bindingResult.getAllErrors().stream()
                    .map(e -> " -"+e.getDefaultMessage()).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            user =userService.save(user);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }
}
