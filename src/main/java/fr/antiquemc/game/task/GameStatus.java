package fr.antiquemc.game.task;

public enum GameStatus {

    LOBBY,
    GAME,
    END;

    private static GameStatus gameStatus;
    public static void setGameStatus(GameStatus status) {
        gameStatus = status;
    }

    public static boolean isGameStatus(GameStatus status) {
        return gameStatus == status;
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }

}
