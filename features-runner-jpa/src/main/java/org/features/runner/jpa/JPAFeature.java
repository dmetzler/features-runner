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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.features.runner.core.FeaturesRunner;
import org.features.runner.core.SimpleFeature;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class JPAFeature extends SimpleFeature {
	private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<EntityManager>();

	@Override
	public void configure(FeaturesRunner runner, Binder binder) {

		super.configure(runner, binder);

		Class<?> classToTest = runner.getTargetTestClass();
		final PersistenceUnitConfig persistenceUnitConfig = FeaturesRunner.getScanner()
				.getFirstAnnotation(classToTest, PersistenceUnitConfig.class);
		
		
		
		binder.install(new Module() {

			@Override
			public void configure(Binder binder) {

			}

			@Provides
			@Singleton
			private EntityManagerFactory providesEntityManagerFactory() {
				if(persistenceUnitConfig == null) {
					return null;
				}

				Map<String, String> properties = new HashMap<String, String>();
				properties.put("hibernate.connection.driver_class",
						"org.h2.Driver");
				properties.put("hibernate.connection.url",
						"jdbc:h2:target/test");
				properties.put("hibernate.connection.username", "sa");
				properties.put("hibernate.connection.password", "");
				properties.put("hibernate.dialect",
						"org.hibernate.dialect.H2Dialect");
				properties.put("hibernate.hbm2ddl.auto", "create");
				properties.put("hibernate.cache.provider_class",
						"org.hibernate.cache.NoCacheProvider");
				properties.put("hibernate.id.new_generator_mappings", "true");
				return Persistence.createEntityManagerFactory(persistenceUnitConfig.name(),
						properties);
			}

			@Provides
			private EntityManager provideEntityManager(
					EntityManagerFactory entityManagerFactory) {
				if(entityManagerFactory == null) {
					return null;
				}
				
				EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
				if (entityManager == null) {
					ENTITY_MANAGER_CACHE
							.set(entityManager = entityManagerFactory
									.createEntityManager());
				}
				return entityManager;
			}

		});
	}

}
