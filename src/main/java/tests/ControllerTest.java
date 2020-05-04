package tests;

import java.util.*;
import org.junit.*;
import controller.StandardGameControllerImpl;
import model.*;
import static org.junit.Assert.*;

public class ControllerTest {
	
	@Test
    public void testMovePlayer() {
		StandardGameControllerImpl controller = new StandardGameControllerImpl("player1", "player2");
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(0,2));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(0,3));
        assertEquals(controller.getGame().getCurrentPlayer().getCoordinate(), new Coordinate(0,3));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player2");
        assertEquals(controller.getGame().getCurrentPlayer().getCoordinate(), new Coordinate(8,4));
        controller.movePlayer(new Coordinate(7,4));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        controller.movePlayer(new Coordinate(1,4));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "player1");
        assertTrue(true);
	}
	
}
