package model.junk;

public class BoardDimensionImpl implements BoardDimension{

	public static final int DEFAULT_DIMENSION = 9;
	private int dimension;
	
	private static class LazyBoardDimension {
		private static final BoardDimensionImpl LAZY_DIMENSION = new BoardDimensionImpl();
	}
	
	private BoardDimensionImpl() {
		this.dimension = DEFAULT_DIMENSION;
	}
	
	public static BoardDimension getBoardDimension() {
		return LazyBoardDimension.LAZY_DIMENSION;
	}

	@Override
	public void set(int dimension) {
		this.dimension = dimension;
	}

	@Override
	public int get() {
		return this.dimension;
	}

}
