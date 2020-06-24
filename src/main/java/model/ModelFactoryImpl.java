package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.RoundPUpEnvironmentImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;
import model.roundenvironment.powerups.RoundPowerUps;
import model.roundenvironment.powerups.RoundPowerUpsImpl;

/**
 * The Class ModelFactoryImpl.
 */
public class ModelFactoryImpl implements ModelFactory {
	
	/**
	 * method to minimise repetitions.
	 *
	 * @param nickname1 the nickname 1
	 * @param nickname2 the nickname 2
	 * @return the pair
	 */
	private static Pair<RoundBarriers, RoundPlayers> commonEnvironmentsObjects(String nickname1, String nickname2){
		Coordinate coordinate1 = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate coordinate2 = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
				
		List<Player> playersList = Stream.of(new PlayerImpl(nickname1, coordinate1, Player.DEFAULT_BARRIERS_NUMBER, Model.BOARD_DIMENSION - 1),  
				new PlayerImpl(nickname2, coordinate2, Player.DEFAULT_BARRIERS_NUMBER, 0))
				.collect(Collectors.toList());
		
		RoundBarriers barriers = new RoundBarriersImpl(Model.BOARD_DIMENSION);
		RoundPlayers players = new RoundPlayersImpl(playersList);
		
		return new Pair<>(barriers, players);
	}

	@Override
	public Model<RoundEnvironment> buildStandard(String nickname1, String nickname2) {
		List<RoundEnvironment> roundEnvironments = new ArrayList<>();
		
		for (int i = 0; i < Model.NUMBER_OF_ROUNDS; i++) {
			Pair<RoundBarriers, RoundPlayers> roundObjects = commonEnvironmentsObjects(nickname1, nickname2);
			RoundEnvironment environment = new RoundEnvironmentImpl(roundObjects.getX(), roundObjects.getY());
			roundEnvironments.add(environment);
		}
		
		return new ModelImpl<>(roundEnvironments, Model.BOARD_DIMENSION, roundEnvironments.get(0), new ArrayList<>());
	}
	
	public Model<RoundPUpEnvironment> buildWithPowerUps(String nickname1, String nickname2) {
		List<RoundPUpEnvironment> roundPowerUpEnvironments = new ArrayList<>();
		
		for (int i = 0; i < Model.NUMBER_OF_ROUNDS; i++) {
			Pair<RoundBarriers, RoundPlayers> roundObjects = commonEnvironmentsObjects(nickname1, nickname2);
			RoundPowerUps roundPowerUps = new RoundPowerUpsImpl();
			RoundPUpEnvironment powerUpEnvironment = new RoundPUpEnvironmentImpl(roundObjects.getX(), roundObjects.getY(), roundPowerUps);
			roundPowerUpEnvironments.add(powerUpEnvironment);
		}
		
		return new ModelImpl<>(roundPowerUpEnvironments, Model.BOARD_DIMENSION, roundPowerUpEnvironments.get(0), new ArrayList<>());
	}
	
	public <X extends RoundEnvironment> Model<X> buildFromExisting(List<X> roundEnvironments, int boardDimension){
		Map<X, Optional<Player>> map = new HashMap<>();
		X currentRoundEnvironment = null;
		
		List<Player> winners = new ArrayList<>();
		for(X x : roundEnvironments) {
			List<Player> l = x.getRoundPlayers().getPlayers().stream()
					.filter(p -> p.isWinner())
					.collect(Collectors.toList());
			if(l.size() > 0) {
				winners.addAll(l);
				map.put(x, Optional.of(l.get(0)));
			} else {
				if(currentRoundEnvironment == null) {
					currentRoundEnvironment = x;
				}
				map.put(x, Optional.empty());
			}
		}
		return new ModelImpl<>(roundEnvironments, boardDimension, currentRoundEnvironment, winners);
	}

}
