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
