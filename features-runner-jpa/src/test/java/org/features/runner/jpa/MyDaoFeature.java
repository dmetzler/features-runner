/*
 * Copyright (c) 2011 Damien METZLER
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	  Damien Metzler 
 */
package org.features.runner.jpa;

import org.features.runner.core.Features;
import org.features.runner.core.FeaturesRunner;
import org.features.runner.core.SimpleFeature;

import com.google.inject.Binder;
import com.google.inject.Scopes;

@Features(JPAFeature.class)
public class MyDaoFeature extends SimpleFeature{

	@Override
	public void configure(FeaturesRunner runner, Binder binder) {
		binder.bind(MyDAO.class).to(MyDAOImpl.class).in(Scopes.SINGLETON);
	}
	
}
