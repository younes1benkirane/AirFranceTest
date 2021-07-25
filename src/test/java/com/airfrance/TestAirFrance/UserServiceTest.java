package com.airfrance.TestAirFrance;

import com.airfrance.AirFranceTest.entity.User;
import com.airfrance.AirFranceTest.repository.UserRepository;
import com.airfrance.AirFranceTest.service.UserService;
import com.airfrance.AirFranceTest.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     This class test userService Methods
 * </p>
 *
 * @author Younes Benkirane
 */
@SpringBootTest(classes = UserServiceTest.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    User userOne=new User();
    User userTwo=new User();


    @Before
    public void setUp() {
        userOne = new User("superAdmin","Younes","03/05/1989","France");
        userTwo=new User("admmin","Amine","03/05/1999","France");
    }

    /**
     * <p>
     *     This method test findALl method
     * </p>
     */
    @Test
    public void getAllUserTestCase() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(userOne, userTwo));
        assertThat(userService.findAll().size()).isEqualTo(2);
    }

    /**
     * <p>
     *     this method test existence of login
     * </p>
     */
    @Test
    public void findByLoginTestCase() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(userOne));
        assertThat(userService.getUserByLogin("superAdmin").get().getLogin()).isEqualTo(userOne.getLogin());
    }


    /**
     * <p>
     *     this method throw Error if login not  exist
     * </p>
     */
    @Test
    public void loginNotExistTestCase() throws AssertionError{
        try {
            when(userRepository.findByLogin("jack")).thenReturn(Optional.empty());
        }catch(AssertionError error){
            throw error;
        }
    }

    /**
     * <p>
     *      this method test create a new user
     * </p>
     */
    @Test
    public void saveUserTestCase() {
        when(userRepository.save(userTwo)).thenReturn(userTwo);
        assertThat(userService.save(userTwo)).isNotNull();
    }

    /**
     * <p>
     *     this method test findAge between 2 dates
     * </p>
     */
    @Test
    public void findAgeTestCase() {
        // The age must be 32 at actual year and more from next and above
        assertThat(userService.findAge(userOne)).isGreaterThanOrEqualTo(32);
    }

}
