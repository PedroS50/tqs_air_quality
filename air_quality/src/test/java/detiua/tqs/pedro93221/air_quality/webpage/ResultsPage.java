package detiua.tqs.pedro93221.air_quality.webpage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return locationHeader.getText();
    }

    public void clickBackHome() {
        this.backBtn.click();
    }

}
