package controller.genericmove;

import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import savings.save.SaveLeaderBoard;
import savings.save.SaveLeaderBoardImpl;
import view.game.ViewLogic;

/**
 * @param <X>
 */
public abstract class GenericMove<X extends RoundEnvironment> {

    private final Model<X> model;
    private final ViewLogic view;
    private final SaveLeaderBoard leaderboard;
    private final RoundPlayers players;
    private final List<Player> turns;
    private final Iterator<X> iterRounds;

    public GenericMove(final Model<X> model, final ViewLogic view, final Iterator<X> iterRounds) {
		this.model = model;
		this.view = view;
		this.leaderboard = new SaveLeaderBoardImpl();
		this.iterRounds = iterRounds;
		this.players = this.model.getCurrentRoundEnvironment().getRoundPlayers();
		this.turns = this.players.getPlayers();
		if (!this.existingPlayer()) {
			if (this.model.getWinners().size() % 2 == 0) {
				this.players.setCurrentPlayer(this.turns.get(0));
			} else {
				this.players.setCurrentPlayer(this.turns.get(1));
			}
		}
	}
	
	private boolean existingPlayer() {
		//need to check if the current player is setted (the game may be loaded)
		if (!(this.players.getCurrentPlayer() == null)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method that changes the players turn.
	 */
	protected final void changeTurn() {
		final List<String> nicknames = List.copyOf(this.turns).stream().map(Player::getNickname).collect(Collectors.toList());
		int index = nicknames.indexOf(this.players.getCurrentPlayer().getNickname());
		if (index != this.turns.size() - 1) {
			this.players.setCurrentPlayer(this.turns.get(index + 1));
		} else {
			this.players.setCurrentPlayer(this.turns.get(0));
		}
	}
	
	/*
	 * Method that changes the game round
	 */
	protected final void changeRound() {
		if (this.iterRounds.hasNext()) {
			//i need to check if a player have already won so i don't pass to the next round
			final String currentPlayer = this.players.getCurrentPlayer().getNickname();
			long nWins = this.model.getWinners().stream().peek(Player::getNickname)
													.filter(p -> p.getNickname().compareTo(currentPlayer) == 0)
													.count();
			//if the current player won half of the rounds he won the game
			if (nWins > this.model.getGameRoundEnvironments().size() / 2) {
				System.out.println("Game Over!" + currentPlayer + " won!");
				this.leaderboard.updateLeaderBoard(currentPlayer);
				this.view.endGame(currentPlayer);
			} else {
				this.model.setCurrentRoundEnvironment(this.iterRounds.next());
				this.view.endRound(currentPlayer);
			}

		} else {
			System.out.println("All rounds finished, game over");
			//now i check who won more rounds and set him winner of the game
			final List<String> winnersNicknames = this.model.getWinners().stream().map(Player::getNickname).collect(Collectors.toList());
			final String winner = winnersNicknames.stream()
			        							.reduce(BinaryOperator.maxBy((o1, o2) -> Collections.frequency(winnersNicknames, o1)
			        									- Collections.frequency(winnersNicknames, o2)))
			        							.orElse(null);
			System.out.println("Game Over!" + winner + " won!");
			this.leaderboard.updateLeaderBoard(winner);
			this.view.endGame(winner);
		}
	}
	
	/**
	 * @param roundBarriers
	 * @param coordinate
	 * @param orientation
	 * @return true if there's an existing barrier in certain position and orientation
	 */
	protected boolean containsBarrierTypeIndipendent(final RoundBarriers roundBarriers, final Coordinate coordinate, final Orientation orientation) {
		return roundBarriers.getBarriersAsList().stream()
				.filter(b -> b.getCoordinate().equals(coordinate) && b.getOrientation().equals(orientation))
				.collect(Collectors.toList())
				.size() > 0;
	}
	
	/**
	 * @param currentPlayer
	 * @return the player against the current player
	 */
	protected Optional<Player> getOtherPlayer(final Player currentPlayer) {
		for (final Player p : this.players.getPlayers()) {
			if (!p.equals(currentPlayer)) {
				return Optional.of(p);
			}
		}
		return Optional.empty();
	}
}
