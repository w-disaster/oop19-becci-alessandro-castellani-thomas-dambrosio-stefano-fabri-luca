package controller;

/**
 * this class updates the view when the model changes
 * 
 * @author Thomas
 */
public interface Observer {

	void update(Object action, String player);
}
