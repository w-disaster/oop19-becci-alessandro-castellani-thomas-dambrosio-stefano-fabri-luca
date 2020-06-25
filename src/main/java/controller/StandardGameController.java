package controller;

/**
 * Controller for standard games.
 */
public interface StandardGameController extends GameController {

    /**
     * @param nicknamePlayer1
     * @param nicknamePlayer2
     */
    void newStandardGame(String nicknamePlayer1, String nicknamePlayer2);

}
