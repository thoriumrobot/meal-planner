package de.zuellich.meal_planner.pinterest.services;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.MealPlanner;
import de.zuellich.meal_planner.pinterest.datatypes.Board;
import de.zuellich.meal_planner.pinterest.datatypes.Pin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 *
 */
public class BoardServiceTest extends FixtureBasedTest {

    /**
     * Get a ready set up instance of the BoardService.
     * @param restTemplate The RestTemplate instance to inject.
     * @return The service instance.
     */
    private BoardService getBoardService(RestTemplate restTemplate) {
        RestTemplateBuilder mockBuilder = mock(RestTemplateBuilder.class);
        when(mockBuilder.build()).thenReturn(restTemplate);
        return new BoardService(mockBuilder);
    }

    @Test
    public void returnsUsersBoards() {
        final String accessToken = "abcdef";
        final String responseJSON = getResource("/fixtures/pinterest/responses/v1/me_boards.json");

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(requestTo("https://api.pinterest.com/v1/me/boards?access_token=abcdef"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJSON, MediaType.APPLICATION_JSON));

        BoardService service = getBoardService(restTemplate);
        List<Board> boards = service.getBoards(accessToken);

        server.verify();
        assertEquals("Two boards are returned.", 2, boards.size());

        Board board = boards.get(0);
        assertEquals("https://www.pinterest.com/auser/board-name1/", board.getUrl());
        assertEquals("1", board.getId());
        assertEquals("Board Name 1", board.getName());

        board = boards.get(1);
        assertEquals("https://www.pinterest.com/auser/board2/", board.getUrl());
        assertEquals("2", board.getId());
        assertEquals("Board2", board.getName());
    }

    @Test
    public void returnsBoardsPins() {
        final String accessToken = "abcdef";
        final String boardId = "111111111111111111";
        final String responseJSON = getResource("/fixtures/pinterest/responses/v1/board_pins.json");

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(requestTo("https://api.pinterest.com/v1/boards/111111111111111111/pins/?fields=id,link&access_token=abcdef"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJSON, MediaType.APPLICATION_JSON));

        BoardService service = getBoardService(restTemplate);
        List<Pin> pins = service.getPins(boardId, accessToken);

        server.verify();
        assertEquals("5 pins are returned", 5, pins.size());
        for (Pin pin : pins) {
            assertFalse("Pin's id should be set.", pin.getId().isEmpty());
            assertFalse("Pin's link should be set.", pin.getLink().isEmpty());
        }
    }

}