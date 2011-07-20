package org.features.runner.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GoogleResultPage extends WebPage {

	public int getNumberOfResults() {
		List<WebElement> findElements = getDriver().findElements(
				By.xpath("//ol[@id='rso']/li"));
		return findElements.size();
	}

}
