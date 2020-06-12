package model;

import java.util.List;
import java.util.Optional;

import model.roundenvironment.BarrierEnvironment;
import model.roundenvironment.RoundEnvironment;

public class ModelFactoryImpl implements ModelFactory {

	@Override
	public <X extends RoundEnvironment> ModelImpl<X> build(List<X> roundEnvironments) {
		return new ModelImpl<>(roundEnvironments, Optional.empty());
	}
	
}
