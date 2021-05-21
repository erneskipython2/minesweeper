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

   
## Postman Collections

Download and Import the collections available in the folder docs/MINESWEEPER.postman_collection.json, or just click here [postman collections](docs/MINESWEEPER.postman_collection.json)
