package model;

import java.util.List;

public class StandardPlayerImpl implements StandardPlayer{
	
	public static final int BARRIERS_NUMBER = 10;
	private String nickname;
	private Pair<Integer, Integer> position;
	private int remainingBarriers;
	private int XfinishLine;
	

	@Override
	public String getNickname() {
		return this.nickname;
	}

	@Override
	public int getRemainingBarriers() {
		return this.remainingBarriers;
	}

	@Override
	public Pair<Integer, Integer> getPosition() {
		return this.position;
	}
	
	@Override
	public void placeBarrier() {
		this.remainingBarriers = this.remainingBarriers - 1;
	}
	
	@Override
	public boolean isWinner() {
		return this.position.getX().equals(this.getXFinishLine());
	}
	
	@Override
	public int getXFinishLine() {
		return this.XfinishLine;
	}
	
	@Override
	public void setXFinishLine(Integer x) {
		this.XfinishLine = x;
	}
	
	@Override
	public void setPossibleMoves(List<Pair<Integer, Integer>> positions) {
		// TODO Auto-generated method stub
	}

}
