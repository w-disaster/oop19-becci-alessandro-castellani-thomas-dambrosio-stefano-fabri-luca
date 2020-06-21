package model;

import java.util.ArrayList;
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

public class ModelFactoryImpl implements ModelFactory {
	
	/**
	 * method to minimise repetitions
	 * @param nickname1
	 * @param nickname2
	 * @return
	 */
	private static Pair<RoundBarriers, RoundPlayers> commonEnvironmentsObjectsOfStandard(String nickname1, String nickname2){
		Coordinate coordinate1 = new Coordinate(Model.BOARD_DIMENSION/2, 0);
		Coordinate coordinate2 = new Coordinate(Model.BOARD_DIMENSION/2, Model.BOARD_DIMENSION - 1);
				
		List<Player> playersList = Stream.of(new PlayerImpl(nickname1, coordinate1, Player.DEFAULT_BARRIERS_NUMBER, Model.BOARD_DIMENSION - 1),  
				new PlayerImpl(nickname2, coordinate2, Player.DEFAULT_BARRIERS_NUMBER, 0))
				.collect(Collectors.toList());
		
		RoundBarriers barriers = new RoundBarriersImpl();
		RoundPlayers players = new RoundPlayersImpl(playersList);
		
		return new Pair<>(barriers, players);
	}

	@Override
	public Model<RoundEnvironment> buildStandard(final String nickname1, final String nickname2) {
		List<RoundEnvironment> roundEnvironments = new ArrayList<>();
		
		for (int i = 0; i < Model.NUMBER_OF_ROUNDS; i++) {
			Pair<RoundBarriers, RoundPlayers> roundObjects = commonEnvironmentsObjectsOfStandard(nickname1, nickname2);
			RoundEnvironment environment = new RoundEnvironmentImpl(roundObjects.getX(), roundObjects.getY());
			roundEnvironments.add(environment);
		}
		
		return new ModelImpl<>(roundEnvironments, Model.BOARD_DIMENSION, roundEnvironments.get(0), new ArrayList<>());
	}
	
	public Model<RoundPUpEnvironment> buildWithPowerUps(String nickname1, String nickname2) {
		List<RoundPUpEnvironment> roundPowerUpEnvironments = new ArrayList<>();
		
		for (int i = 0; i < Model.NUMBER_OF_ROUNDS; i++) {
			Pair<RoundBarriers, RoundPlayers> roundObjects = commonEnvironmentsObjectsOfStandard(nickname1, nickname2);
			RoundPowerUps roundPowerUps = new RoundPowerUpsImpl();
			RoundPUpEnvironment powerUpEnvironment = new RoundPUpEnvironmentImpl(roundObjects.getX(), roundObjects.getY(), roundPowerUps);
			roundPowerUpEnvironments.add(powerUpEnvironment);
		}
		
		return new ModelImpl<>(roundPowerUpEnvironments, Model.BOARD_DIMENSION, roundPowerUpEnvironments.get(0), new ArrayList<>());
	}
	
	
	public <X extends RoundEnvironment> Model<X> buildFromExisting(List<X> roundEnvironments, int boardDimension){
		Map<X, Optional<Player>> map = roundEnvironments.stream()
				.map(re -> new Pair<X, Optional<Player>>(re, re.getRoundPlayers().getPlayers().stream()
						.filter(p -> p.isWinner())
						.findFirst()))
				.collect(Collectors.toMap(p -> p.getX(), p -> p.getY()));
		
		X currentRoundEnvironment = map.entrySet().stream()
				.filter(e -> e.getValue().isEmpty())
				.map(e -> e.getKey())
				.collect(Collectors.toList())
				.get(0);

		List<Player> winners = map.values().stream()
				.filter(o -> o.isPresent())
				.map(o -> o.get())
				.collect(Collectors.toList());
		
		return new ModelImpl<>(roundEnvironments, boardDimension, currentRoundEnvironment, winners);
	}

}
