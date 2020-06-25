package savings.load;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundEnvironment;

/**
 * The Interface LoadGame.
 *
 * @param <X> the generic type
 */
public interface LoadGame<X extends RoundEnvironment> {
	
	/**
	 * Gets the data from file and reconstructs model.
	 *
	 */
	void getData();
	
	/**
	 * Gets the iterator.
	 *
	 * @return the iterator of the rounds of the game loaded
	 */
	Iterator<X> getIterator();
	
	/**
	 * Gets the model.
	 *
	 * @return the model of the game loaded
	 */
	Model<X> getModel();
	
	/**
	 * Save exist.
	 *
	 * @return true if save Exist, false if save doesn't exist
	 */
	boolean saveExist();
}
