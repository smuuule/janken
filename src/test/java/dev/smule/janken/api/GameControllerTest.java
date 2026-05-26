package dev.smule.janken.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void createsAndFetchesGame() throws Exception {
    var createResult = mockMvc.perform(post("/api/games")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Lisa\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value("WAITING_FOR_OPPONENT"))
        .andExpect(jsonPath("$.player1").value("Lisa"))
        .andReturn();

    var gameId = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

    mockMvc.perform(get("/api/games/{id}", gameId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(gameId))
        .andExpect(jsonPath("$.player1").value("Lisa"));
  }

  @Test
  void rejectsOutOfOrderMoves() throws Exception {
    var createResult = mockMvc.perform(post("/api/games")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Lisa\"}"))
        .andExpect(status().isCreated())
        .andReturn();

    var gameId = JsonPath.read(createResult.getResponse().getContentAsString(), "$.id");

    mockMvc.perform(post("/api/games/{id}/move", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Lisa\",\"move\":\"ROCK\"}"))
        .andExpect(status().isConflict());

    mockMvc.perform(post("/api/games/{id}/join", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Pelle\"}"))
        .andExpect(status().isOk());

    mockMvc.perform(post("/api/games/{id}/move", gameId)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Pelle\",\"move\":\"SCISSORS\"}"))
        .andExpect(status().isConflict());
  }
}
