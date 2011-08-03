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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.google.inject.Inject;

public class MyDAOImpl implements MyDAO {

	@Inject
	EntityManager em;

	@Override
	public void saveEntity(MyEntity e) {
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		em.persist(e);
		tx.commit();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MyEntity> getAllEntities() {
		return em.createQuery("FROM MyEntity").getResultList();
	}
}
