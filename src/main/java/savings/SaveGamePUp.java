package savings;



import model.Model;
import model.roundenvironment.RoundPUpEnvironment;

public class SaveGamePUp<X extends RoundPUpEnvironment> extends SaveGameImpl<X> implements SaveGame{
	
	private Model<X> model;
	
	public SaveGamePUp(Model<X> model) {
		super(model);
		this.model = model;
	}

}
