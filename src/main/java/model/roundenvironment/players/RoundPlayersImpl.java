package model.roundenvironment.players;

import java.util.List;
import java.util.Optional;

/**
 * Round players class
 * @author luca
 *
 */
public class RoundPlayersImpl implements RoundPlayers {
	
	private Optional<Player> currentPlayer;
	private List<Player> players;
	
	/**
	 * Round players from existing players
	 * @param players
	 */
	public RoundPlayersImpl(final List<Player> players) {
		super();
		this.players = players;
		this.currentPlayer = Optional.empty();
	}

	@Override
	public Optional<Player> getCurrentPlayer() {
		return this.currentPlayer;
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}
	
	@Override
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = Optional.of(player);
	}

	@Override
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
}
