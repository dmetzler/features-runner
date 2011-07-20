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
@Browser(type=BrowserFamily.CHROME)
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
