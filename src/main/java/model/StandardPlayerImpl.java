package model;

import java.util.Optional;

public class StandardPlayerImpl implements StandardPlayer{
	
	public static final int DEFAULT_BARRIERS_NUMBER = 10;
	private String nickname;
	private Coordinate coordinate;
	private int availableBarriers;
	private Integer finishLine;
	
	public StandardPlayerImpl(String nickname, Coordinate coordinate, Optional<Integer> availableBarriers, Integer finishLine) {
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
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	@Override
	public void placeBarrier() {
		this.availableBarriers = this.availableBarriers - 1;
	}
	
	@Override
	public boolean isWinner() {
		return this.coordinate.getX().equals(this.getFinishLine());
	}
	
	private Integer getFinishLine() {
		return this.finishLine;
	}
	
	private void setFinishLine(Integer finishLine) {
		this.finishLine = finishLine;
	}
}
