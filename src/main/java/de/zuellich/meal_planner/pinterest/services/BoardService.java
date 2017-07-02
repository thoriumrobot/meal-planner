package de.zuellich.meal_planner.pinterest.services;

import de.zuellich.meal_planner.pinterest.datatypes.Board;
import de.zuellich.meal_planner.pinterest.datatypes.BoardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * A service to call the Pinterest Board API.
 */
@Service
public class BoardService {

    public final String BOARD_URL = "https://api.pinterest.com/v1/me/boards?access_token={token}";

    private RestTemplate restTemplate;

    /**
     * @param restTemplateBuilder The RestTemplate instance used to make the requests.
     */
    @Autowired
    public BoardService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * @param accessToken The access token required by the Pinterest API.
     * @return A list of the users boards or an empty list of none found.
     */
    public List<Board> getBoards(String accessToken) {
        ResponseEntity<BoardList> boards = restTemplate.getForEntity(BOARD_URL, BoardList.class, accessToken);
        return boards.getBody().getBoards();
    }

}
