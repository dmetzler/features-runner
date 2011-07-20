package org.features.runner.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePage extends WebPage {
	
	@FindBy(name="q")
	WebElement searchInput;
	
	@FindBy(name="btnG")
	WebElement searchBtn;

	public GoogleResultPage searchFor(String q) {
		searchInput.clear();
		searchInput.sendKeys(q);
		searchBtn.click();
		return getPage(GoogleResultPage.class);
	}
}
