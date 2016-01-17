# Introduction #

WebFeature is a feature that introduces new annotations in order to run webdriver funtionnal tests.


# How to test google homepage #

Let's begin by the test class itself. It's a class that makes use of the FeaturesRunner and that declares to use the WebDriverFeature.

```

@RunWith(FeaturesRunner.class)
@Features(WebDriverFeature.class)
@Browser(type=BrowserFamily.CHROME)
@HomePage(type=GooglePage.class,url="http://www.google.fr")
public class HomePageTest {
	
	@Inject
	GooglePage home;
	
}
```

Two other annotations are present :

  * `@Browser` that tells which browser to use for testing
  * `@Homepage` that tells the type of the homepage (which must extend the `WebPage` class and its url.

Then in your test you'll be able to inject a `GooglePage` object that reflects you web functionnal logic.

Let's see this sample test code :

```

	@Test
	public void iCanSearch() throws Exception {
		GoogleResultPage results = home.searchFor("features-runner");
		assertThat(results.getNumberOfResults() > 0 , is(true));
	}
```

This test makes use of methods of the `GooglePage` and `GoogleResultPage` class that are business class that reflects your web logic. This way you can cleanly separate you test logic and you pages logic.


Here is how those two classes are built :

```

public class GooglePage extends WebPage {
	
	@FindBy(name="q")
	WebElement searchInput;
	
	@FindBy(name="btnK")
	WebElement searchBtn;

	public GoogleResultPage searchFor(String q) {
		searchInput.clear();
		searchInput.sendKeys(q);
		searchBtn.click();
		return getPage(GoogleResultPage.class);
	}
}


public class GoogleResultPage extends WebPage {

	public int getNumberOfResults() {
		List<WebElement> findElements = getDriver().findElements(
				By.xpath("//ol[@id='rso']/li"));
		return findElements.size();
	}

}
```

The `WebPage` object are injected by WebDriver with the elements that are reference with the `@FindBy` keyword.

Those classes make use of the WebDriver API to navigate thru pages. In this example you can see how to return and construct another `WebPage`.