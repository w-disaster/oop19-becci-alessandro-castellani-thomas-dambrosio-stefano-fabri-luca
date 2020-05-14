package model.roundenvironment;
import model.roundenvironment.barriers.*;
import model.roundenvironment.players.*;


public interface RoundEnvironment extends BarrierEnvironment{
	
	/**
	 * 
	 * @return current round barriers
	 */
	//RoundBarriers getRoundBarriers();
	
	/**
	 * 
	 * @return current round players
	 */
	RoundPlayers getRoundPlayers();

}
