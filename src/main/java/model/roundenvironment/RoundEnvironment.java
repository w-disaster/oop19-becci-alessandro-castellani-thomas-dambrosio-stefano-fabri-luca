package model.roundenvironment;

import model.roundenvironment.players.RoundPlayers;

/**
 * This inteface models the round environment for the standard or custom game that not contains power ups.
 * @author luca
 *
 */
public interface RoundEnvironment extends BarrierEnvironment { 
	
	/**
	 * 
	 * @return the players of the current round
	 */
	RoundPlayers getRoundPlayers();

}
