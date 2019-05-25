## Running FoodTruckFinder:

To build the project, navigate into the redfin.foodtrucks directory. Run with this command:
```
./gradlew build
```
The above command may take a few minutes if you have never run gradle before.
After building the project, run it with this command:

```
./gradlew run
```

Type enter to see the next set of foodtruck results or type exit to exit the program. 

Note: If you you have have never run Java on your machine in the past, you maybe prompted to install Java runtime. It can be found here: 
https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html



## What I would change if this was a full scale web application:

For data persistence, I would probably setup a job to call the Socrata API regularly, maybe daily or at a cadence that matches how frequently Socrata refreshes their own data, and I would store that data locally in some way (maybe a simple nosql database or a cloud service storing the data for us?). If we are serving responses to thousands of user’s requests we would not want to have to repeatedly call the Socrata API to fill these requests. In such a case, I would not call the Socrata api with a specific query based on the current time, but instead retrieve all of their possible results for that day (or week, depending on Socrata's data refresh) and have those ready when a GET request to our service was made. This means we would need to filter for the trucks that are currently open for the user based on current time as part of our own database query. I would definitely have automated tests and take whatever other steps needed to put a continuous integration & delivery system in place, so that we could implement changes for users as features are requested without breaking everything that we already have in place. Performance-wise we would want to think about caching, page load time, and indexing the results in our database and querying the database efficiently so that we can scale the application to server our many future users.
