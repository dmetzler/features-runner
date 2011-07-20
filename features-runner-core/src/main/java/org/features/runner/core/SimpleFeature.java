/*
* Copyright (c) 2006-2011 Nuxeo SA (http://nuxeo.com/) and others.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* 	  Damien Metzler - outsourced from Nuxeo to features-runner
*     Nuxeo - initial API and implementation
*/
package org.features.runner.core;

import org.junit.runners.model.FrameworkMethod;

import com.google.inject.Binder;

/**
 * These are the states the runner goes through when using runtime feature:
 * <pre>
 * CREATE FRAMEWORK
 * new feature()        --> constructor
 * COLLECT DEFINED DEPLOYMENTS
 * feature.initialize() --> can be used to configure nuxeo home or register JNDI objects
 * START FRAMEWORK
 * feature.start()
 * CREATE INJECTOR => feature.configure() --> can be used to add guice bindings and to dynamically deploy components using the harness
 * feature.beforeRun()
 * feature.beforeMethodRun()  --> test method interceptor
 * feature.afterMethodRun()   --> test method interceptor
 * feature.afterRun() --> cleanup that require framework to be started
 * STOP FRAMEWORK
 * feature.stop()  --> destructor
 * </pre>
 *
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class SimpleFeature implements RunnerFeature {

	public void initialize(FeaturesRunner runner) throws Exception {
	}

	public void configure(FeaturesRunner runner, Binder binder) {
	}

	public void beforeRun(FeaturesRunner runner) throws Exception {
	}

	public void afterRun(FeaturesRunner runner) throws Exception {
	}

	public void start(FeaturesRunner runner) throws Exception {
	}

	public void testCreated(Object test) throws Exception {
	}

	public void stop(FeaturesRunner runner) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void beforeMethodRun(FeaturesRunner runner, FrameworkMethod method,
			Object test) throws Exception {
	}

	public void afterMethodRun(FeaturesRunner runner, FrameworkMethod method,
			Object test) throws Exception {
	}

   

}
