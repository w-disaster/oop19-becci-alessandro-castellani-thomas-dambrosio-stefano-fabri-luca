package controller.observers;

/**
 * this class updates the view when the model changes
 * 
 * @author Thomas
 */
public interface Observer {

	void update(Object object, String player);
}
