package com.cssw.tutorial.game;

import com.cssw.tutorial.game.model.Game;
import com.cssw.tutorial.game.model.GameDTO;

import java.util.List;

public interface GameService {
    Game get(Long id);

    List<Game> find(String title, Long idCategory);

    void save(Long id, GameDTO dto);
}
