package detiua.tqs.pedro93221.air_quality.webpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {

    private WebDriver driver;
    
    @FindBy(tagName = "h3")
    private WebElement mainHeader;

    @FindBy(id = "locationHeader")
    private WebElement locationHeader;

    @FindBy(id = "backBtn")
    private WebElement backBtn;

    //Constructor
	public ResultsPage(WebDriver driver){
		this.driver = driver;
		//Initialise Elements
		PageFactory.initElements(driver, this);
		this.driver.manage().window().maximize();
	}

    public String getHeader() {
        return mainHeader.getText();
    }

    public String getLocation() {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("locationHeader")));
        return locationHeader.getText();
    }

    public void clickBackHome() {
        this.backBtn.click();
    }

}
