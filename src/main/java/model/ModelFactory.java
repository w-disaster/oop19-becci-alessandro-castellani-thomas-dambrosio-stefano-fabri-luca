package model;


import model.roundenvironment.RoundEnvironment;

public interface ModelFactory {
	
	/**
	 * 
	 * @param nickname1
	 * @param nickname2
	 * @return model of a standard game
	 */
	Model<RoundEnvironment> standardModel(String nickname1, String nickname2);
		
}
