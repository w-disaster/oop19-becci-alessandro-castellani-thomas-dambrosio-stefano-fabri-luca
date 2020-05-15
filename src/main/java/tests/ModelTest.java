package tests;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.*;
import model.roundenvironment.*;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayersImpl;

public class ModelTest {

	public static final void main() {
		// al posto di "new ArrayList<Player>" metti una lista di player con le coordinate iniziali di ciascuno
		Model<RoundEnvironment> model = new ModelFactoryImpl<RoundEnvironment>()
				.build(Stream.iterate(new RoundEnvironmentImpl(new RoundBarriersImpl(), new RoundPlayersImpl(new ArrayList<Player>())), i -> i)
						.limit(ModelImpl.NUMBER_ROUNDS).collect(Collectors.toList()));
		
		//model.setCurrentRoundEnvironment(model.getGameRoundsEnvironments().get((model.getGameRoundsEnvironments().indexOf(model.getCurrentRoundEnvironment()) + 1)));
	}
	
}
