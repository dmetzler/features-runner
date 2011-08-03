package org.features.runner.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;

import org.features.runner.core.Features;
import org.features.runner.core.FeaturesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.internal.Nullable;

@RunWith(FeaturesRunner.class)
@Features(MyDaoFeature.class)
public class NullEmInjectionTest {
	
	@Inject
	@Nullable
	EntityManager em;

	@Test
	public void emIsNull() throws Exception {
		assertThat(em, is(nullValue()));
	}
}
