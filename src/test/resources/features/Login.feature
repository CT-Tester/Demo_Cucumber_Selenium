@Module_Login
Feature: BasicLogin

  @UI @Mobile @Automated @Regression @Priority_High @Login_Error
  Scenario Outline: TC01_Online_Shop_Basic_Login
  #Check if the user can perform a login operation with these usernames
    Given I start a chrome session and the url is "https://qa-challenge.codesubmit.io/"
    When I try to login with these values: <userName> and <password>
    Then Main page should appear

    Examples:
      |userName|password|
      |"standard_user"|"secret_sauce"|
      |"locked_out_user"|"secret_sauce"|
      |"problem_user"|"secret_sauce"|
      |"performance_glitch_user"|"secret_sauce"|
      |"error_user"|"secret_sauce"|
      |"visual_user"|"secret_sauce"|

  Scenario Outline: TC02_Login_then_logout
  #Check if the user can perform a login operation with these usernames
    Given I start a chrome session and the url is "https://qa-challenge.codesubmit.io/"
    When I try to login with these values: <userName> and <password>
    Then I do a logout

    Examples:
      |userName|password|
      |"standard_user"|"secret_sauce"|
      |"problem_user"|"secret_sauce"|
      |"performance_glitch_user"|"secret_sauce"|
      |"error_user"|"secret_sauce"|
      |"visual_user"|"secret_sauce"|

  Scenario Outline: TC03_Login_check_main_page_elements_then_logout
  #Check if the user can perform a login operation with these usernames
    Given I start a chrome session and the url is "https://qa-challenge.codesubmit.io/"
    When I try to login with these values: <userName> and <password>
    And I check item images
    And I check item text
    Then I do a logout

    Examples:
      |userName|password|
      |"standard_user"|"secret_sauce"|
      |"problem_user"|"secret_sauce"|
      |"performance_glitch_user"|"secret_sauce"|
      |"error_user"|"secret_sauce"|
      |"visual_user"|"secret_sauce"|

  Scenario Outline: TC04_Check_login_response_time
  #Check if the user can perform a login operation with these usernames
    Given I start a chrome session and the url is "https://qa-challenge.codesubmit.io/"
    When I try to login with these values: <userName> and <password> and check response time
    Then Response time should not be longer than 100 miliseconds
    Examples:
      |userName|password|
      |"standard_user"|"secret_sauce"|
      |"problem_user"|"secret_sauce"|
      |"performance_glitch_user"|"secret_sauce"|
      |"error_user"|"secret_sauce"|
      |"visual_user"|"secret_sauce"|