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
*/
package org.features.runner.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.features.runner.core.Features;
import org.features.runner.core.FeaturesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;


@RunWith(FeaturesRunner.class)
@Features(WebDriverFeature.class)
@Browser(type=BrowserFamily.HTML_UNIT)
@HomePage(type=GooglePage.class,url="http://www.google.fr")
public class HomePageTest {
	
	@Inject
	GooglePage home;
	
	@Test
	public void iCanReachGoogle() throws Exception {
		assertThat(home,is(notNullValue()));
	}
	
	@Test
	public void iCanSearch() throws Exception {
		GoogleResultPage results = home.searchFor("features-runner");
		assertThat(results.getNumberOfResults() > 0 , is(true));
	}
}
