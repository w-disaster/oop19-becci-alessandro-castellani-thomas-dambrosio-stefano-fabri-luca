package tests;

import java.util.*;
import org.junit.*;
import controller.StandardGameControllerImpl;
import model.*;
import model.roundenvironment.coordinate.Coordinate;

import static org.junit.Assert.*;

public class ControllerTest {
	
	@Test
    public void testBasicMovePlayer() {
		StandardGameControllerImpl controller = new StandardGameControllerImpl("player1", "player2");
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(2,0));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(3,0));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player2");
        controller.movePlayer(new Coordinate(4,7));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(4,1));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
	}
	
}
