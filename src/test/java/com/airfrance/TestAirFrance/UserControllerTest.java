package com.airfrance.TestAirFrance;

import com.airfrance.AirFranceTest.controller.UserController;
import com.airfrance.AirFranceTest.dto.UserDto;
import com.airfrance.AirFranceTest.entity.User;
import com.airfrance.AirFranceTest.entity.validator.UserValidation;
import com.airfrance.AirFranceTest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.Validation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 *     This class tests  user controller
 * </p>
 *
 * @author  Younes Benkirane
 */
@SpringBootTest(classes = UserControllerTest.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Mock
    UserService userService;



    UserValidation userValidation = new UserValidation(userService);

    private UserController userController;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    User userOne = new User();
    UserDto userDto = new UserDto();
    List<User> users;


    /**
     * <p>
     *     initialize data before  execute test
     * </p>
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userOne = new User("admin","younes", "03/05/1989", "FRANCE");
        userOne.setPhoneNumber("0687654321");
        userOne.setGender("M");

        userDto.setLogin("amine");
        userDto.setFullName("amine benmansour");
        userDto.setBirthday("03/05/1999");
        userDto.setCountry("FRANCE");
        userDto.setPhoneNumber("0876543211");
        userDto.setGender("M");

        users = Arrays.asList(userOne,userOne,userOne,userOne,userOne,userOne);
        userValidation = new UserValidation(userService);
        userController = new UserController(userService,userValidation);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * <p>
     *     This method tests findALlUser
     * </p>
     * @throws Exception
     */
    @Test
    public void findAllUserReturnAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(users);

        MvcResult mvcResult = mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * <p>
     *     This method tests getUserByLogin
     * </p>
     * @throws Exception
     */
   @Test
    public void getUserByLoginTestCase() throws Exception {
        when(userService.getUserByLogin(userOne.getLogin())).thenReturn(Optional.ofNullable(userOne));

          MvcResult mvcResult = mockMvc.perform(get("/user/admin")).andExpect(status().isOk())
                .andReturn();
    }

    /**
     * <p>
     *     this method test create new user
     * </p>
     * @throws Exception
     */
    @Test
    public void createUserTestCase() throws Exception {
        when(userService.save(userOne)).thenReturn(userOne);
        when(userService.getUserByLogin(any())).thenReturn(Optional.empty());
        when(userService.findAge(any())).thenReturn(32);

        MvcResult mvcResult = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn();
           }

}
