package de.zuellich.meal_planner.pinterest.datatypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Represents a list of boards. Helper for JSON serialization. */
public class BoardList {

  @JsonProperty(value = "data")
  private List<Board> boards;

  public List<Board> getBoards() {
    return boards;
  }

  public void setBoards(List<Board> boards) {
    this.boards = boards;
  }
}
