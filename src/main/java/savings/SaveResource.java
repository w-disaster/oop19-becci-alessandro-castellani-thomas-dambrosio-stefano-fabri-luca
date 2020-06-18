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
public class SaveResource {
	
	private final Model<RoundEnvironment> model;
	private final Iterator<RoundEnvironment> iterator;
	
	public SaveResource(final Model<RoundEnvironment> model, final Iterator<RoundEnvironment> iterator) {
		this.model = model;
		this.iterator = iterator;
	}

	public Model<RoundEnvironment> getModel(){
		return model;
	}

	public Iterator<RoundEnvironment> getRoundIterator(){
		return iterator;
	}
	
}
