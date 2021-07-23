package com.airfrance.AirFranceTest.entity.validator;

import com.airfrance.AirFranceTest.entity.User;
import com.airfrance.AirFranceTest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * <p>
 *      this component valider user
 * </p>
 * @author Younes Benkirane
 */
@Component
public class UserValidation implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(UserValidation.class);
    private static final int AGE = 18;
    private static final Pattern PATTERN_DATE = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    private final UserService userService;

    public UserValidation(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     *     this class support validation
     * </p>
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(User.class);
    }

    /**
     * <p>
     *     this method validate User Object
     * </p>
     * @param entity
     * @param errors
     */
    @Override
    public void validate(Object entity, Errors errors) {
        logger.debug("User validation {}", entity);
        if (entity instanceof User) {
            User user = (User) entity;
            requiredLoginValidation(user,errors);
            if(!errors.hasErrors()) {
                existUserValidation(user, errors);
                validateBirthday(user, errors);
                validateCountry(user, errors);
            }
        }
    }

    /**
     * <p>
     *     validate User Exists
     * </p>
     * @param user
     * @param errors
     */
    private void existUserValidation(User user, Errors errors) {
        Optional<User> userUnique = userService.getUserByLogin(user.getLogin());
        if (userUnique.isPresent())
            errors.reject("user error", "This user already exist");
    }


    /**
     * <p>
     *     this method validate login
     * </p>
     * @param user
     * @param errors
     */
    private void requiredLoginValidation(User user, Errors errors) {
        if (user.getLogin() == null){
                errors.reject("login error", "login is required");
            }
    }


    /**
     * <p>
     *     this method validate adult age 18 years old
     * </p>
     * @param user
     * @param errors
     */
    private void validateBirthday(User user, Errors errors) {
        if (!PATTERN_DATE.matcher(user.getBirthday()).matches()) {
            errors.reject("date format error", "date must be in dd/MM/yyyy format");
        }
        try {

            if (userService.findAge(user) < AGE) {
                errors.reject("age error", "you have to be older than 18");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * <p>
     *     this method validate french people
     * </p>
     * @param user
     * @param errors
     */
    private void validateCountry(User user, Errors errors) {
        if(!(user.getCountry().equalsIgnoreCase("France"))){
            errors.reject("country error", "you must be in France");
        }
    }

}
