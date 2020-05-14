package model;

import java.util.List;
import java.util.Optional;

import model.roundenvironment.BarrierEnvironment;

public class ModelFactoryImpl<X extends BarrierEnvironment> implements ModelFactory<X>{

	@Override
	public ModelImpl<X> build(List<X> roundEnvironments) {
		return new ModelImpl<>(roundEnvironments, Optional.empty());
	}
	
}
