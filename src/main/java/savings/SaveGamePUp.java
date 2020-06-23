package savings;

import java.util.Iterator;

import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;

public class SaveGamePUp<X extends RoundPUpEnvironment> extends SaveGameImpl implements SaveGame{
	
	private Model<RoundPUpEnvironment> model;
	private Iterator<RoundPUpEnvironment> iterator;
	
	public SaveGamePUp(Model<RoundPUpEnvironment> model, Iterator<RoundPUpEnvironment> iterator) {
		super(model, iterator);
	}

}
