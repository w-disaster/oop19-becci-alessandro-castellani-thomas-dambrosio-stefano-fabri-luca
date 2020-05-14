package model;

import java.util.List;

import model.roundenvironment.BarrierEnvironment;

public interface ModelFactory<X extends BarrierEnvironment> {
	
	ModelImpl<X> build(List<X> roundEnvironments);
		
}
