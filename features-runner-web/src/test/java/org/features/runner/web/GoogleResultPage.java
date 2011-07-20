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
