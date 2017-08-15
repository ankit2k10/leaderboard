package com.leaderboard.e2e;

import com.leaderboard.TestApplication;
import com.leaderboard.TestConfiguration;
import com.leaderboard.dto.user.UserCreateDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
@Slf4j
public class UserResourceTest {
    private static String baseUrl;

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<TestConfiguration>(TestApplication.class, "src/test/resources/config/test.yaml");

    @BeforeClass
    public static void setUp() {
        int port = RULE.getLocalPort();
        baseUrl = String.format("http://localhost:%d", port);
    }

    @Test
    public void userCreationTest() throws Exception {
        Random random = new Random();

        UserCreateDto createDto = new UserCreateDto();

        Integer x = random.nextInt();
        createDto.setEmailId("email_xxx_" + x.toString());
        createDto.setName("user_xxx_" + x.toString());

        createUser(createDto, baseUrl);
    }

    private int createUser(UserCreateDto createDto, String baseUrl) {
        Client client = new Client();
        ClientResponse response = client.resource(baseUrl + "/user")
                .accept("application/json").type("application/json")
                .post(ClientResponse.class, createDto);

        assertThat(response.getStatus() == 201).isTrue();
        return response.getEntity(Integer.class);
    }

}
