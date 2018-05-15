# Sports-Tracking-System
## Members:
* Avi
* Tuan


## Goals
This project will track statistics on a team basis, on a person basis, and on a game basis.
Teams should be abstracted as to represent any kind of team, and then an concrete definition provided (in a separate package) for NBA
Players should be abstracted as to represetn any kind of player, and then concrete definition ...
Games should be ....



### Teams
Teams will start building from a top down level. We should be able to see what player is on what team, and when they started. It is common for players to move across teams, so start and end dates are required. And weather or not they are currently on a team should be tracked, and the state of the player (general play status - injury, benched, etc.)
<br/>


### Games
Games will start building from a top down level. You should be able to query what games are left, and see the scores of previous games. Please note, a game is a matchup of 2 teams, regardless of sports. this should be abstracted to a level that we can have NFL, NBA, Hockey, etc. games built in. 

Once the abstraction layer is built, you can then create the CONCRETE definition of a team, which can be the NBA team.


### Statistics
We'll add this part in later.

# Create a project
Building a project:
1. use the maven archetype to generate the project (java core (j2se, java8))
2. add logging using dependency information. Finad and verify the latest JUNIT
3. use slf4j as logging, and you need to use all level approppriatly
<br/>* They can go to a console if you want, but all logs need to go to a file as well

## Architecture
Your system should be built in an event driven manner. This means that it should accept objects as changes to it's system which will allow us to make this as a server at a later date. It should receive games, players, teams, etc. in this manner.


## structure
1. Break the project into the MVC mode
2. Ensure the model supports both abstract layers, as well as concrete. Ensure the concrete layers ARE NOT IN THE SAME PLACE (PACKAGE) as the abstraction
3. Controllers should have the following purposes:
<br/>* seed a player
<br/>* seed a team
<br/>* seed a list of games
4. I should be able to request a list of all upcoming games
<br/>* I should be able to see the location of a game
<br/>* I should be able to see the time of a game
<br/>* I should be able to see the time of a game when in different time zones
5. I should be able to request a list of all finished games
<br/>* I should be able to see the final score for each of these games 
6. For each game that I see, I want to get a list of all players


