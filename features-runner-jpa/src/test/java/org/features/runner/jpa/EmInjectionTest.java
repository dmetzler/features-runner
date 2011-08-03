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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.features.runner.core.Features;
import org.features.runner.core.FeaturesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features(MyDaoFeature.class)
@PersistenceUnitConfig(name="db-manager")
public class EmInjectionTest {

	@Inject
	MyDAO dao;

	@Test
	public void DaoIsBinded() throws Exception {
		assertThat(dao, is(notNullValue()));
	}

	@Test
	public void iCanPersistEntities() throws Exception {

		assertThat(dao.getAllEntities().size(), is(0));
		MyEntity e1 = new MyEntity();
		e1.setName("e1");
		dao.saveEntity(e1);

		assertThat(dao.getAllEntities().size(), is(1));

	}
}
