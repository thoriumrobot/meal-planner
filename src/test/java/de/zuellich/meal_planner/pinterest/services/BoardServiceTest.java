package de.zuellich.meal_planner.pinterest.services;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.pinterest.datatypes.Board;
import de.zuellich.meal_planner.pinterest.datatypes.BoardListing;
import de.zuellich.meal_planner.pinterest.datatypes.Pin;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 *
 */
public class BoardServiceTest extends FixtureBasedTest {

    private static final String EXAMPLE_ACCESS_TOKEN = "abcdef";
    private static final String EXAMPLE_BOARD_ID = "111111111111111111";

    /**
     * Get a ready set up instance of the BoardService.
     *
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
        final String responseJSON = getResource("/fixtures/pinterest/responses/v1/me_boards.json");

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(requestTo("https://api.pinterest.com/v1/me/boards?access_token=abcdef"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJSON, MediaType.APPLICATION_JSON));

        BoardService service = getBoardService(restTemplate);
        List<Board> boards = service.getBoards(EXAMPLE_ACCESS_TOKEN);

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
        final String responseJSON = getResource("/fixtures/pinterest/responses/v1/board_pins.json");

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(requestTo("https://api.pinterest.com/v1/boards/111111111111111111/pins/?fields=id,link&access_token=abcdef"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJSON, MediaType.APPLICATION_JSON));

        BoardService service = getBoardService(restTemplate);
        List<Pin> pins = service.getPins(EXAMPLE_BOARD_ID, EXAMPLE_ACCESS_TOKEN);

        server.verify();
        assertEquals("5 pins are returned", 5, pins.size());
        assertPinsNotEmptyOrNull(pins);
    }

    /**
     * Verify that all pins in the list have an id and link that is not empty or null.
     * @param pins The list of pins to verify. Make sure the list is not empty.
     */
    private void assertPinsNotEmptyOrNull(List<Pin> pins) {
        for (Pin pin : pins) {
            assertFalse("Pin's id should be set.", pin.getId().isEmpty());
            assertFalse("Pin's link should be set.", pin.getLink().isEmpty());
        }
    }

    @Test
    public void returnsABoardWithItsPins() {
        final String boardResponseJSON = getResource("/fixtures/pinterest/responses/v1/get_board.json");
        final String pinResponseJSON = getResource("/fixtures/pinterest/responses/v1/board_pins.json");

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        // first mock the board retrieval
        server.expect(requestTo("https://api.pinterest.com/v1/boards/" + EXAMPLE_BOARD_ID + "/?fields=id,name,url&access_token=abcdef"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(boardResponseJSON, MediaType.APPLICATION_JSON));

        // now also mock the pin retrieval
        server.expect(requestTo("https://api.pinterest.com/v1/boards/111111111111111111/pins/?fields=id,link&access_token=abcdef"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(pinResponseJSON, MediaType.APPLICATION_JSON));

        BoardService service = getBoardService(restTemplate);
        BoardListing result = service.getBoardListing(EXAMPLE_BOARD_ID, EXAMPLE_ACCESS_TOKEN);

        Board resultBoard = result.getBoard();
        assertEquals("The board name is returned.", "Board name", resultBoard.getName());
        assertEquals("The board link is returned.", "https://www.pinterest.com/user/board-name/", resultBoard.getUrl());
        assertEquals("The board id is returned.", "1111111111111111111", resultBoard.getId());

        assertEquals("5 Pins are returned.", 5, result.getPins().size());
        assertPinsNotEmptyOrNull(result.getPins());
    }

}