Feature: Air Quality Test
    
    Scenario: Search and view an address air pollution analysis
        When I access "http://localhost:4200/"
            Then I verify that i'm in the home page
            Then I choose the search type as "Historical"
            And I write down the address "United States"
            And I select the start date as "2020-01-01T00:00"
            And I select the end date as "2020-02-01T00:00"
            When I press the search button
            Then I get redirected to the results page
            And I confirm the header contains "You're viewing results for:"
            And I confirm that the results location is "United States"
            Then I click the button to go back to the home page
            Then I verify that the new page's header is "Search for a region's Air Quality analysis!"
            And I exit the website