package model;

import java.util.List;

import model.roundenvironment.BarrierEnvironment;
import model.roundenvironment.RoundEnvironment;

public interface ModelFactory {
	
	/**
	 * simple method to build the model given the round environments
	 * @param <X>
	 * @param roundEnvironments
	 * @return
	 */
	<X extends RoundEnvironment> ModelImpl<X> build(List<X> roundEnvironments);
		
}
