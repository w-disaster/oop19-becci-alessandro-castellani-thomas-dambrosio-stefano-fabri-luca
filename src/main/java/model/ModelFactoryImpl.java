package model;

import java.util.List;
import java.util.Optional;

import model.roundenvironment.BarrierEnvironment;

public class ModelFactoryImpl implements ModelFactory {

	@Override
	public <X> ModelImpl<X> build(List<X> roundEnvironments) {
		return new ModelImpl<>(roundEnvironments, Optional.empty());
	}
	
}
