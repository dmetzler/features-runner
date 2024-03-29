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
package org.features.runner.web;

import org.openqa.selenium.WebDriver;

/**
 * WebDriver test configuration that can be configured either from
 * system properties or for annotations.
 *
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class Configuration {

    /**
     * The current driver
     */
    protected WebDriver driver;

    /**
     * Custom factory to create the driver
     */
    protected DriverFactory factory;

    /**
     * Initial URL (the one to be used by the home page)
     */
    protected String home;

    /**
     * The home page class
     */
    protected Class<?> homePageClass;


    public Configuration(DriverFactory factory) {
        this.factory = factory;
    }

    protected WebDriver createDriver() {
        return factory.createDriver();
    }

    protected void disposeDriver(WebDriver driver) {
        factory.disposeDriver(driver);
    }

    public BrowserFamily getBrowserFamily() {
        return factory.getBrowserFamily();
    }

    public void setFactory(DriverFactory factory) {
        resetDriver();
        this.factory = factory;
    }

    public DriverFactory getFactory() {
        return factory;
    }

    public void setHome(String url) {
        this.home = url;
    }

    public String getHome() {
        return home;
    }

    public void setHomePageClass(Class<?> homePageClass) {
        this.homePageClass = homePageClass;
    }

    public Class<?> getHomePageClass() {
        return homePageClass;
    }

    public void home() {
        if (home != null && driver != null) {
            driver.get(home);
        }
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
            home();
        }
        return driver;
    }

    public void resetDriver() {
        if (driver != null) {
            driver.quit();
            disposeDriver(driver);
            driver = null;
        }
    }

}
