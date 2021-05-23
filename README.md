# A java implementation for the minesweeper

This repository is a functional minesweeper game with api rest backend

**Author**: Erneski Coronado  - newdavid2007@gmail.com

## Technologies used

    - Java
    - Spring Boot
    - Mongo DB

## Description    

This project provides an implementation of the classic game MineSweeper. There are a pretty console client for play direct with a rest client. Postman collection are provided with all the needed.

### Features developed

    -Account Creation  with password encripted in database for manage different separately the games.
    -Login with JWT and roles
    -Game can be paused and resumed for not count the time
    -Movements tracking
    -If the first movement is a mine this is automatically put on other field randomly
    -Cleaning of clear fields recursively when the adjacent field has no mines as neighbors
    -Flaging a Field
    -Surrender and see where are the mines
    -Control for business exception with friendly message
    -Get the parties asocieted to an account
    -Get the api details for an specific game
    -Print the board for play by console (Text mode)
    
### Endpoints

    -/api/v1/accounts : for create a new account
    -/api/v1/login : Login with JWT
    -/api/v1/session-games : Creates a new game for the loged user
    -/api/v1/session-games : Updates the session state for a party (pause, resume)
    -/api/v1/session-games/{{MINESWEEPER_USER_ID}} : Get the games asociated for the loged user
    -/api/v1/session-games/{{MINESWEEPER_USER_ID}}/{{PARTY_ID}} : Get the api details for an account
    -/api/v1/session-games/{{MINESWEEPER_USER_ID}}/{{PARTY_ID}}/play : Get the string representation for a game (the board) without affect the movements
    -/api/v1/session-games/{{MINESWEEPER_USER_ID}}/{{PARTY_ID}}/play : Play the game, sned your movement, flag a field or surrender
    -/api/v1/session-games/{{MINESWEEPER_USER_ID}}/{{PARTY_ID}} : Deletes a game for the loged user
    -/v2/api-docs : swagger docs
    
*More detail see the swagger endpoints at browser /swagger-ui.html#/session-game-controller or by postman at /v2/api-docs*

![swagger](docs/images/swagger.png)

*For Code documentation see* [postman collections](docs/index.html)

### How to play

#### Update the application.properties file password with the correct credentials

    spring.data.mongodb.uri=mongodb+srv://[user]:[password]@cluster/minesweeper?retryWrites=true&serverSelectionTimeoutMS=7000&connectTimeout=7000&ssl=true&retrywrites=false&maxIdleTimeMS=120000
    spring.data.mongodb.database=minesweeper 
    custom.jwt.secret=${SECRET}
    
#### Run or build the application

    docker build . -t minesweeper
    docker run -p 8090:8090 -ti --rm minesweeper
    
#### Download the postman collections provided here and import [postman collections](docs/MINESWEEPER.postman_collection.json)

#### Creates a new account
![account](docs/images/account.png)

#### Login with the user/pass crated
![login](docs/images/login.png)

#### Creates a new game

You has the following options (Check the paramenter to send): 

    -Select a default setting for board creation EASY | NORMAL | HARD. Default is EASY
    -Set a custom number of rows, columns or mines

![create](docs/images/create.png)

#### Get your board
![view](docs/images/view.png)

#### Play: sends the coordinate of row and column
![play](docs/images/play.png)

#### Play: check the flag parameter - sends the coordinate of row and column
![flag](docs/images/flag.png)

#### Play: check the surrender parameter if you want to finish and reveal the board
![surrender](docs/images/surrender.png)

#### Session: put in pause a game and for resume sends the state
![pause](docs/images/pause.png)

#### Aditionally you can get all your created games and your game details
![details](docs/images/details.png)

## History of Changes and Steps developed

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
	-Added logic for multiple sessions, login with JWT, updated postman collections 
	-Added validation for WON party
	-Added Sonar and Dockerfile and Swagger
	-Added final revitions, javadoc, swagger and the readme organized

## TODO
	
	-Finish the technical debt for complete testing, a little % is covered by the moment
  
## Extras

### Validating with Sonar


**1.-Run the docker compose provided for sonar [here](docs/sonar-compose.yml)**

    docker-compose -f sonar-compose.yml up
    
*PD*: If you have problems with that stop and starts again:

    docker-compose -f sonar-compose.yml stop
    docker-compose -f sonar-compose.yml start
    
**2.-Access the sonar admin page at http://localhost:9000**

    user: admin
    password: admin

**3.-Create you project and copy your token**

**4.-Run the command**

    mvn clean install
    java -Djava.security.egd=file:/dev/./urandom -jar minesweeper-0.0.1-SNAPSHOT.jar
    
    mvn sonar:sonar -Dsonar.projectKey=minesweeper -Dsonar.host.url=http://localhost:9000 -Dsonar.login=[your token here] -Dsonar.coverage.jacoco.xmlReportPaths=/[your absolute path to project repo]/minesweeper/target/site/jacoco-it/jacoco.xml
    

