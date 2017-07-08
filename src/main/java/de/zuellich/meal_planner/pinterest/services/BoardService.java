package de.zuellich.meal_planner.pinterest.services;

import de.zuellich.meal_planner.pinterest.datatypes.Board;
import de.zuellich.meal_planner.pinterest.datatypes.BoardList;
import de.zuellich.meal_planner.pinterest.datatypes.Pin;
import de.zuellich.meal_planner.pinterest.datatypes.PinList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A service to call the Pinterest Board API.
 */
@Service
public class BoardService {

    public final String USERS_BOARDS = "https://api.pinterest.com/v1/me/boards?access_token={token}";

    public final String BOARDS_PINS = "https://api.pinterest.com/v1/boards/{id}/pins/?fields=id,link&access_token={token}";

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
        ResponseEntity<BoardList> boards = restTemplate.getForEntity(USERS_BOARDS, BoardList.class, accessToken);
        return boards.getBody().getBoards();
    }

    public List<Pin> getPins(String boardId, String accessToken) {
        Map<String, String> requestParameter = new HashMap<>(2);
        requestParameter.put("id", boardId);
        requestParameter.put("token", accessToken);

        ResponseEntity<PinList> pins = restTemplate.getForEntity(BOARDS_PINS, PinList.class, requestParameter);
        return pins.getBody().getPins();
    }
}
