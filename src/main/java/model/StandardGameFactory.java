package model;

public interface StandardGameFactory {

	StandardGame getSGbyName(String nickname1, String nickame2);
	
	StandardGame getSGbyPlayers(Pair<StandardPlayer, StandardPlayer> players);
	
	StandardGame getSGbyDimension(final int dimension, String nickname1, String nickname2);
}
