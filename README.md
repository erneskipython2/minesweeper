# A java implementation for the minesweeper

This repository is a functional minesweeper game with api rest backend

**Author**: Erneski Coronado  - newdavid2007@gmail.com

## Technologies used

    - Java
    - Spring Boot
    - Mongo DB
    


## Description

In each commit step I'am going to be describing here the changes

    -I added the basic project skeleton with expected packages and maven configuration dependencies, testing and coverage
    -I added the basic log configuration properties
    -I added info project properties
    -I added mongodb, actuator and some other common props, added actuator dep for prod ready features
    -I added the SessionGame model class for describe a party persistence and tests for this pojo works as expected.
    -I improved the last test coverage for SessionGame class
    -Removed .DS_Store
    -As I'm going to create now some functional features I'm going to follow with some basic branch strategies dev/staging/master, pushing to remote, and change to dev branch 
    -Created the repository layer and basic rest controller for create a new party
    -Added basic persistence interface for SessionGame
    -Added basic service interface for SessionGame available operations
    -Added basic TDD tests and implementation for RED / GREEN / REFACTOR SessionGameService
    -Added functional implementation for createParty
    -Added logic for update and Delete SessionGame, if the game was in state PAUSED don't add the time tracking. Added some validations and Business Exceptions, Added testing for each case
    -Added logic for find user parties and a specific party with testing
    -Added basic rest controller with create(post) and update(put) game, create basic http auth config, create endpoints in props and postman init collections for testing api in docs/ folder
    -Added rest controller for get parties and party, testing and updated collections
    -Added BoardSettings model with some default constructors for levels and tests
    
    -Added Field model for describe every cell and logic for generate the board and fill the mines randomly and a handy printer helper
    -At this point you can see a board with number of mines adjacent, flag, safe fields and mines when you lose or surrender:  
    
	*****ⓂⒾⓃⒺⓈⓌⒺⒺⓅⒺⓇ*****
	🅱🆈 🅴🆁🅽🅴🆂🅺🅸 🅲🅾🆁🅾🅽🅰🅳🅾
	
	Instructions: Send with the row and column params your next move, Enjoy!
	State: STARTED | Time Playing: 82 (seconds) | Movements: 2
	Settings: BoardSettings(rows=9, columns=9, mines=10, level=EASY)
	Legend: ☐ -> unknow | ☑ -> safe | [1..n] -> Number of Mines adjacent | � -> Flagged | ☠ -> Mined, so you losed :(
	
	[̲̅M][̲̅Y] [̲̅B][̲̅O][̲̅A][̲̅R][̲̅D]
	   |0|1|2|3|4|5|6|7|8|
	 0 |☑|☑|☑|1|☐|☐|☐|☐|☐|
	 1 |2|2|1|1|☐|☐|☐|☐|☐|
	 2 |☐|☐|☐|☐|☐|☐|☐|☐|☐|
	 3 |☐|☐|☐|☐|☐|☐|☐|☐|☐|
	 4 |☐|☐|☐|☐|☐|☐|☐|☐|☐|
	 5 |☐|☐|☐|☐|☐|☐|☐|☐|☐|
	 6 |☐|☐|☐|☐|☐|☐|☐|☐|☐|
	 7 |�|☐|☐|☐|☐|☐|☐|☐|☐|
	 8 |☐|☐|☐|☐|☐|☐|☐|☐|☐|
		
	-Added interface for playing
	-Added play endpoints and base logic for playing. Some corrections and pretty presentation.
	-Added some logic for move to field and clear or lose.
	-Clearing some code and comments for next, added logic for flag and client-side board (without the mines)
	-Correction to flag logic
	-Added logic for count the number of mines adjacent to each one in the realBoard
	-Added validation if game is already ended
	-Added logic for clean recursively the board when a field is safe and its adjacent fields
	-Improved custom row, columns and mines board creation
	-Added logic for move mine if this is the first move
	-Added logic for manage business exception user friendly with the correct status code for each case
	-Added behavior for delete a party
	-Added logic y components for improve user, created account controller, logic and persistence

   
## Postman Collections

Download and Import the collections available in the folder docs/MINESWEEPER.postman_collection.json, or just click here [postman collections](docs/MINESWEEPER.postman_collection.json)
