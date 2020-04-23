package model;

import java.util.List;

public class StandardPlayerImpl implements StandardPlayer{
	
	public static final int BARRIERS_NUMBER = 10;
	private String nickname;
	private Pair<Integer, Integer> position;
	private Integer remainingBarriers;
	private Integer XfinishLine;
	
	public StandardPlayerImpl(String nickname, Pair<Integer, Integer> position) {
		super();
		this.nickname = nickname;
		this.position = position;
		this.remainingBarriers = BARRIERS_NUMBER;
		this.setXFinishLine(StandardGameImpl.POSITIONBOUNDARY - this.position.getX());
	}

	@Override
	public void move(Direction direction, Type type) {
		final int offset = type.equals(Type.NORMAL) ? 1 : 2;
		switch(direction) {
		case LEFT:
			this.position = new Pair<>(this.getPosition().getX() - offset, this.getPosition().getY());
			break;
		case RIGHT:
			this.position = new Pair<>(this.getPosition().getX() + offset, this.getPosition().getY());
			break;
		case TOP:
			this.position = new Pair<>(this.getPosition().getX(), this.getPosition().getY() - offset);
			break;
		case DOWN:
			this.position = new Pair<>(this.getPosition().getX(), this.getPosition().getY() + offset);
			break;
		}
	}

	@Override
	public String getNickname() {
		return this.nickname;
	}

	@Override
	public Integer getRemainingBarriers() {
		return this.remainingBarriers;
	}

	@Override
	public Pair<Integer, Integer> getPosition() {
		return this.position;
	}
	
	private Integer getXFinishLine() {
		return this.XfinishLine;
	}
	
	@Override
	public void placeBarrier() {
		this.remainingBarriers = this.remainingBarriers - 1;
	}
	
	@Override
	public boolean isWinner() {
		return this.position.getX().equals(this.getXFinishLine());
	}

	private void setXFinishLine(Integer x) {
		this.XfinishLine = x;
	}
	
	@Override
	public void setPossibleMoves(List<Pair<Integer, Integer>> positions) {
		// TODO Auto-generated method stub
	}

}
