## General notes

- Package name should be all-lowercase.

- Classes for particular functionality should be in one package, like model package `com.sts.model` that will contain all models required by system
   - Good practice is if some classes will be abstract call them AbstractClass like `AbstractGame`...

- If classes need to have some methods, for example in all abstract models there is method `isValid... or equals`, create interface that will require that methods to be implemented. Example:
  
```
   java
	package com.sts.model;
	
	public interface Model {
	
		boolean isValid();
		
		String toString();
		...
	}    
```

```
   java
	package com.sts.model;
	
	public class SomeModel implements Model {
		
		@Override
		public boolean isValid() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
    	public String toString() {
    		// TODO Auto-generated method stub
			return null;
    	};
		...
	}    
```

- Only one logger is enough.

- Class that contain `main` method that starts system should be in root of package `com.sts`. This class will contain all required data for actual system start, like reading configuration and setting system base on it.

- Controls for actual system needs to be in `com.sts.control` package.

- Important note for good programming is documenting systems workflow by including a couple of words on what class contain and what method do. This will help in future when maintaining code is required.

```java
	
	/**
	 * This is dummy class.
	 */
	public class SomeClass {
	
		/**
		 * Gets info.
		 * @param input 
		 * @returns String calculated value
		 */
		public String getMeInfo(String input) {
		}
	}
``` 