package org.features.runner.core;

import org.junit.runners.model.FrameworkMethod;

import com.google.inject.Binder;
import com.google.inject.Scopes;

public class CountFeature implements RunnerFeature {

	boolean initialized = false;
	boolean beforeRunCalled = false;
	boolean afterRunCalled = false;
	boolean started = false;
	boolean stopped = false;
	boolean testCreated = false;

	public void initialize(FeaturesRunner runner) throws Exception {
		initialized = true;
	}

	public void configure(FeaturesRunner runner, Binder binder) {
		binder.bind(DummyInterface.class).to(DummyImpl.class).in(Scopes.SINGLETON);
	}

	public void beforeRun(FeaturesRunner runner) throws Exception {
		beforeRunCalled = true;
		
	}

	public void afterRun(FeaturesRunner runner) throws Exception {
		afterRunCalled = true;
		
	}

	public void start(FeaturesRunner runner) throws Exception {
		started = true;
		
	}

	public void testCreated(Object test) throws Exception {
		testCreated  = true;
		
	}

	public void stop(FeaturesRunner runner) throws Exception {
		stopped=true;
		
	}

	public void beforeMethodRun(FeaturesRunner runner, FrameworkMethod method,
			Object test) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterMethodRun(FeaturesRunner runner, FrameworkMethod method,
			Object test) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
