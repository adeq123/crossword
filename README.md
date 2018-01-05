# Crossword

Download this project from: https://github.com/adeq123/crossword

## Motivation
This respository was created as a main project from AGH object oriented programming course.

## General description
It is a desktop aplication which allows us to generate the crossword based on loaded database, fill it and check the solution. 
Each Crossword is written to TXT file and and you can browser through all of them. At the end you are able to generate the PDF file
To run it copy the resository to your ide and run main method included in the Main class.
## Detailed description & Features
  ### Required input
  The application takes as an input folling data:
  - desired crossword size measured in fields. Use New crossword panel (default 10x10)
  - strategy, choose Easy or Hard located in Strategy panel. (default Hard)
    - Easy strategy generates plain crossword where the first column is the solution of the crossword and all of the crosswords phrases 
      are horizontal 
    - Hard strategy generates complex crossword where all of the phrases can cross and be either horizontal or vertical. 
  - database path, it is a txt file where the the first row is a solution to the clue, and the next one is the clue it self. 
    Then it continues in the same manner with the rest of data (row 1: answer 1, row 2 : clue 1, row 3: answer 2, row 4: clue 2 ...etc)
    As a default is uses the database attached to the project (in Polish)
    
   ### Optional input
   You can use following input (not required): 
   - txt file path, the crossword txt file to be loaded if you would like to work with the crossword that you saved
 ## Interaction
  After setting up all of the necesary input press Generate button in New crossword panel. The new crossword will show up on the screen.
  Below you see clues fill in the corssword by pressing each field and typing in the letter. At the end of the day go to Control Panel
  and hit Solve. All green fileds were correct all red ones were incorrect. You can use Print button to create pdf file. Use Save button
  to save the crossword in TXT file format. The browser panel allows you to navigate among the crosswords generated. Press Previous to 
  see the previously generated crossword and press Next to see the next crossword.
    

## Concepts used:
Java SE, Javadoc, Swing, MVC, Bilder pattern,
Core datastructures used: Arrays, LinkedList

## Author
Adrian Roguski with gudience included on AGH object oriented programming course webside

## Screenshots
![](images/Hard.png)
