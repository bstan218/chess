package chess.response;

import model.GameData;

import java.util.List;

public record ListGameResponse(String message, List<GameData> games) {
}
