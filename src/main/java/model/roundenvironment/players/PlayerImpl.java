package model.roundenvironment.players;

import java.util.Optional;

import model.roundenvironment.coordinate.Coordinate;

/**
 * Class that models a standard game player
 * @author luca
 *
 */
public class PlayerImpl implements Player{
	
	private String nickname;
	private Coordinate coordinate;
	private int availableBarriers;
	private Integer finishLine;
	
	/**
	 * game player
	 * @param nickname
	 * @param coordinate
	 * @param availableBarriers
	 * @param finishLine
	 */
	public PlayerImpl(final String nickname, final Coordinate coordinate, final Optional<Integer> availableBarriers, final Integer finishLine) {
		this.nickname = nickname;
		this.coordinate = coordinate;
		this.availableBarriers = availableBarriers.isEmpty() ? DEFAULT_BARRIERS_NUMBER : availableBarriers.get(); 
		this.finishLine = finishLine;
	}
	
	@Override
	public String getNickname() {
		return this.nickname;
	}

	@Override
	public int getAvailableBarriers() {
		return this.availableBarriers;
	}
	
	@Override
	public void setAvailableBarriers(int availableBarriers) {
		this.availableBarriers = availableBarriers;
	}
	
	@Override
	public boolean isWinner() {
		return this.coordinate.getY().equals(this.getFinishLine());
	}

	@Override
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
		
	@Override
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	private Integer getFinishLine() {
		return finishLine;
	}

}
