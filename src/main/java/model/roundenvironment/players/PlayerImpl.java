package model.roundenvironment.players;

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
	private int finishLine;
	
	/**
	 * game player
	 * @param nickname
	 * @param coordinate
	 * @param availableBarriers
	 * @param finishLine
	 */
	public PlayerImpl(final String nickname, final Coordinate coordinate, final int availableBarriers, final int finishLine) {
		this.nickname = nickname;
		this.coordinate = coordinate;
		this.availableBarriers = availableBarriers;
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

	@Override
	public int getFinishLine() {
		return finishLine;
	}

}
