@Module_Chart
Feature: ChartOperations

  @UI @Mobile @Automated @Regression @Priority_High @Login_Error
  Scenario: TC01 all items and checkout
  #Check if the user can perform a login operation with these usernames
    Given I start a chrome session and the url is "https://qa-challenge.codesubmit.io/"
    When I login as username: "standard_user" and password: "secret_sauce"
    And I add one of every single item in the main page
    Then All added items should be displayed
    And I do a checkout of the selected items
    And The total price plus shipping should be correct and the purchase is completed
