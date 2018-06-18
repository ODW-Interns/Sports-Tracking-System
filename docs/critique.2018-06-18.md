# **MAJOR NOTE**

# General


- **GOALS** stated next: "Teams should be abstracted as to represent any kind of team, and then an concrete definition provided (in a separate package) for NBA". It means that all concrete implementation models related to NBA should be placed in their own separate package, e.g. `com.sts.nba.models`.

- **If documentation is not precise and does not provide good explanation, be free to ask for it anyone that wrote it. Most of common issues are result of misunderstading.**

- Enums should be named same as classes, e.g. `SPORTS_CAT` should be named `SportsCat` or better `SportsCatalog`.
- `DateCompare` class should be placed in util, not in model.
- Unneeded `TODO`s should be removed after implementation.

- Java documentation should be properly created, e.g. 

```java 

	// NOT GOOD
	/*
	 * Class is not documented by java documentation comment, but with regular one.
	 * 
	 */
	public class BadExample {
	}
	
	/**
	 * This is well documented good class.
	 */
	public class GoodExample {
	}
	
```

- Text should be formatted before commit.  


## Code review.

- `TeamHistory` should contain connection to both team and player, instead of setting team name and city and using it only in `AbstractPlayer`. In that case it should be named `TeamPlayer`. E.g.

```java

	/**
	 * This class contains connection between team and player and player's engagement.
	 */
	public class TeamPlayer {
		private field AbstractPlayer player;
		private field AbstractTeam team;
		private DateTime startDate;
		private DateTime endDate;
		/**
		 * This contains information if player is still active in team.
		 */
		private PlayerStatusInTeam status;
		
		...
		// TODO add getters and setters
		...
	}
	
	
	/**
	 * This class contains information about team.
	 */
	public class AbstractTeam {
		private List<TeamPlayer> teamPlayers;
		
		public List<TeamPlayer> getCurrentPlayers() {
			List<AbstractPlayer> filteredPlayersList = new ArrayList<>();
			// TODO filter team players by status/enddate.
			return filteredPlayersList; 
		}
	}
	
	/**
	 * This class contains 
	 */
	public class AbstractPlayer {
		private List<TeamPlayer> playerTeams;
		
		public TeamPlayer getCurrentTeam() {
			AbstractTeam retVal = null;
			// TODO filter team players by status/enddate.
			...
			retVal = teamPlayers where status is active.
			...
			return retVal; 
		}
	}
```

- **NOTE: from critique.2018-06-11**: Class that contain `main` method that starts system should be in root of package `com.sts`. This class will contain all required data for actual system start, like reading configuration and setting system base on it.


- `TeamsFileReader` and `PlayersFileReader` both work with `.csv` files. It is possible to add single class that is generic and accepts class and converter to use instead having duplicate code.

```java

	public class CsvReader<T> {
	   	private CsvConverter converter;
	   
		public CsvReader(CsvConverter converter) {
			this.converter = converter; 
	   	}
	
	   	public List<T> readCsv() {
	   		
	   		// TODO read csv file
	   		List<String> fileLines = loadFile(); 
	   		return converter.convert(fileLines);
	   	}
	}
	
	public interface CsvConverter<T> {
		List<T> convert(List<String> csvLines);
	}
   
	public class PlayerCsvConverter implements CsvConverter {
		public List<T> convert(List<String> csvLines) {
			List<T> retVal = new ArrayList<>();
			// TODO iterate lines and convert with corresponding converter
			
			for(String line: csvLines) {
				T current = null;
				try {
					current = convertLine(List<String> columnNames, line);
				}
				catch(Exception e) {
					// TODO Decide either to skip or to crash whole loading
				}
				retVal.add(current);
			}
		}
		
		private T convertLine(List<String> columnNames, line) {
			// add conversion here.
			return new T();
		}
	}
   
```
- **IMPORTANT**: please check project documentation on github. This solution is not event driven. There should be point which offers e.g. service, available for some client that can send messages, like create new games, create new players, move player to another team, create team, etc. 
