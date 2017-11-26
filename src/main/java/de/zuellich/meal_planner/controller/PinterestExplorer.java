package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.algorithms.RecipeService;
import de.zuellich.meal_planner.datatypes.Recipe;
import de.zuellich.meal_planner.exception.RecipeParseException;
import de.zuellich.meal_planner.pinterest.datatypes.Board;
import de.zuellich.meal_planner.pinterest.datatypes.BoardListing;
import de.zuellich.meal_planner.pinterest.datatypes.Pin;
import de.zuellich.meal_planner.pinterest.services.BoardService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** A controller that allows to explore a users pinterestOAuth2Configuration boards and pins. */
@RestController
public class PinterestExplorer {

  private final BoardService service;

  private final RecipeService recipeService;

  @Autowired
  public PinterestExplorer(BoardService service, RecipeService recipeService) {
    this.service = service;
    this.recipeService = recipeService;
  }

  @RequestMapping("/boards")
  public ResponseEntity<List<Board>> getBoards() {
    List<Board> boards = service.getBoards();
    return ResponseEntity.ok(boards);
  }

  @RequestMapping("/board")
  public ResponseEntity<BoardListing> getBoard(@RequestParam(value = "boardId") String boardId) {
    BoardListing board = service.getBoardListing(boardId);
    return ResponseEntity.ok(board);
  }

  @RequestMapping("/recipes")
  public ResponseEntity<List<Recipe>> getRecipes() throws InterruptedException {
    List<Board> boards = service.getBoards();
    List<Recipe> recipes = new ArrayList<>();

    for (Board board : boards) {
      System.out.println("Retrieving pins for board: " + board.getName());
      List<Pin> pins = service.getPins(board.getId());

      for (Pin pin : pins) {
        try {
          System.out.println("\r:: Download pin < " + pin.getLink() + " >");
          Recipe recipe = recipeService.fromURL(pin.getLink());

          if (recipe.getSource().isEmpty()) {
            recipe.setSource(pin.getLink());
          }

          recipes.add(recipe);

          Thread.sleep(100);
        } catch (RecipeParseException e) {
          System.out.println("!! Error downloading pin: " + e.getCause().getMessage());
        }
      }
    }

    System.out.println("\nDone.");

    return ResponseEntity.ok(recipes);
  }
}
