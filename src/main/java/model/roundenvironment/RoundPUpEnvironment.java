package model.roundenvironment;
import javafx.util.Pair;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp.Type;

public interface RoundPUpEnvironment extends RoundEnvironment {

	Pair<Type, Coordinate> getPowerUp();
	
}
