package savings;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundEnvironment;

public interface LoadGame {
	
	/**
	 * 
	 * @return the iterator of the rounds of the game loaded
	 */
	public Iterator<RoundEnvironment> getIterator();
	
	/**
	 * 
	 * @return the model of the game loaded
	 */
	public Model<RoundEnvironment> getModel();
	
	/**
	 * 
	 * @return true if save Exist, false if save doesn't exist
	 */
	public boolean saveExist();
}
