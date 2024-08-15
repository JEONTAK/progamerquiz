Feature: Quiz Click
  Scenario: User clicks on a quiz
    Given the user is on the Main Page
    When the user clicks on a quiz
    Then the quiz should open

Feature: Ad Click
  Scenario: User clicks on an advertisement
    Given the user is on the Main Page
    When the user clicks on an advertisement
    Then a new window should open with the advertisement

Feature: Select Quiz from Menu
  Scenario: User selects a quiz from the left menu
    Given the user is on the Main Page
    When the user selects a quiz from the left menu
    Then the page should display the selected quiz

Feature: Input Pro Gamer
  Scenario: User inputs a pro gamer as the answer
    Given the user is on Quiz 1, 3, 4, or 6
    When the user inputs a pro gamer they think is correct
    Then the input should be accepted

Feature: Suggest Pro Gamer Names
  Scenario: User inputs a pro gamer name and suggestions are shown
    Given the user is on Quiz 1, 3, 4, or 6
    When the user starts typing a pro gamer name
    Then a list of pro gamer IDs or names matching the input should be displayed

Feature: Input Team
  Scenario: User inputs a team as the answer
    Given the user is on Quiz 5
    When the user inputs a team they think is correct
    Then the input should be accepted

Feature: Suggest Team Names
  Scenario: User inputs a team name and suggestions are shown
    Given the user is on Quiz 5
    When the user starts typing a team name
    Then a list of team names matching the input should be displayed

Feature: Click Board
  Scenario: User clicks on a board
    Given the user is on Quiz 2
    When the user clicks on the board they think is correct
    Then the board click should be registered

Feature: Set Board Size
  Scenario: User sets the board size
    Given the user is on Quiz 2
    When the user selects the desired board size
    Then the board should be displayed in the selected size

Feature: User Correct Answer
  Scenario: User answers correctly
    Given the user is on Quiz 1, 4, 5, or 6
    When the user inputs a correct answer
    Then the blurred image should be revealed
    And the background should change to green

Feature: User Correct Answer on Bingo Board
  Scenario: User answers correctly on a bingo board
    Given the user is on Quiz 2
    When the user inputs a correct answer
    Then the background of the bingo board should change to green

Feature: User Correct Answer with Pro Gamer Image
  Scenario: User answers correctly on bingo board and sees pro gamer image
    Given the user is on Quiz 3
    When the user inputs a correct answer
    Then the background of the bingo board should change to green
    And the image of the pro gamer should be displayed

Feature: User Incorrect Answer with Hint
  Scenario: User inputs an incorrect answer and receives a hint
    Given the user is on Quiz 1
    When the user inputs an incorrect answer
    Then a hint should be provided

Feature: User Incorrect Answer with Red Background
  Scenario: User inputs an incorrect answer and sees a red background
    Given the user is on Quiz 2, 3, 4, 5, or 6
    When the user inputs an incorrect answer
    Then the background should temporarily change to red

Feature: User Fails Quiz with Image and Red Background
  Scenario: User fails the quiz
    Given the user is on Quiz 1, 4, 5, or 6
    When the user fails the quiz
    Then the blurred image should be revealed or displayed
    And the background should change to red

Feature: User Fails Quiz with Failure Screen
  Scenario: User fails the quiz and sees a failure screen
    Given the user is on Quiz 2 or 3
    When the user fails the quiz
    Then a failure screen should be displayed

Feature: User Completes Quiz
  Scenario: User completes the quiz
    Given the user is on Quiz 4, 5, or 6
    When the user completes the quiz
    Then the number of correct answers and total questions should be displayed
    And the number of attempts used and total attempts should be displayed
