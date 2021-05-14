package detiua.tqs.pedro93221.air_quality.webpage;

import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    
    private WebDriver driver;

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(id = "addressInput")
    private WebElement searchBar;

    @FindBy(id = "typeSelect")
    private WebElement type;

    @FindBy(id = "startDay")
    private WebElement startDay;

    @FindBy(id = "startMonth")
    private WebElement startMonth;

    @FindBy(id = "startYear")
    private WebElement startYear;

    @FindBy(id = "startHour")
    private WebElement startHour;

    @FindBy(id = "startMinutes")
    private WebElement startMinutes;

    @FindBy(id = "endDay")
    private WebElement endDay;

    @FindBy(id = "endMonth")
    private WebElement endMonth;

    @FindBy(id = "endYear")
    private WebElement endYear;

    @FindBy(id = "endHour")
    private WebElement endHour;

    @FindBy(id = "endMinutes")
    private WebElement endMinutes;

    @FindBy(id = "searchBtn")
    private WebElement searchBtn;


    //Constructor
	public HomePage(WebDriver driver){
		this.driver = driver;
		//Initialise Elements
		PageFactory.initElements(driver, this);
		driver.manage().window().maximize();
	}

    //Constructor
	public HomePage(WebDriver driver, String page_url){
		this.driver = driver;
		driver.get(page_url);
		//Initialise Elements
		PageFactory.initElements(driver, this);
		driver.manage().window().maximize();
	}

    public boolean isPageOpened() {
		return driver.getTitle().equals("APHome");
	}

    public String getHeader() {
        return this.header.getText();
    }

    public void setType(String typeStr) {
        Select typeSelect = new Select(type);
        typeSelect.selectByValue(typeStr);
    }

    public void setSearch(String address) {
        searchBar.sendKeys(address);
    }

    public void setStartDate(LocalDateTime start) {
        startDay.clear();
        startDay.sendKeys( String.valueOf(start.getDayOfMonth()) );
        startMonth.clear();
        startMonth.sendKeys( String.valueOf(start.getMonthValue()) );
        startYear.clear();
        startYear.sendKeys( String.valueOf(start.getYear()) );
        startHour.clear();
        startHour.sendKeys( String.valueOf(start.getHour()) );
        startMinutes.clear();
        startMinutes.sendKeys( String.valueOf(start.getMinute()) );
    }

    public void setEndDate(LocalDateTime end) {
        endDay.clear();
        endDay.sendKeys( String.valueOf(end.getDayOfMonth()) );
        endMonth.clear();
        endMonth.sendKeys( String.valueOf(end.getMonthValue()) );
        endYear.clear();
        endYear.sendKeys( String.valueOf(end.getYear()) );
        endHour.clear();
        endHour.sendKeys( String.valueOf(end.getHour()) );
        endMinutes.clear();
        endMinutes.sendKeys( String.valueOf(end.getMinute()) );
    }

    public void clickSearch() {
        searchBtn.click();
    }
}