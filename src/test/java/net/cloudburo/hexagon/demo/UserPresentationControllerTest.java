package net.cloudburo.hexagon.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.cloudburo.hexagon.demo.kernel.user.UserUseCaseRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserPresentationControllerTest.class)
public class UserPresentationControllerTest {
    // Taken out due to configuring mockMvc manually before tests
    //  @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserUseCaseRepository maintenanceUseCaseRepository;

    private static String BASE_PATH = "http://localhost:8080/presentation";
    private static String MEMBERSHIPS_PATH = "/maintain/user";
    private static final long ID = 1;
    // private Person person;

}
