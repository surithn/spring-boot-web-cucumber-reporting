package hello;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * @author Admin
 *
 */
public class SearchQueryPage {

    private final WebDriver driver;
    static String searchUrl= "http://localhost:9000/";
    
    @FindBy(css = "input[name=q]")
    WebElement query;

    @FindBy(css = "input[value=\"Google Search\"]")
    WebElement searchButton;

    @FindBy(css = "input[value=\"I'm Feeling Lucky\"]")
    WebElement luckyButton;

    public static SearchQueryPage loadUsing( WebDriver driver) {
        driver.get(searchUrl);
        return new SearchQueryPage(driver);
    }
    
    public SearchQueryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public SearchQueryPage setQuery(String term) {
        query.clear();
        query.sendKeys(term);
        return this;
    }

    public SearchResultPage pressEnterInQuery() {
        query.sendKeys("\n");
        return new SearchResultPage(driver);
    }

    public SearchResultPage clickSearchButton() {
        searchButton.click();
        return new SearchResultPage(driver);
    }

    public SearchResultPage clickLuckyButton() {
        luckyButton.click();
        return new SearchResultPage(driver);
    }


}
