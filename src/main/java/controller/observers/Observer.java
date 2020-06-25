package controller.observers;

/**
 * This class updates the view when the model changes.
 * 
 */
public interface Observer {

    void update(Object object, String player);
}
