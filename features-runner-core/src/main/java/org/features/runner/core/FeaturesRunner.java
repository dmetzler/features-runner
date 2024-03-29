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
package org.features.runner.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * A Test Case runner that can be extended through features and provide injection
 * though Guice.
 *
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class FeaturesRunner extends BlockJUnit4ClassRunner {

    protected static final AnnotationScanner scanner = new AnnotationScanner();

    /**
     * Guice injector.
     */
    protected Injector injector;

    protected List<RunnerFeature> features;

    public static AnnotationScanner getScanner() {
        return scanner;
    }

    public FeaturesRunner(Class<?> classToRun) throws InitializationError {
        super(classToRun);
        try {
            loadFeatures(getTestClass().getJavaClass());
            initialize();
        } catch (Throwable t) {
            throw new InitializationError(Collections.singletonList(t));
        }
    }

    public Class<?> getTargetTestClass() {
        return super.getTestClass().getJavaClass();
    }

    protected void loadFeature(HashSet<Class<?>> cycles,
            LinkedHashSet<Class<? extends RunnerFeature>> features,
            Class<? extends RunnerFeature> clazz) throws Exception {
    if (features.contains(clazz)) {
            return;
        }
        if (cycles.contains(clazz)) {
            throw new IllegalStateException("Cycle detected in features dependencies of "+clazz);
        }
        cycles.add(clazz);
        scanner.scan(clazz);
        // load required features from annotation
        List<Features> annos = scanner.getAnnotations(clazz, Features.class);
        if (annos != null) {
            for (Features anno : annos) {
                for (Class<? extends RunnerFeature> cl : anno.value()) {
                    if (!features.contains(cl)) {
                        loadFeature(cycles, features, cl);
                    }
                }
            }
        }
        features.add(clazz); // add at the end to ensure requirements are added first
    }

    protected void loadFeatures(Class<?> classToRun) throws Exception {
        scanner.scan(classToRun);
        LinkedHashSet<Class<? extends RunnerFeature>> features = new LinkedHashSet<Class<? extends RunnerFeature>>();
        // load required features from annotation
        List<Features> annos = scanner.getAnnotations(classToRun, Features.class);
        if (annos != null) {
            for (Features anno : annos) {
                for (Class<? extends RunnerFeature> cl : anno.value()) {
                    if (!features.contains(cl)) {
                        loadFeature(new HashSet<Class<?>>(), features, cl);
                    }
                }
            }
        }
        // register collected features
        this.features = new ArrayList<RunnerFeature>();
        for (Class<? extends RunnerFeature> fc : features) {
            RunnerFeature rf = fc.newInstance();
            this.features.add(rf);
        }
    }

    public <T extends RunnerFeature> T getFeature(Class<T> type) {
        for (RunnerFeature rf : features) {
            if (rf.getClass() == type) {
                return type.cast(rf);
            }
        }
        return null;
    }

    public List<RunnerFeature> getFeatures() {
        return features;
    }

    protected void initialize() throws Exception {
        for (RunnerFeature feature : features) {
            feature.initialize(this);
        }
    }

    protected void beforeRun() throws Exception {
        for (RunnerFeature feature : features) {
            feature.beforeRun(this);
        }
    }

    protected void beforeMethodRun(FrameworkMethod method, Object test) throws Exception {
        for (RunnerFeature feature : features) {
            feature.beforeMethodRun(this, method, test);
        }
    }

    protected void afterMethodRun(FrameworkMethod method, Object test) throws Exception {
        for (RunnerFeature feature : features) {
            feature.afterMethodRun(this, method, test);
        }
    }

    protected void afterRun() throws Exception {
        for (RunnerFeature feature : features) {
            feature.afterRun(this);
        }
    }

    protected void testCreated(Object test) throws Exception {
        for (RunnerFeature feature : features) {
            feature.testCreated(test);
        }
    }

    protected void start() throws Exception {
        for (RunnerFeature feature : features) {
            feature.start(this);
        }
    }

    protected void stop() throws Exception {
        for (RunnerFeature feature : features) {
            feature.stop(this);
        }
    }

    protected void configureBindings(Binder binder) {
        binder.bind(FeaturesRunner.class).toInstance(this);
        for (RunnerFeature feature : features) {
            feature.configure(this, binder);
        }
    }

    /**
     * Gets the Guice injector.
     */
    public Injector getInjector() {
        return injector;
    }

    public void resetInjector() {
        injector = createInjector();
    }

    protected Injector createInjector() {
        Module module = new Module() {

			public void configure(Binder binder) {
				configureBindings(binder);
				
			}
            
        };
        // build injector
        return Guice.createInjector(module);
    }

    @Override
    public void run(final RunNotifier notifier) {
        try {
            try {
                start();
                // create injector
                resetInjector();
                try {
                    beforeRun();
                    super.run(notifier); // launch tests
                } finally {
                    afterRun();
                }
            } finally {
                stop();
            }
        } catch (Throwable e) {
            notifier.fireTestFailure(new Failure(getDescription(), e));
        }
    }

    @Override
    public Object createTest() {
        // Return a Guice injected test class
        Object test = injector.getInstance(getTestClass().getJavaClass());
        // let features adapt the test object if needed
        try {
            testCreated(test);
        } catch (Exception e) {
            throw new Error("Failed to prepare test instance: "+test, e);
        }
        return test;
    }

    @Override
    protected void validateZeroArgConstructor(List<Throwable> errors) {
        // Guice can inject constructors with parameters so we don't want this
        // method to trigger an error
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeMethod(method, test);
    }

    protected class InvokeMethod extends Statement {
        protected final FrameworkMethod testMethod;
        protected final Object target;

        protected InvokeMethod(FrameworkMethod testMethod, Object target) {
            this.testMethod = testMethod;
            this.target= target;
        }

        @Override
        public void evaluate() throws Throwable {
            beforeMethodRun(testMethod, target);
            try {
                testMethod.invokeExplosively(target);
            } finally {
                afterMethodRun(testMethod, target);
            }
        }
    }

}
