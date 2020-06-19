package model;

import java.util.ArrayList;
import java.util.List;


import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;

public class ModelFactoryImpl implements ModelFactory {

	@Override
	public Model<RoundEnvironment> buildFromScratch(final String nickname1, final String nickname2) {
		List<RoundEnvironment> roundEnvironments = new ArrayList<>();
		Coordinate coordinate1 = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate coordinate2 = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
				
		for (int i = 0; i < Model.NUMBER_OF_ROUNDS; i++) {
			List<Player> playersList = new ArrayList<>();
			Player player1 = new PlayerImpl(nickname1, coordinate1, Player.DEFAULT_BARRIERS_NUMBER, Model.BOARD_DIMENSION - 1);
			Player player2 = new PlayerImpl(nickname2, coordinate2, Player.DEFAULT_BARRIERS_NUMBER, 0);
			playersList.add(player1);
			playersList.add(player2);
			RoundBarriers barriers = new RoundBarriersImpl();
			RoundPlayers players = new RoundPlayersImpl(playersList);
			RoundEnvironment environment = new RoundEnvironmentImpl(barriers,players);
			roundEnvironments.add(environment);
		}
		return new ModelImpl<>(roundEnvironments, Model.BOARD_DIMENSION, roundEnvironments.get(0), new ArrayList<>());
	}
	
	public Model<RoundEnvironment> buildWithPowerUp(String nicknamePlayer1, String nicknamePlayer2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO
	public <X extends RoundEnvironment> Model<X> buildFromExisting(List<X> roundEnvironments, int boardDimension){
		return null;
	}

	
	
}
