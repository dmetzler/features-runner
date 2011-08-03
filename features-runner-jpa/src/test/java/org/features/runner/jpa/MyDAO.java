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


public interface MyDAO {

	void saveEntity(MyEntity e1);

	List<MyEntity> getAllEntities();
	
	
}
