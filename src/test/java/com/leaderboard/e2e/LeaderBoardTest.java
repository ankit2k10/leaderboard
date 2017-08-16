package com.leaderboard.e2e;

import com.leaderboard.TestApplication;
import com.leaderboard.TestConfiguration;
import com.leaderboard.dto.board.BoardCreateDto;
import com.leaderboard.dto.user.UserCreateDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import io.dropwizard.testing.junit.DropwizardAppRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ankit.chaudhury on 15/08/17.
 */
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeaderBoardTest {
    private static int boardId;
    private static List<Integer> userIds;
    private static List<Integer> sortedList;

    private static String baseUrl;

    private static int userCount = 1000;
    private static int maxScore = 50;

    @ClassRule
    public static final DropwizardAppRule<TestConfiguration> RULE =
            new DropwizardAppRule<TestConfiguration>(TestApplication.class, "src/test/resources/config/test.yaml");

    @BeforeClass
    public static void setUp() {
        int port = RULE.getLocalPort();
        baseUrl = String.format("http://localhost:%d", port);
    }

    @Test
    public void t1CreateBoardTest() {
        int id = createBoard(new BoardCreateDto("my-board", 0, maxScore));
        this.boardId = id;
        log.info("Board id: " + id);
    }

    @Test
    public void t2CreateUsersTest() {
        UserCreateDto createDto = new UserCreateDto();
        userIds = new ArrayList<Integer>();
        for (int i = 0; i < userCount; i++) {
            createDto.setEmailId("email_" + i);
            createDto.setName("user_" + i);
            int userId = createUser(createDto, baseUrl);
            userIds.add(userId);
        }
    }

    @Test
    public void t3UpdateScoreTest() {
        assertThat(boardId != 0).isTrue();
        Random random = new Random();
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (Integer id : userIds) {
            int score = random.nextInt(maxScore + 1);
            updateScore(boardId, id, score);
            if (!map.containsKey(score)) {
                map.put(score, new ArrayList<Integer>());
            }
            map.get(score).add(id);
        }

        sortedList = new ArrayList<Integer>();
        for (int i = maxScore; i >= 0; i--) {
            if (map.containsKey(i)) {
                sortedList.addAll(map.get(i));
            }
        }
    }

    @Test
    public void t4TopNTest() {
        assertThat(boardId != 0).isTrue();
        List<Integer> userIds = getTop(boardId, 100);
        log.info("Top n : " + userIds);
        for (int i = 0; i < userIds.size(); i++) {
            assertThat(userIds.get(i) == sortedList.get(i));
        }
    }

    @Test
    public void t5RelativeRankTest() {
        assertThat(boardId != 0).isTrue();
        int n1 = 50, n2 = 50;
        for (int i = 0; i < 5; i++) {
            for(int j = 0; j< 5; j++) {
                int userId = new Random().nextInt(userIds.size());
                validateRelativeRank(userId, n1, n2);
            }
            t3UpdateScoreTest();
        }
    }

    private void validateRelativeRank(int userId, int n1, int n2) {
        List<Integer> relatives = getRelative(userId, n1, n2);
        int index1 = relatives.indexOf(userId);
        int index2 = userIds.indexOf(userId);

        for (int j = index1 - 1, k = index2 - 1; j >= 0; j--, k--) {
            assertThat(userIds.get(k) == relatives.get(j));
        }

        for (int j = index1 + 1, k = index2 + 1; j < relatives.size(); j++, k++) {
            assertThat(userIds.get(k) == relatives.get(j));
        }
    }

    private List<Integer> getRelative(int userId, int n1, int n2) {
        Client client = new Client();
        ClientResponse response = client.resource(
                String.format(baseUrl + "/board/%d/relative/%d?n1=%d&n2=%d", boardId, userId, n1, n2))
                .accept("application/json").type("application/json")
                .get(ClientResponse.class);

        assertThat(response.getStatus() == 200).isTrue();
        return response.getEntity(new GenericType<List<Integer>>() {
        });
    }

    private void updateScore(int boardId, int userId, int score) {
        Client client = new Client();
        ClientResponse response = client.resource(
                String.format(baseUrl + "/board/%d/user/%d/%d", boardId, userId, score))
                .accept("application/json").type("application/json")
                .put(ClientResponse.class);

        assertThat(response.getStatus() == 204).isTrue();
    }

    private int createBoard(BoardCreateDto createDto) {
        Client client = new Client();
        try {
            ClientResponse response = client.resource(baseUrl + "/board")
                    .accept("application/json").type("application/json")
                    .post(ClientResponse.class, createDto);

            assertThat(response.getStatus() == 201).isTrue();

            return response.getEntity(Integer.class);
        } catch (Exception e) {
            log.error("createBoard exception ", e);
        }
        return 0;
    }

    private List<Integer> getTop(int boardId, int n) {
        Client client = new Client();
        ClientResponse response = client.resource(
                String.format(baseUrl + "/board/%d/top?n=%d", boardId, n))
                .accept("application/json").type("application/json")
                .get(ClientResponse.class);

        assertThat(response.getStatus() == 200).isTrue();
        return response.getEntity(new GenericType<List<Integer>>() {
        });
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
