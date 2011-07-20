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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features(CountFeature.class)
public class InjectionTest {

	@Inject 
	FeaturesRunner fr;
	
	@Inject
	DummyInterface di;
	
	@Test
	public void featureRunnerShouldBeInjectable() throws Exception {
		assertThat(fr,is(notNullValue()));
		
		CountFeature cf = fr.getFeature(CountFeature.class);
		assertThat(cf.afterRunCalled,is(false));
		assertThat(cf.beforeRunCalled,is(true));
		assertThat(cf.initialized,is(true));
		assertThat(cf.started, is(true));
		assertThat(cf.stopped,is(false));
		assertThat(cf.testCreated,is(true));
	}
	
	
	@Test
	public void canBindSomeClasses() throws Exception {
		assertThat(di,is(notNullValue()));
	}
}
