package detiua.tqs.pedro93221.air_quality.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.time.LocalDateTime;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AirQualitySteps {
    private WebDriver driver = new FirefoxDriver();

    HomePage home;
    ResultsPage results;

    @When("I access {string}")
    public void i_access(String page_url) {
        home = new HomePage(driver, page_url);
	    assertThat( home.isPageOpened(), is(true) );
    }
    
    @Then("I verify that i'm in the home page")
    public void i_verify_that_i_m_in_the_home_page() {
        assertThat( home.isPageOpened(), is(true) );
    }

    @Then("I choose the search type as {string}")
    public void i_choose_the_search_type_as(String type) {
        home.setType(type);
    }

    @Then("I write down the address {string}")
    public void i_write_down_the_address(String address) {
        home.setSearch(address);
    }

    @Then("I select the start date as {string}")
    public void i_select_the_start_date_as(String start) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        home.setStartDate(startDate);
    }

    @Then("I select the end date as {string}")
    public void i_select_the_end_date_as(String end) {
        LocalDateTime endDate = LocalDateTime.parse(end);
        home.setEndDate(endDate);
    }

    @When("I press the search button")
    public void i_press_the_search_button() {
        home.clickSearch();
    }

    @Then("I get redirected to the results page")
    public void i_get_redirected_to_the_results_page() {
        results = new ResultsPage(driver);
    }
    @Then("I confirm the header contains {string}")
    public void i_confirm_the_header_contains(String header) {
        assertThat( results.getHeader(), is(header) );
    }

    @Then("I confirm that the results location is {string}")
    public void i_confirm_that_the_results_location_is(String location) {
        assertThat( results.getLocation(), is(location) );
    }

    @Then("I click the button to go back to the home page")
    public void i_click_the_button_to_go_back_to_the_home_page() {
        results.clickBackHome();
        home = new HomePage(driver);
    }

    @Then("I verify that the new page's header is {string}")
    public void i_verify_that_the_new_page_s_header_is(String header) {
        assertThat( home.getHeader(), is(header) );
    }

    @Then("I exit the website")
    public void i_exit_the_website() {
        driver.close();
    }
}
