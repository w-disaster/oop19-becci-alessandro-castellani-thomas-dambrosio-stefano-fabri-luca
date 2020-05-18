package tests;

import java.util.*;
import org.junit.*;
import controller.StandardGameControllerImpl;
import model.*;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.RoundPlayers;

import static org.junit.Assert.*;

public class ControllerTest {
	
	@Test
    public void testMovePlayer() {
		StandardGameControllerImpl controller = new StandardGameControllerImpl("player1", "player2");
		Model<RoundEnvironment> model = controller.getModel();
		RoundPlayers players = model.getCurrentRoundEnvironment().getRoundPlayers();
		assertEquals(players.getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(0,0));
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
	
	@Test
    public void testBarrierPlacer() {
		StandardGameControllerImpl controller = new StandardGameControllerImpl("player1", "player2");
		Model<RoundEnvironment> model = controller.getModel();
		RoundPlayers players = model.getCurrentRoundEnvironment().getRoundPlayers();
        assertEquals(players.getCurrentPlayer().getNickname(), "player1");
        controller.placeBarrier(new Coordinate(8,0), Orientation.HORIZONTAL);
        assertEquals(players.getCurrentPlayer().getNickname(), "player1");
	}
}
