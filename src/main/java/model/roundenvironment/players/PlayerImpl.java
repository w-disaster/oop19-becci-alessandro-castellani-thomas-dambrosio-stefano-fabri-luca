package model.roundenvironment.players;

import java.util.Optional;

import model.roundenvironment.coordinate.Coordinate;

public class PlayerImpl implements Player{
	
	public static final int DEFAULT_BARRIERS_NUMBER = 10;
	private String nickname;
	private Coordinate coordinate;
	private int availableBarriers;
	private Integer finishLine;
	
	public PlayerImpl(String nickname, Coordinate coordinate, Optional<Integer> availableBarriers, Integer finishLine) {
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
		return this.coordinate.getX().equals(this.getFinishLine());
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