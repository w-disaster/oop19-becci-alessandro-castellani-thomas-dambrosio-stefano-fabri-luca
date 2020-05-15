package tests;

import java.util.*;
import org.junit.*;
import controller.StandardGameControllerImpl;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.RoundPlayers;

import static org.junit.Assert.*;

public class ControllerTest {
	
	@Test
    public void testBasicMovePlayer() {
		StandardGameControllerImpl controller = new StandardGameControllerImpl("player1", "player2");
		Model<RoundEnvironment> model = controller.getModel();
		RoundPlayers players = model.getCurrentRoundEnvironment().getRoundPlayers();
        assertEquals(players.getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(2,0));
        assertEquals(players.getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(3,0));
        assertEquals(players.getCurrentPlayer().getNickname(), "player2");
        controller.movePlayer(new Coordinate(4,7));
        assertEquals(players.getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(4,1));
        assertEquals(players.getCurrentPlayer().getNickname(), "player1");
	}
	
}
