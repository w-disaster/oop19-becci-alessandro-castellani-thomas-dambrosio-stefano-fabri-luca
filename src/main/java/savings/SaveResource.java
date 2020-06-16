package savings;

import java.io.Serializable;
import java.util.Iterator;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundEnvironment;

/**
 * I need this class for putting all objects in a single class for saving it in a single file.
 * @author Alessandro Becci
 *
 */
public class SaveResource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2546103703624570995L;
	private final Model<RoundEnvironment> model;
	private final UIController view;
	private final Iterator<RoundEnvironment> iterator;
	
	public SaveResource(final Model<RoundEnvironment> model, final UIController view, final Iterator<RoundEnvironment> iterator) {
		this.model = model;
		this.view = view;
		this.iterator = iterator;
	}

	public Model<RoundEnvironment> getModel(){
		return model;
	}
	
	public UIController getView() {
		return view;
	}

	public Iterator<RoundEnvironment> getRoundIterator(){
		return iterator;
	}
	
}
