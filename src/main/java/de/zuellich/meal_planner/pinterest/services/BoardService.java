package de.zuellich.meal_planner.pinterest.services;

import de.zuellich.meal_planner.pinterest.datatypes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A service to call the Pinterest Board API.
 */
@Service
public class BoardService {

    public final String USERS_BOARDS = "https://api.pinterest.com/v1/me/boards";

    public final String BOARDS_PINS = "https://api.pinterest.com/v1/boards/{id}/pins/?fields=id,link";

    private static final String GET_BOARD = "https://api.pinterest.com/v1/boards/{id}/?fields=id,name,url";

    private OAuth2RestTemplate restTemplate;

    /**
     * @param restTemplate A rest template that manages our OAuth2 access tokens etc.
     */
    @Autowired
    public BoardService(OAuth2RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @return A list of the users boards or an empty list of none found.
     */
    public List<Board> getBoards() {
        try {
            ResponseEntity<BoardList> boards = restTemplate.getForEntity(USERS_BOARDS, BoardList.class);
            return boards.getBody().getBoards();
        } catch (RestClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Pin> getPins(String boardId) {
        Map<String, String> requestParameter = new HashMap<>(2);
        requestParameter.put("id", boardId);

        ResponseEntity<PinList> pins = restTemplate.getForEntity(BOARDS_PINS, PinList.class, requestParameter);
        return pins.getBody().getPins();
    }

    /**
     * Retrieve a listing of the boards basic properties and its pins.
     * @param boardId The board to retrieve.
     * @return The listing.
     */
    public BoardListing getBoardListing(String boardId) {
        ResponseEntity<BoardRequest> board = restTemplate.getForEntity(GET_BOARD, BoardRequest.class, boardId);
        List<Pin> pins = getPins(boardId);

        BoardListing result = new BoardListing();
        result.setBoard(board.getBody().getBoard());
        result.setPins(pins);

        return result;
    }
}
