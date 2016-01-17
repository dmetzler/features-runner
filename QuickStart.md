# Introduction #

Features-runner is a library that gives you a jUnit runner that can run tests. With the help of a few annotations you can declare _features_.

A feature is a set of configure/bind/runBefore methods that you can wrap around a test class in order to configure your test environment.


# Architecture #

The runner embeds the [Guice](http://code.google.com/p/google-guice/) library. The test class itself is instanciated via Guice. It gives you the ability to bind some classes in a feature for instance.

# Simple Useless sample #

Imagine that you want to have acces to a dummy interface in your test code. Let's say the dummy interface :

```
public interface DummyInterface {
	
}
```


You can write a simple feature that will bind an implementation of this interface :

```
public class MyFeature implements RunnerFeature {

	public void configure(FeaturesRunner runner, Binder binder) {
		binder.bind(DummyInterface.class).to(DummyImpl.class).in(Scopes.SINGLETON);
	}
```


Now to run a test class, here is how proceed :

```

@RunWith(FeaturesRunner.class)
@Features(MyFeature.class)
public class InjectionTest {

	@Inject
	DummyInterface di;
	
	@Test
	public void canBindSomeClasses() throws Exception {
		assertThat(di,is(notNullValue()));
	}
}

```

You can use this to inject some datasources, some services or other thing that are recurrent in a test environment.


# Features composition #
Features can be composed each other in a test. For instance you can write things like :

```

@RunWith(FeaturesRunner.class)
@Features({MyFeature.class, AnotherFeature.class})
public class InjectionTest {

....

```

The feature annotation can also be used on Features classes :


```
@Features({MyFeature.class, AnotherFeature.class})
public class ComposedFeature extends SimpleFeature {
}
```


# How to use it #
Features should be wrapped around functionnal blocks in you application. There should be a feature to setup the DB, a feature to setup the runtime environment....

A feature has access to all the test runtime environment :
  * the test class
  * the FeaturesRunner class
  * all the features

You can ass your own annotations to the test classes in order to setup the environment the way you want. For instance the features-runner-web that is included in this distribution introduces new annotations to build a webdriver test environment.